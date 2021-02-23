package fr.diginamic.recensement.entities;

/** Represente un lieu
 * @author sylvain
 */
public class Lieu implements Comparable {

    protected Integer population = 0;
    protected String code;
    protected String nom;
    protected Integer id; //pour la BDD
    protected String typeLieu; //pour la BDD

    public String getTypeLieu() {
        return typeLieu;
    }

    /**
     * Constructeur d'un Lieu
     * @param code - clé unique d'un lieu
     * @param nom - nom d'un lieu
     */
    public Lieu(String code, String nom, Integer population, String type) {
        this.typeLieu = type;
        this.population = population;
        this.code = code;
        this.nom = nom;
    }

    public Lieu(Integer id, String code, String nom, Integer population) {
        this.id = id;
        this.code = code;
        this.nom = nom;
        this.population = population;
    }

    /**
     * Affiche les informations d'un lieu dans la console. Son nom et sa population
     */
    public void getInformations() {
        System.out.println(this.nom + " => " +this.population + " habitants.");
    }

    /**
     * Tri des lieux en fonction de leur population
     * @param o
     * @return
     */
    @Override
    public int compareTo(Object o) {
        Lieu autreLieu = (Lieu)o;
        return this.population.compareTo(autreLieu.population);
    }

    public String getCode() { return code; }

    public String getNom() { return nom; }

    public Integer getPopulation() { return population; }

    public Integer getId() { return id; }

    public void setPopulation(Integer population) { this.population += population; }

    public void setCode(String code) { this.code = code; }

    public void setNom(String nom) { this.nom = nom; }

    public void setId(Integer id) { this.id = id; }
}
