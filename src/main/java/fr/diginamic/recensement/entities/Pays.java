package fr.diginamic.recensement.entities;

/** Represente un Pays. Lieu le plus haut dans la hiérarchie.
 * Contient des listes completes de chaque type de Lieu.
 * Chaque liste contient des listes de Lieu inférieur dans la hiérarchie.
 *
 * @author Sylvain
 */
public class Pays extends Lieu {

    public Pays(String code) {
        super(code, code, 0, "pays");
    }

    public Pays(String code, int population, int id) {
        super(code, code, population, "pays");
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
