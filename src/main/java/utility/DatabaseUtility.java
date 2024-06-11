package utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class DatabaseUtility implements DatabaseUtilityInterface {
    private static final String URL = "jdbc:mysql://localhost:3306/area";
    private static final String USER = "root";
    private static final String PASSWORD = "Rosey@123";
    private static final Logger logger = Logger.getLogger(DatabaseUtility.class.getName());

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @Override
    public void saveDataToDatabase(List<String[]> data) throws SQLException {
        String insertSQL = "INSERT INTO pincodes (pincode, district, state) VALUES (?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            for (String[] row : data) {
                // Parse the pincode string to an integer
                int pincode = Integer.parseInt(row[0]);
                preparedStatement.setInt(1, pincode);
                preparedStatement.setString(2, row[1]);
                preparedStatement.setString(3, row[2]);
                preparedStatement.setString(4, row[2]);
                preparedStatement.addBatch();
            }
            int[] affectedRows = preparedStatement.executeBatch();
            logger.info("Rows inserted: " + affectedRows.length);
        } catch (SQLException e) {
            logger.severe("Error inserting data into database: " + e.getMessage());
            throw e;
        }catch (NumberFormatException e) {
            logger.severe("Error parsing pincode to integer: " + e.getMessage());
            throw e;
        }
    }
}
