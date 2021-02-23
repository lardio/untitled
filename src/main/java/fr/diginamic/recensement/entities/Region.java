package fr.diginamic.recensement.entities;

/** Represente une Region.
 *
 * @author Sylvain
 */
public class Region extends Lieu {

    protected int pays_id;

    public int getPays_id() {
        return pays_id;
    }

    public Region(String code, String nom, int pays) {
        super(code, nom, 0, "region");
        this.pays_id = pays;
    }

    public Region(Integer id, String code, String nom, Integer population, int pays) {
        super(code, nom, population, "region");
        this.pays_id = pays;
        this.id = id;
    }

    /**
     * Ajoute un objet Ville a la propriété listeVille.
     * Incrémente la propriété polutation avec la population de la Ville
     * @see Ville
     * @param ville
     */
    public void addVille(Ville ville) {
        this.population += ville.population;
    }
}
