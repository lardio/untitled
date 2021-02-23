package fr.diginamic.recensement.dao;

import fr.diginamic.recensement.entities.Pays;
import fr.diginamic.recensement.entities.Region;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe enfant de LieuDAO
 * Classe de gestion de regions, qui sont enfant de la classe Lieu
 * @see Region
 * @see fr.diginamic.recensement.entities.Lieu
 * @see LieuDAO
 *
 * @author sylvain
 */
public class RegionDAO extends LieuDAO<Region>{

    public RegionDAO(Connection connection) {
        super(connection);
        this.table = "region";
    }

    public Region rechercher(int id) {
        Region region = null;
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
                Integer pays_id = curseur.getInt("pays_id");
                region = new Region(idLieu, code, nom, population, pays_id);
            }

            curseur.close();
            statement.close();
        } catch (SQLException e) {
            GestionErreurSQL.gestionErreur(e);
        }
        return region;
    }

    public Region rechercherByCode(String codeRegion) {
        Region region = null;
        try {
            String requete = "SELECT * FROM " + this.table + " WHERE code = ?";
            PreparedStatement statement = this.connect.prepareStatement(requete);
            statement.setString(1, codeRegion);
            ResultSet curseur = statement.executeQuery();
            while (curseur.next()) {
                Integer idLieu = curseur.getInt("id");
                String nom = curseur.getString("nom");
                String code = curseur.getString("code");
                Integer population = curseur.getInt("population");
                Integer pays_id = curseur.getInt("pays_id");
                region = new Region(idLieu, code, nom, population, pays_id);
            }

            curseur.close();
            statement.close();
        } catch (SQLException e) {
            GestionErreurSQL.gestionErreur(e);
        }
        return region;
    }


}
