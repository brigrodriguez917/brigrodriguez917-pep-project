package Util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;

import org.h2.tools.RunScript;

public class DatabaseInitializer {
    public static void initializeDatabase() {
        try (Connection conn = ConnectionUtil.getConnection()) {
            InputStream sqlStream = DatabaseInitializer.class.getClassLoader()
                .getResourceAsStream("SocialMedia.sql");
            InputStreamReader sqlReader = new InputStreamReader(sqlStream);
            RunScript.execute(conn, sqlReader);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
