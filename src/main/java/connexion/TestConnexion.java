
package connexion;

import java.sql.Connection;
import java.sql.SQLException;

public class TestConnexion {

    public static void main(String[] args) {

        try (Connection con = Connexion.getConnection()) {

            System.out.println("Connexion à sprint_extra réussie !");

        } catch (SQLException e) {

            System.out.println("Erreur de connexion : " + e.getMessage());
        }
    }
}
