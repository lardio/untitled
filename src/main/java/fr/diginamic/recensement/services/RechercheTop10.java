package fr.diginamic.recensement.services;

import fr.diginamic.recensement.dao.*;
import fr.diginamic.recensement.entities.Lieu;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Classe gérant l'affichage de 10 Lieux les plus peuplés à partir d'un Lieu ayant une hiérarchie plus haute.
 * Affiche les 10 Lieu les plus peuplés d'un Lieu.
 *
 * @author Sylvain
 *
 */
public class RechercheTop10 extends MenuServices {

    /**
     * Constructeur de RecherchePopulation
     * @param choixMenu, int qui permet d'identifier le type de Lieu recherché.
     * @param typeRecherche, String qui précise le type de Lieu recherché, utilisé pour afficher le type de Lieu recherché à l'utilisateur.
     */
    public RechercheTop10(int choixMenu, String typeRecherche) {
        this.choixMenu = choixMenu;
        this.typeRecherche = typeRecherche;
    }

    /**
     * Retourne les les 10 Lieu les plus peuplés d'un Lieu.
     * Les lieu recherchés sont filtrés par le choix utilisateur identifié par la propriété choixMenu.
     * Controle si le lieu parent au 10 autres lieu existe.
     * L'affichage des 10 Lieu est géré par la propriété displayTop10 de la classe parente MenuServices.
     * @see MenuServices
     *
     * @param scanner
     */
    public void traiter(Scanner scanner) {

        try {
            List<Lieu> choixListe  = new ArrayList<>();
            Connection connection = ConnectionBDD.getConnection();
            RegionDAO regionDAO = new RegionDAO(connection);
            DepartementDAO departementDAO = new DepartementDAO(connection);
            VilleDAO villeDAO = new VilleDAO(connection);

            if( this.choixMenu == 4 || this.choixMenu == 5 || this.choixMenu == 8 ) {
                if ( this.choixMenu== 4 ) {
                    choixListe = regionDAO.getListeLieu();
                } else if ( this.choixMenu== 5 ) {
                    choixListe = departementDAO.getListeLieu();
                } else {
                    choixListe = villeDAO.getListeLieu();
                }
                System.out.println("#### Top 10 des " +this.typeRecherche +" de France =>");

            } else {
                List<Lieu> choixAdditionel;
                String jeVeuxFinirDoncManiereBrutalPasPropre;
                if( this.choixMenu == 6 ) {
                    System.out.println("Merci de choisir le département (code departement).");
                    choixAdditionel = departementDAO.getListeLieu();
                    this.choix = scanner.nextLine();
                    System.out.println(choixAdditionel.size());
                    jeVeuxFinirDoncManiereBrutalPasPropre = "departement_id";

                } else {
                    System.out.println("Merci de choisir la région (code region).");
                    choixAdditionel = regionDAO.getListeLieu();
                    this.choix = scanner.nextLine();
                    jeVeuxFinirDoncManiereBrutalPasPropre = "region_id";
                }

                choix = verifSiEntreeExiste(choixAdditionel, choix, scanner);
                if(choix == "back") {
                    return;
                }

                for(Lieu lieu : choixAdditionel) {
                    if( this.choix.equals(lieu.getCode()) ) {
                        choixListe = villeDAO.getListeVille(lieu, jeVeuxFinirDoncManiereBrutalPasPropre);
                        System.out.println(choixListe.size());
                        System.out.println("#### Top 10 des villes pour le/la " +this.typeRecherche +" " +lieu.getNom() +" =>");
                    }
                }

            }

            connection.close();

            this.displayTop10(choixListe);
            System.out.println("\n ################ Pour continuer taper sur une entrée (fort car ca deconne !).");
            scanner.nextLine();
            return;
        } catch (SQLException e) {
            GestionErreurSQL.gestionErreur(e);
        }
    }
}