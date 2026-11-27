
package connexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {

    private static final String URL =
            "jdbc:mariadb://localhost:3306/sprint_extra";

    private static final String USER = "root";
    private static final String PASSWORD = "";

public static Connection getConnection() throws SQLException {

    try {
        Class.forName("org.mariadb.jdbc.Driver");
    } catch (ClassNotFoundException e) {
        throw new SQLException("Pilote MariaDB introuvable", e);
    }

    return DriverManager.getConnection(URL, USER, PASSWORD);
}
}
