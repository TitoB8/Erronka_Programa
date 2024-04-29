package erronka3;

import java.util.ArrayList;

/**
 * Eskari egoeren datu-basearen kudeaketa klasea.
 */
public class BDEskari_Egoera {
    /** Eskari egoera objektuen lista. */
    private ArrayList<Eskari_Egoera> listaEgoera;

    /**
     * BDEskari_Egoera klasearen konstruktorea.
     */
    public BDEskari_Egoera() {
        listaEgoera = new ArrayList<Eskari_Egoera>();
    }

    /**
     * Eskari egoera objektua listan gehitzen duen metodoa.
     * 
     * @param ee Gehitu nahi den Eskari_Egoera objektua
     */
    public void eskariEgoeraKargatu(Eskari_Egoera ee) {
        listaEgoera.add(ee);
    }

    /**
     * Klasearen propietateen string errepresentazioa itzultzen duen metodoa.
     * 
     * @return Klasearen propietateen string errepresentazioa
     */
    @Override
    public String toString() {
        return "BDEskari_Egoera [listaEgoera=" + listaEgoera + "]";
    }
}
