package fr.diginamic.recensement.dao;
import fr.diginamic.recensement.entities.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui gere les requetes en BDD avec les Lieu
 * @see Lieu
 *
 * Prend en parametre une Connection fournit par Connection BDD
 * @see ConnectionBDD
 *
 * Les requetes ajouter ; maj; supprimer; checkIfExists et checkIfExistByCode gere l'ensemble des lieux et ont pas besoin d'etre redefinies.
 * @param <T> qui doit etre une classe enfant de LieuDAO
 * @author Sylvain
 */
public abstract class LieuDAO<T extends Lieu> {

    protected Connection connect = null;
    protected String table;

    public LieuDAO(Connection connection) {
        this.connect = connection;
    }

    public void ajouter(T lieu) {
        System.out.println(lieu.getClass());
        ArrayList<Method> getterClass = getGettersAndProperties.findGetters(lieu.getClass()); //recuperation de tous els getters de la classe

        ArrayList<String> listeType = new ArrayList<>(); //stockage des type de valeurs que les getters retournent
        ArrayList<String> listeValeurs = new ArrayList<>(); //stockage des  valeurs
        String ajoutChamps = ""; //String pour la requete SQL
        String ajoutValeur = ""; //String pour la requete SQL (les ?)

        for (Method method : getterClass) {
            try {
                method.setAccessible(true);
                if(method.invoke(lieu) != null ) {
                    String result = method.invoke(lieu).toString(); // valeur de retour du getter
                    String propertyName =  method.getName().split("get")[1].toLowerCase(); // nom de la propriété
                    String propertyType = method.getGenericReturnType().getTypeName().substring(method.getGenericReturnType().getTypeName().lastIndexOf('.') + 1);// type de la propriété

                    if(!propertyName.equals("id")) {
                        ajoutChamps += propertyName + ",";
                        ajoutValeur += "?,";
                        listeValeurs.add(result);
                        listeType.add(propertyType);
                    }
                }
            }catch(InvocationTargetException | IllegalAccessException e) {
                System.out.println("ko");
            }
        }

        try {
            ajoutChamps = ajoutChamps.substring(0, ajoutChamps.length() -1); //supression derniere ','
            ajoutValeur = ajoutValeur.substring(0, ajoutValeur.length() -1);

            String requete = "INSERT INTO " +this.table +" (" +ajoutChamps +") VALUES (" +ajoutValeur +");";
            PreparedStatement statement = this.connect.prepareStatement(requete);
            for(int i = 0; i < listeValeurs.size(); i++) {
                if(listeType.get(i).equals("String")) {
                    statement.setString(i + 1, listeValeurs.get(i));
                } else {
                    statement.setInt(i + 1, Integer.parseInt(listeValeurs.get(i)));
                }
            }

            statement.executeUpdate();
            statement.close();

        } catch (SQLException e) {
            GestionErreurSQL.gestionErreur(e);
        }

    }

    public void maj(T lieu) {
        //recuperation de tous les getters du Lieu
        ArrayList<Method> getterClass = getGettersAndProperties.findGetters(lieu.getClass());
        ArrayList<String> listeType = new ArrayList<>();
        ArrayList<String> listeValeurs = new ArrayList<>();
        String updateString = "";

        for (Method method : getterClass) {
            try {
                method.setAccessible(true);
                if(method.invoke(lieu) != null ) {
                    String result = method.invoke(lieu).toString();
                    String propertyName =  method.getName().split("get")[1].toLowerCase();
                    String propertyType = method.getGenericReturnType().getTypeName().substring(method.getGenericReturnType().getTypeName().lastIndexOf('.') + 1);

                    if(!propertyName.equals("id")) {
                        updateString += propertyName + " = ?,";
                        listeValeurs.add(result);
                        listeType.add(propertyType);
                    }
                }
            }catch(InvocationTargetException | IllegalAccessException e) {
                System.out.println("ko");
            }
        }

        try {
            updateString = updateString.substring(0, updateString.length() -1);
            String requete = "UPDATE " +this.table +" SET " +updateString +" WHERE id = ?;";
            PreparedStatement statement = this.connect.prepareStatement(requete);
            for(int i = 0; i < listeValeurs.size(); i++) {
                if(listeType.get(i).equals("String")) {
                    statement.setString(i + 1, listeValeurs.get(i));
                } else {
                    statement.setInt(i + 1, Integer.parseInt(listeValeurs.get(i)));
                }
            }

            statement.setInt(listeValeurs.size() + 1, lieu.getId());
            statement.executeUpdate();
            statement.close();

        } catch (SQLException e) {
            GestionErreurSQL.gestionErreur(e);
        }
    };

    public boolean supprimer(T lieu) {
        try {
            String requete = "DELETE FROM " +this.table +" WHERE id = ?";
            PreparedStatement statement = this.connect.prepareStatement(requete);
            statement.setInt(1, lieu.getId());
            statement.executeUpdate();

            statement.close();
            return false;

        } catch (SQLException e) {
            GestionErreurSQL.gestionErreur(e);
            return true;
        }
    }

    public boolean checkIfExist(Integer id) {
        Boolean check = false;
        try {
            String requete = "SELECT * FROM " + this.table + " WHERE id = ?";
            PreparedStatement statement = this.connect.prepareStatement(requete);
            statement.setLong(1, id);
            ResultSet curseur = statement.executeQuery();
            while (curseur.next()) {
                check = true;
            }

            curseur.close();
            statement.close();
        } catch (SQLException e) {
            GestionErreurSQL.gestionErreur(e);
        }
        return check;
    }

    public boolean checkIfExistByCode(String code) {
        Boolean check = false;
        try {
            String requete = "SELECT * FROM " + this.table + " WHERE code = ?";
            PreparedStatement statement = this.connect.prepareStatement(requete);
            statement.setString(1, code);
            ResultSet curseur = statement.executeQuery();
            while (curseur.next()) {
                check = true;
            }

            curseur.close();
            statement.close();
        } catch (SQLException e) {
            GestionErreurSQL.gestionErreur(e);
        }
        return check;
    }

    public  List<Lieu> getListeLieu () {
        List<Lieu> listeLieux = new ArrayList<>();
        try {
            String requete = "SELECT * FROM " + this.table;
            PreparedStatement statement = this.connect.prepareStatement(requete);
            ResultSet curseur = statement.executeQuery();
            while (curseur.next()) {
                Integer idLieu = curseur.getInt("id");
                String nom = curseur.getString("nom");
                String code = curseur.getString("code");
                Integer population = curseur.getInt("population");
                Lieu lieu = new Lieu(idLieu, code, nom, population);
                listeLieux.add(lieu);
            }

            curseur.close();
            statement.close();
        } catch (SQLException e) {
            GestionErreurSQL.gestionErreur(e);
        }
        return listeLieux;
    }
}
