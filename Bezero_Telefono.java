package erronka3;

public class Bezero_Telefono {
    private int ID;
    private int idBezero;
    private String zenbakia;

    public Bezero_Telefono(int pId, int pIdBezero, String pZenbakia) {
        this.ID = pId;
        this.idBezero = pIdBezero;
        this.zenbakia = pZenbakia;
    }

    public int getId() {
        return ID;
    }

    public void setId(int ID) {
        this.ID = ID;
    }

    public int getIdBezero() {
        return idBezero;
    }

    public void setIdBezero(int idBezero) {
        this.idBezero = idBezero;
    }

    public String getZenbakia() {
        return zenbakia;
    }

    public void setZenbakia(String zenbakia) {
        this.zenbakia = zenbakia;
    }

    @Override
    public String toString() {
        return "- ID: " + ID + ", idBezero: " + idBezero + ", zenbakia: " + zenbakia + " \n";
    }
}
