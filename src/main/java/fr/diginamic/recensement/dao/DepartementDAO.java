package fr.diginamic.recensement.dao;

import fr.diginamic.recensement.entities.Departement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe enfant de LieuDAO
 * Classe de gestion de departement, qui sont enfant de la classe Lieu
 * @see Departement
 * @see fr.diginamic.recensement.entities.Lieu
 * @see LieuDAO
 *
 * @author sylvain
 */
public class DepartementDAO extends LieuDAO<Departement> {

    public DepartementDAO(Connection connection) {
        super(connection);
        this.table = "departement";
    }

    /**
     *
     * @param id
     * @return Departement
     * Recherche un Departement en fonction de son ID, retourne null si rien trouve
     */
    public Departement rechercher(int id) {
        Departement departement = null;
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
                Integer region_id = curseur.getInt("region_id");
                Integer pays_id = curseur.getInt("pays_id");
                departement = new Departement(idLieu, code, population, region_id, pays_id);
            }

            curseur.close();
            statement.close();

        } catch (SQLException e) {
            GestionErreurSQL.gestionErreur(e);
        }
        return departement;
    }

    /**
     *
     * @param codeDepartement
     * @return Departement
     * Recherche un Departement en fonction de son code, retourne null si rien trouve
     */
    public Departement rechercherByCode(String codeDepartement) {
        Departement departement = null;
        try {
            String requete = "SELECT * FROM " + this.table + " WHERE code = ?";
            PreparedStatement statement = this.connect.prepareStatement(requete);
            statement.setString(1, codeDepartement);
            ResultSet curseur = statement.executeQuery();
            while (curseur.next()) {
                Integer idLieu = curseur.getInt("id");
                String nom = curseur.getString("nom");
                String code = curseur.getString("code");
                Integer population = curseur.getInt("population");
                Integer region_id = curseur.getInt("region_id");
                Integer pays_id = curseur.getInt("pays_id");
                departement = new Departement(idLieu, code, population, region_id, pays_id);
            }

            curseur.close();
            statement.close();

        } catch (SQLException e) {
            GestionErreurSQL.gestionErreur(e);
        }
        return departement;
    }

}