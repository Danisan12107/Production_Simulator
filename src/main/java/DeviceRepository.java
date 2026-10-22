import java.sql.*;
import  java.util.*;
public class DeviceRepository {

    public void insertDevice(Device device) throws SQLException {

        String sql = """
                INSERT INTO Devices (device_name)
                VALUES (?)
                """;

        try (
                Connection connection = DatabaseManager.getConnection();

                PreparedStatement stmt = connection.prepareStatement(
                        sql,
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            stmt.setString(1, device.getName());

            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {

                if (keys.next()) {
                    int genId = keys.getInt(1);
                    device.setId(genId);

                } else {
                    throw new SQLException(
                            "Failed to retrieve device ID"
                    );
                }
            }
        }
    }

    public List<Device> extractDevice() throws SQLException{
        List<Device> extractedDevices = new ArrayList<>();
        String sql = """
                SELECT * FROM Devices
                """;
        try(
                Connection connection = DatabaseManager.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();
        ) {
            while(rs.next()) {
                int id = rs.getInt("device_id");
                String name = rs.getString("device_name");
                Device temp = new Device(name);
                temp.setId(id);
                // now we need to add rules
                extractRules(temp);
            }
        }

        // temp return
        return extractedDevices;
    }

    private void extractRules(Device device) throws SQLException {
        String sql = """
                SELECT output_name, output_type, lower_bound, upper_bound
                FROM OutputRules
                WHERE device_id = ?
                """;
        try(
                Connection connection = DatabaseManager.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();
        ) {
            String type = rs.getString("output_type");
            if(type.equals("Numeric")) {
                int lower = rs.getInt("lower_bound");
                int upper = rs.getInt("upper_bound");
                String oName = rs.getString("output_name");
                device.addNumericOutput(oName, lower, upper);
            } else {
                // Categorical case
            }
        }
    }
}