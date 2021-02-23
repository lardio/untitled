package fr.diginamic.recensement.services;
import fr.diginamic.recensement.dao.*;
import fr.diginamic.recensement.entities.Lieu;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Classe gérant la recherche d'un Lieu.
 * Affiche les informations de n'importe quel Lieu (son nom et sa population).
 *
 * @author Sylvain
 *
 */
public class RecherchePopulation extends MenuServices {

    /**
     * Constructeur de RecherchePopulation
     * @param choixMenu, int qui permet d'identifier le type de Lieu recherché.
     * @param typeRecherche, String qui précise le type de Lieu recherché, utilisé pour afficher le type de Lieu recherché à l'utilisateur.
     */
    public RecherchePopulation(int choixMenu, String typeRecherche) {
        this.choixMenu = choixMenu;
        this.typeRecherche = typeRecherche;
    }

    /**
     * Retourne les informations concernants n'importe quel type de Lieu.
     * Le lieu recherché est filtré par le choix utilisateur identifié par la propriété choixMenu.
     * Controle si le lieu recherché existe, via la methode verifSiEntreeOk de MenuServices.
     * @see MenuServices
     *
     * @param scanner
     */
    public void traiter(Scanner scanner) {
        try {
            Connection connection = ConnectionBDD.getConnection();
            System.out.println("Merci de choisir un(e) " + typeRecherche);
            choix = scanner.nextLine();
            List<Lieu> choixListe = new ArrayList<>();

            if(choixMenu == 3) {
                RegionDAO regionDAO = new RegionDAO(connection);
                choixListe = regionDAO.getListeLieu();
            } else if(choixMenu == 2) {
                DepartementDAO departementDAO = new DepartementDAO(connection);
                choixListe = departementDAO.getListeLieu();
            } else {
                VilleDAO villeDAO = new VilleDAO(connection);
                choixListe = villeDAO.getListeLieu();
            }

            choix = verifSiEntreeExiste(choixListe, choix, scanner);
            if(choix == "back") {
                return;
            }

            for(Lieu lieu : choixListe) {
                if( choix.equals(lieu.getCode()) ) {
                    lieu.getInformations();
                }
            }

            connection.close();

            System.out.println("\n ################ Pour continuer taper sur une entrée (fort car ca deconne !).");
            scanner.nextLine();
            return;
        } catch (SQLException e) {
            GestionErreurSQL.gestionErreur(e);
        }
    }

}