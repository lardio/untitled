package fr.diginamic.recensement;
import fr.diginamic.recensement.dao.*;
import fr.diginamic.recensement.entities.Departement;
import fr.diginamic.recensement.entities.Pays;
import fr.diginamic.recensement.entities.Region;
import fr.diginamic.recensement.entities.Ville;
import fr.diginamic.recensement.entities.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.sql.Connection;
import java.util.*;


/**
 * Classe Recensement.
 * Parcours un fichier via la méthode rescencerFichier
 * L'ensemble des données seront accessible via la propriété pays qui contiendra un ensemble de liste organisé et hiérarchisé de Lieu.
 *
 * @see Lieu
 * @author Sylvain
 */
public class Recensement {

    Connection connection = ConnectionBDD.getConnection();
    protected PaysDAO paysDAO = new PaysDAO(connection);
    protected Pays pays;
    protected RegionDAO regionDAO = new RegionDAO(connection);
    protected DepartementDAO departementDAO = new DepartementDAO(connection);
    protected VilleDAO villeDAO = new VilleDAO(connection);


    public void rescencerFichier(String cheminFichier) throws IOException {
        pays = new Pays("France");
        paysDAO.ajouter(pays);

        pays = paysDAO.rechercherByCode("France");

        Path pathOrigine = Paths.get(cheminFichier); //fichier a copier
        List<String> lignesFichier = Files.readAllLines(pathOrigine, StandardCharsets.UTF_8);

        try {
            for(String ligne : lignesFichier) {
                String[] colonne = ligne.split(";");

                if(colonne[0].matches("-?\\d+")) { // evite les en-tetes
                    int populationTotale = Integer.parseInt(colonne[9].replaceAll("\\s+",""));
                    String codeRegion = colonne[0];
                    String codeDepartement = colonne[2];
                    String codeVille = colonne[5];

                    if(!regionDAO.checkIfExistByCode(codeRegion)) {
                        Region region = new Region(codeRegion, colonne[1], pays.getId());
                        regionDAO.ajouter(region);;
                    }
                    Region regionGet = regionDAO.rechercherByCode(codeRegion);

                    if(!departementDAO.checkIfExistByCode(codeDepartement)) {
                        Departement departement = new Departement(codeDepartement, regionGet.getId(), pays.getId());
                        departementDAO.ajouter(departement);;
                    }

                    Departement departementGet = departementDAO.rechercherByCode(codeDepartement);

                    Ville ville = new Ville(colonne[6], populationTotale, departementGet.getId(), regionGet.getId(), pays.getId());
                    villeDAO.ajouter(ville);
                }
            }
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}