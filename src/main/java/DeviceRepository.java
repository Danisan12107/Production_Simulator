import java.sql.*;

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
}