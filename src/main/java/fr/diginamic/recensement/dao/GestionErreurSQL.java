package fr.diginamic.recensement.dao;

import java.sql.SQLException;

/**
 * Permet d'afficher et gérer les erreurs liés aux manips avec la BDD
 * @author sylvain
 */
public abstract class GestionErreurSQL {

    public static void gestionErreur(SQLException e) {
        if (e.getErrorCode() == 1045) {
            System.out.println("Impossible de se connecter à la base de données:" + e.getMessage());
        } else {
            System.out.println("Pb de requête: " + e.getMessage());
        }
    }

}
