import java.sql.*;
import java.util.*;
public class RuleRepository {

    public void insertCatRule(int id, String outputName, List<String> outputs)
            throws SQLException {

        String sql1 = """
            INSERT INTO OutputRules(device_id, output_name, output_type)
            VALUES(?, ?, ?)
            """;

        String sql2 = """
            INSERT INTO CategoricalValues(rule_id, category_value)
            VALUES(?, ?)
            """;

        try (Connection connection = DatabaseManager.getConnection()) {

            connection.setAutoCommit(false);

            try {
                int ruleId;

                // 1. Insert the categorical rule
                try (PreparedStatement stmt = connection.prepareStatement(
                        sql1,
                        Statement.RETURN_GENERATED_KEYS)) {

                    stmt.setInt(1, id);
                    stmt.setString(2, outputName);
                    stmt.setString(3, "Categorical");

                    stmt.executeUpdate();

                    // 2. Retrieve the generated rule_id
                    try (ResultSet keys = stmt.getGeneratedKeys()) {
                        if (keys.next()) {
                            ruleId = keys.getInt(1);
                        } else {
                            throw new SQLException("Failed to retrieve rule_id");
                        }
                    }
                }

                // 3. Insert categorical values using rule_id
                try (PreparedStatement stmt = connection.prepareStatement(sql2)) {

                    for (String val : outputs) {
                        stmt.setInt(1, ruleId);
                        stmt.setString(2, val);

                        stmt.addBatch();
                    }

                    stmt.executeBatch();
                }

                // 4. Commit both inserts
                connection.commit();

            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }
    }

    public void insertNumRule(int id, String outputName, int low, int high) throws SQLException {
        String sql = """
                INSERT INTO OutputRules(device_id, output_name, output_type, lower_bound, upper_bound)
                VALUES(?, ?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseManager.getConnection()) {
            try(PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.setString(2, outputName);
                stmt.setString(3, "Numeric");
                stmt.setInt(4, low);
                stmt.setInt(5, high);
                stmt.executeUpdate();

            }
        }
    }
}
