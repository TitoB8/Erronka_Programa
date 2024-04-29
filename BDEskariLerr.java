package erronka3;

import java.util.ArrayList;

/**
 * Salmenta eskaeren datu-basea kudeatzen duen klasea.
 */
public class BDEskariLerr {
    // Atributuak
    ArrayList<EskariLerr> lista;

    // Sortzaileak
    /**
     * BDEskariLerr klasearen sortzailea. Lista objetuak hasieratzen ditu.
     */
    public BDEskariLerr() {
        lista = new ArrayList<>();
    }

    // Metodoak
    /**
     * Lista atributuaren balioa ezartzen du.
     * 
     * @param lista EskariLerr objektuen lista
     */
    public void setLista(ArrayList<EskariLerr> lista) {
        this.lista = lista;
    }

    /**
     * EskariLerr objektua gehitzen du listara.
     * 
     * @param eskari Gehitu nahi den EskariLerr objektua
     */
    public void gehituSaltzaile(EskariLerr eskari) {
        lista.add(eskari);
    }

    /**
     * EskariLerr objektua kendutzen du listatik.
     * 
     * @param eskari Kendu nahi den EskariLerr objektua
     */
    public void kenduSaltzaile(EskariLerr eskari) {
        lista.remove(eskari);
    }
}
