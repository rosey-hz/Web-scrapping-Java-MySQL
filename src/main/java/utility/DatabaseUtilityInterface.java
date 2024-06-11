package utility;

import java.sql.SQLException;
import java.util.List;

public interface DatabaseUtilityInterface {
    void saveDataToDatabase(List<String[]> data) throws SQLException;
}