package fr.diginamic.recensement.entities;

public class Ville extends Lieu {

    protected Integer region_id;
    protected Integer departement_id;
    protected Integer pays_id;

    /** Represente une Ville.
     * A chaque creation d'une Ville, les autres Lieu passés en parametres du constructeur recoivent cette Ville en propriétés dans listeVilles.
     * La population de cette ville s'ajoute a chaque Lieu passés en constructeur.
     *
     * @author Sylvain
     */
    public Ville(String nom, int population, Integer departement_id, Integer region_id, Integer pays_id) {
        super(nom, nom, population, "ville");
        this.departement_id = departement_id;
        this.region_id = region_id;
        this.pays_id = pays_id;
    }

    public Ville(Integer id, String nom, int population, Integer departement, Integer region, Integer pays) {
        super(nom, nom, population, "ville");
        this.id = id;
        this.departement_id = departement;
        this.region_id = region;
        this.pays_id = pays;
    }

    public Ville(String nom, String b, String c) {
        super(nom, nom, 0, "ville");
        this.nom = nom;
    }

    public Integer getRegion_id() {
        return region_id;
    }

    public Integer getDepartement_id() {
        return departement_id;
    }

    public Integer getPays_id() { return pays_id; }

}
