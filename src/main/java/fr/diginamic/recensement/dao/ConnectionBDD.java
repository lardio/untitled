package fr.diginamic.recensement.dao;

import org.mariadb.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Classe qui instancie une connection a la BDD
 * @author sylvain
 */
public class ConnectionBDD {

    /**
     * Execution automatique au chargement de la classe.
     * Charge le driver de la base.
     */
    static {
        try {
            DriverManager.registerDriver(new Driver());
        } catch (SQLException e) {
            System.out.println("######## ERREUR AVEC LE DRIVER");
            System.out.println(e.getMessage());
        }
    }

    /**
     * retourne une instance de connection a la BDD
     * @return Connection a la BDD
     */
    public static Connection getConnection() {
        ResourceBundle fichierConf = ResourceBundle.getBundle("database");
        String driverClass = fichierConf.getString("database.driver");
        String url = fichierConf.getString("database.url");
        String user = fichierConf.getString("database.user");
        String password = fichierConf.getString("database.password");

        try {
            Connection connection = DriverManager.getConnection(url, user, password); //demande au drivermanager de fournir une connexion type mariadb
            return connection;
        } catch (SQLException e) {
            System.out.println("######## ERREUR AVEC LA CONNECTION A LA BDD");
            System.out.println(e.getMessage());
            return null;
        }
    }

}
