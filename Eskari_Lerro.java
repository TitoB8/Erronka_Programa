package erronka3;

public class Eskari_Lerro {
    private int idEskari;
    private int idLerro;
    private int idProduktu;
    private int Kopuru;
    private double Salneurri;

    public Eskari_Lerro(int idEskari, int idLerro, int idProduktu, int kopuru, double d) {
        this.idEskari = idEskari;
        this.idLerro = idLerro;
        this.idProduktu = idProduktu;
        Kopuru = kopuru;
        Salneurri = d;
    }

    public int getIdEskari() {
        return idEskari;
    }

    public void setIdEskari(int idEskari) {
        this.idEskari = idEskari;
    }

    public int getIdLerro() {
        return idLerro;
    }

    public void setIdLerro(int idLerro) {
        this.idLerro = idLerro;
    }

    public int getIdProduktu() {
        return idProduktu;
    }

    public void setIdProduktu(int idProduktu) {
        this.idProduktu = idProduktu;
    }

    public int getKopuru() {
        return Kopuru;
    }

    /**
     * Eskari bakoitzeko produktu kopurua ezartzen du.
     * @param kopuru Eskari bakoitzeko produktu kopurua.
     */
    public void setKopuru(int kopuru) {
        Kopuru = kopuru;
    }

    /**
     * Eskari bakoitzeko produktuen salneurria itzultzen du.
     * @return Eskari bakoitzeko produktuen salneurria.
     */
    public double getSalneurri() {
        return Salneurri;
    }

    /**
     * Eskari bakoitzeko produktuen salneurria ezartzen du.
     * @param salneurri Eskari bakoitzeko produktuen salneurria.
     */
    public void setSalneurri(int salneurri) {
        Salneurri = salneurri;
    }

    /**
     * Klasearen testu errepresentazioa itzultzen du.
     * @return Klasearen testu errepresentazioa.
     */
    @Override
    public String toString() {
        return "- idEskari: " + idEskari + ", idLerro: " + idLerro + ", idProduktu: " + idProduktu
                + ", Kopuru: " + Kopuru + ", Salneurri: " + Salneurri + "\n";
    }
    
}
