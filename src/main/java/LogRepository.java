import java.sql.*;
import  java.util.*;
public class LogRepository {

    public void insertLog(Device device, String oName, Object result) throws SQLException{
        String sql = """
                INSERT INTO DeviceLogs(device_id, output_name, numeric_value, categorical_value)
                VALUES(?,?,?,?)
                """;
        try(
                Connection connection = DatabaseManager.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql);
        ) {
            stmt.setInt(1, device.getId());
            stmt.setString(2, oName);
            // categorical case
            if(result instanceof String category) {
                stmt.setNull(3, Types.DECIMAL);
                stmt.setString(4, category);
            // Numerical case
            } else if(result instanceof Integer num) {
                stmt.setInt(3, num);
                stmt.setNull(4, Types.VARCHAR);
            } else {
                throw new IllegalArgumentException("Unsupported value type");
            }

            stmt.executeUpdate();
        }
    }
}
