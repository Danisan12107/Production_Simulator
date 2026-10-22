import java.sql.*;
import java.util.*;
public class RuleRepository {
    public void insertCatRule(int id, String outputName, List<String> outputs) throws SQLException{
        // going to need to make an insertion to OutputRules table first
        String sql1 = """
                INSERT INTO OutputRules(device_id, output_name, output_type)
                VALUES(?, ?, ?)
                """;

        String sql2 = """
                INSERT INTO CategoricalOutputValues(device_id, output_name, category_value)
                VALUES(?, ?, ?)
                """;

       try(Connection connection = DatabaseManager.getConnection()) {
           // prevents an insert from commiting immediately
           connection.setAutoCommit(false);
           try {
               // Inserts the cateogorical rule
               try(PreparedStatement stmt = connection.prepareStatement(sql1)) {
                   stmt.setInt(1, id);
                   stmt.setString(2, outputName);
                   stmt.setString(3, "Categorical");
                   stmt.executeUpdate();
               }

               try(PreparedStatement stmt = connection.prepareStatement(sql2)) {
                   for(String val : outputs) {
                       stmt.setInt(1, id);
                       stmt.setString(2, outputName);
                       stmt.setString(3, val);
                       stmt.executeUpdate();
                   }
               }

               // saves all operations after both tries succeed
               connection.commit();
           } catch(SQLException e) {
               // undoes the changes if an error occurs
               connection.rollback();
               throw e;
           }
       }
    }

    public void insertNumRule(int id, String outputName, int low, int high) throws SQLException{
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
