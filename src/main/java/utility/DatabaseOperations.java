package utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseOperations {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/area";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "Rosey@123";

    public static String getPincode(String area) {
        String pincode = null;
        String sql = "SELECT PinCode FROM AreaInformation WHERE Area = ?";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, area);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    pincode = rs.getString("PinCode");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pincode;
    }

    public static List<String> getAreas(String pincode) {
        List<String> areas = new ArrayList<>();
        String sql = "SELECT Area FROM AreaInformation WHERE PinCode = ?";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pincode);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    areas.add(rs.getString("Area"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return areas;
    }
}