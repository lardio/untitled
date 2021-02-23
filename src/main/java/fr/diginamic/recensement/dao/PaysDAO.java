package fr.diginamic.recensement.dao;

import fr.diginamic.recensement.entities.Departement;
import fr.diginamic.recensement.entities.Pays;

import java.sql.*;
import java.util.List;

/**
 * Classe enfant de LieuDAO
 * Classe de gestion de pays, qui sont enfant de la classe Lieu
 * @see Pays
 * @see fr.diginamic.recensement.entities.Lieu
 * @see LieuDAO
 *
 * @author sylvain
 */
public class PaysDAO extends LieuDAO<Pays>{

    public PaysDAO(Connection connection) {
        super(connection);
        this.table = "pays";
    }

    /**
     * Permet d'ajouter plusieurs villes a la BDD
     * @param liste
     * @return
     */
    public boolean ajouter(List<Pays> liste) {
        try {
            String requete = "INSERT INTO " +this.table +" (code, nom, population) VALUES(?, ?, ?)";
            for (int counter = 0; counter < liste.size(); counter++) {
                PreparedStatement statement = this.connect.prepareStatement(requete);
                statement.setObject(1, liste.get(counter).getCode());
                statement.setObject(2, liste.get(counter).getNom());
                statement.setObject(3, liste.get(counter).getPopulation(), Types.INTEGER);

                statement.executeUpdate();
                statement.close();
            }
            return false;
        } catch (SQLException e) {
            GestionErreurSQL.gestionErreur(e);
            return true;
        }
    }


    public Pays rechercher(int id) {
        Pays pays = null;
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
                pays = new Pays(code, population, idLieu);
            }

            curseur.close();
            statement.close();
        } catch (SQLException e) {
            GestionErreurSQL.gestionErreur(e);
        }
        return pays;
    }

    public Pays rechercherByCode(String codePays) {
        Pays pays = null;
        try {
            String requete = "SELECT * FROM " + this.table + " WHERE code = ?";
            PreparedStatement statement = this.connect.prepareStatement(requete);
            statement.setString(1, codePays);
            ResultSet curseur = statement.executeQuery();
            while (curseur.next()) {
                Integer idLieu = curseur.getInt("id");
                String nom = curseur.getString("nom");
                String code = curseur.getString("code");
                Integer population = curseur.getInt("population");
                pays = new Pays(code, population, idLieu);
            }

            curseur.close();
            statement.close();
        } catch (SQLException e) {
            GestionErreurSQL.gestionErreur(e);
        }
        return pays;
    }

}
