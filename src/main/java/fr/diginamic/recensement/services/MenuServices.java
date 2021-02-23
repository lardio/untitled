package fr.diginamic.recensement.services;

import fr.diginamic.recensement.entities.Lieu;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Classe gérant les traitements liés au Menu.
 *
 * @author Sylvain
 *
 */
public class MenuServices {

    protected Lieu lieu;
    int choixMenu;
    String typeRecherche;
    String choix;

    /**
     * Affichage du Menu lancé dans la classe Application
     * @see fr.diginamic.recensement.Application
     * @param scanner
     * @return
     */
    public static String displayMenu(Scanner scanner) {
        System.out.println("\n######### MENU ###########");
        System.out.println("1 => Voir la population d'une ville.");
        System.out.println("2 => Voir la population d'un departement.");
        System.out.println("3 => Voir la population d'une region.");
        System.out.println("4 => Top 10 des régions les plus peuplées.");
        System.out.println("5 => Top 10 des départements les plus peuplés.");
        System.out.println("6 => Top 10 des villes les plus peuplées d'un département.");
        System.out.println("7 => Top 10 des villes les plus peuplées d'une région.");
        System.out.println("8 => Top 10 des villes les plus peuplées de France.");
        System.out.println("9 => Quitter.");
        return scanner.nextLine();
    }

    /**
     * Affiche les 10 lieux les plus peuplés d'une liste de Lieu.
     * @see Lieu
     * @param list
     */
    protected void displayTop10(List<Lieu> list) {
        Collections.sort(list, Collections.reverseOrder());
        for(int i = 0; i < 9; i ++) {
            list.get(i).getInformations();
        }
    }

    /**
     * Verifie si le code d'un Lieu fourni correspond à un Lieu dans une liste de Lieu.
     * Si oui retourne true, sinon false
     * @see Lieu
     * @param list
     */
    protected boolean verifSiEntreeOk(List<Lieu> list, String code){
        return list.stream().anyMatch(lieu -> lieu.getCode().equals(code));
    }

    /**
     * Controle si le code d'un lieu fourni existe.
     * Si il existe pas, propose à l'utilisateur de rentrer un nouveau code, ou bien de quitter le menu courant.
     * Si le code fourni existe, execute la suite de l'oparation demandée.
     * @see Lieu
     * @param list
     */
    protected String verifSiEntreeExiste(List<Lieu> list, String choix, Scanner scanner) {
        while(!verifSiEntreeOk(list, choix)) {
            System.out.println("### La valeure renseignée ne correspond a aucun(e) " +typeRecherche +" ###");
            System.out.println("Pour resaisir une recherche taper 1. Sinon taper 2.");
            Integer newChoix = Integer.parseInt(scanner.nextLine());
            while(newChoix < 1 || newChoix > 2) {
                System.out.println("### Choix inccorrect.");
                System.out.println("Pour resaisir une recherche taper 1. Sinon taper 2.");
                newChoix = Integer.parseInt(scanner.nextLine());
            }
            if(newChoix == 2) {
                return "back";
            }
            System.out.println("Merci de choisir un(e) " + typeRecherche +".");
            choix = scanner.nextLine();
        }
        return choix;
    }


}