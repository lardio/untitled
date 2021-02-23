package fr.diginamic.recensement.entities;

/** Represente un Departement.
 *
 * @author Sylvain
 */
public class Departement extends Lieu {

    protected Integer region_id;
    protected Integer pays_id;

    public Departement(String code, Integer region, Integer pays) {
        super(code, code, 0, "departement");
        this.pays_id = pays;
        this.region_id = region;
    }

    public Departement(Integer id, String code, Integer population, Integer region, Integer pays) {
        super(code, code, population, "departement");
        this.pays_id = pays;
        this.region_id = region;
        this.id = id;
    }



    public Integer getRegion_id() {
        return region_id;
    }

    public Integer getPays_id() { return pays_id; }

}
