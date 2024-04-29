package erronka3;
public class Inbentario {
    private int idProduktu;
    private int idBiltegi;
    private int kopuru;

    public Inbentario(int idProduktu, int idBiltegi, int kopuru) {
        this.idProduktu = idProduktu;
        this.idBiltegi = idBiltegi;
        this.kopuru = kopuru;
    }

    public int getIdProduktu() {
        return idProduktu;
    }

    public void setIdProduktu(int idProduktu) {
        this.idProduktu = idProduktu;
    }

    public int getIdBiltegi() {
        return idBiltegi;
    }

    public void setIdBiltegi(int idBiltegi) {
        this.idBiltegi = idBiltegi;
    }

    public int getKopuru() {
        return kopuru;
    }

    public void setKopuru(int kopuru) {
        this.kopuru = kopuru;
    }

    @Override
    public String toString() {
        return "- idProduktu: " + idProduktu + ", idBiltegi: " + idBiltegi + ", kopuru: " + kopuru + "\n";
    }
    
    
}
