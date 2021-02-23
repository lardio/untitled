package fr.diginamic.recensement.dao;

import fr.diginamic.recensement.entities.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe enfant de LieuDAO
 * Classe de gestion de villes, qui sont enfant de la classe Lieu
 * @see Ville
 * @see fr.diginamic.recensement.entities.Lieu
 * @see LieuDAO
 *
 * La classe Ville etant la plus basse dans la hiérarchie des Lieu, elle est celle qui increment l'ensemble des populations vers les autres lieux.
 *
 * @author sylvain
 */
public class VilleDAO extends LieuDAO<Ville> {

    public VilleDAO(Connection connection) {
        super(connection);
        this.table = "ville";
    }

    public Ville rechercher(int id) {
        Ville ville = null;
        try {
            String requete = "SELECT * FROM " + this.table + " WHERE id = ?";
            PreparedStatement statement = this.connect.prepareStatement(requete);
            statement.setLong(1, id);
            ResultSet curseur = statement.executeQuery();
            while (curseur.next()) {
                Integer idLieu = curseur.getInt("id");
                String nom = curseur.getString("nom");
                String code = curseur.getString("code");
                Integer population = curseur.getInt("population");
                Integer departement_id = curseur.getInt("departement_id");
                Integer region_id = curseur.getInt("region_id");
                Integer pays_id = curseur.getInt("pays_id");
                ville = new Ville(idLieu, nom, population, departement_id, region_id, pays_id);
            }

            curseur.close();
            statement.close();
        } catch (SQLException e) {
            GestionErreurSQL.gestionErreur(e);
        }
        return ville;
    }

    public Ville rechercherByCode(String codeVille) {
        Ville ville = null;
        try {
            String requete = "SELECT * FROM " + this.table + " WHERE code = ?";
            PreparedStatement statement = this.connect.prepareStatement(requete);
            statement.setString(1, codeVille);
            ResultSet curseur = statement.executeQuery();
            while (curseur.next()) {
                Integer idLieu = curseur.getInt("id");
                String nom = curseur.getString("nom");
                String code = curseur.getString("code");
                Integer population = curseur.getInt("population");
                Integer departement_id = curseur.getInt("departement_id");
                Integer region_id = curseur.getInt("region_id");
                Integer pays_id = curseur.getInt("pays_id");
                ville = new Ville(idLieu, nom, population, departement_id, region_id, pays_id);
            }

            curseur.close();
            statement.close();
        } catch (SQLException e) {
            GestionErreurSQL.gestionErreur(e);
        }
        return ville;
    }

    /**
     * Créé un objet Ville puis le créé dans la BDD
     * créé ensuite des objets de classe qui sont enfant de Lieu et hierarchiquement plus haute que Ville pour incrementer leur population, puis met a jour la propriété population de ces objets en BDD
     * @param lieu
     * * @author Sylvain
     */
    public void ajouter(Ville lieu) {
        super.ajouter(lieu);
        Integer departementId = lieu.getDepartement_id();
        Integer regionid = lieu.getRegion_id();
        Integer paysId = lieu.getPays_id();
        System.out.println(departementId);

        DepartementDAO departementDAO = new DepartementDAO(this.connect);
        Departement departement = departementDAO.rechercher(departementId);
        departement.setPopulation(lieu.getPopulation());
        departementDAO.maj(departement);

        RegionDAO regionDAO = new RegionDAO(this.connect);
        Region region = regionDAO.rechercher(regionid);
        region.setPopulation(lieu.getPopulation());
        regionDAO.maj(region);

        PaysDAO paysDAO = new PaysDAO(this.connect);
        Pays pays = paysDAO.rechercher(paysId);
        pays.setPopulation(lieu.getPopulation());
        paysDAO.maj(pays);
    }

    /**
     * Retourne une liste de villes en filtrant par un objet d'une classe enfante de Lieu plus haute hiérarchiquement que Ville.
     * @see Ville
     * @param lieu
     * @param typeLieu
     * @return
     * @author Sylvain
     */
    public List<Lieu> getListeVille(Lieu lieu, String typeLieu) {
        List<Lieu> listeLieux = new ArrayList<>();
        try {
            String requete = "SELECT * FROM " + this.table + " WHERE " + typeLieu +" = ?";
            PreparedStatement statement = this.connect.prepareStatement(requete);
            statement.setInt(1, lieu.getId());
            System.out.println(statement);
            ResultSet curseur = statement.executeQuery();
            while (curseur.next()) {
                Integer idLieu = curseur.getInt("id");
                String nom = curseur.getString("nom");
                String code = curseur.getString("code");
                Integer population = curseur.getInt("population");
                Lieu lieuAjout = new Lieu(idLieu, code, nom, population);
                listeLieux.add(lieuAjout);
            }

            curseur.close();
            statement.close();
        } catch (SQLException e) {
            GestionErreurSQL.gestionErreur(e);
        }
        return listeLieux;
    }

}
