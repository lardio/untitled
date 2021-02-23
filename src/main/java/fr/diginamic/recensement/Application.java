package fr.diginamic.recensement;

import fr.diginamic.recensement.services.MenuServices;
import fr.diginamic.recensement.services.RecherchePopulation;
import fr.diginamic.recensement.services.RechercheTop10;

import java.io.IOException;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) throws IOException {

        Recensement recensement = new Recensement();
        String cheminFichier = "///Users/sylvain/Documents/recensement.csv";
        //recensement.rescencerFichier(cheminFichier);
        Scanner scanner = new Scanner(System.in);
        int userChoice = 0;

        while (userChoice != 9) {
            userChoice = Integer.parseInt(MenuServices.displayMenu(scanner));
            if(userChoice < 1 || userChoice > 9) {
                System.out.println("Choix incorrect ! Merci de renseigner un nombre correspondant a un choix");
            } else {
                switch (userChoice){
                    case 1:
                        RecherchePopulation rechercheR = new RecherchePopulation(1, "ville");
                        rechercheR.traiter(scanner);
                        break;
                    case 2:
                        RecherchePopulation rechercheD = new RecherchePopulation(2, "département");
                        rechercheD.traiter(scanner);
                        break;
                    case 3:
                        RecherchePopulation rechercheV = new RecherchePopulation(3, "région");
                        rechercheV.traiter(scanner);
                        break;
                    case 4:
                        RechercheTop10 recherche10R = new RechercheTop10(4, "regions");
                        recherche10R.traiter(scanner);
                        break;
                    case 5:
                        RechercheTop10 recherche10D = new RechercheTop10(5, "départements");
                        recherche10D.traiter(scanner);
                        break;
                    case 6:
                        RechercheTop10 recherche10VD = new RechercheTop10(6, "département");
                        recherche10VD.traiter(scanner);
                        break;
                    case 7:
                        RechercheTop10 recherche10VR = new RechercheTop10(7, "région");
                        recherche10VR.traiter(scanner);
                        break;
                    case 8:
                        RechercheTop10 recherche10V = new RechercheTop10(8, "villes");
                        recherche10V.traiter(scanner);
                        break;
                    case 9:
                        System.out.println("Fermeture du menu.");
                        break;


                }
            }
        }
    }
}