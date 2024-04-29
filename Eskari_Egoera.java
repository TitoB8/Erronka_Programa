package erronka3;

public class Eskari_Egoera {
    private int ID;
    private String deskribapena;

    public Eskari_Egoera(int ID, String deskribapena) {
        this.ID = ID;
        this.deskribapena = deskribapena;
    }

    public int getId() {
        return ID;
    }

    public void setId(int ID) {
        this.ID = ID;
    }

    public String getDeskribapena() {
        return deskribapena;
    }

    public void setDeskribapena(String deskribapena) {
        this.deskribapena = deskribapena;
    }

    @Override
    public String toString() {
        return "- ID: " + ID + ", deskribapena=" + deskribapena + "\n";
    }
}
