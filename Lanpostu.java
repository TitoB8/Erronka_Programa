package erronka3;

public class Lanpostu {
    private int lanpostuID;
    private String lanpostuIzena;
    
    public Lanpostu() {}

    public Lanpostu(int lanpostuID, String lanpostuIzena) {
        super();
        this.lanpostuID = lanpostuID;
        this.lanpostuIzena = lanpostuIzena;
    }

    public int getLanpostuID() {
        return lanpostuID;
    }

    public void setLanpostuID(int lanpostuID) {
        this.lanpostuID = lanpostuID;
    }

    public String getLanpostuIzena() {
        return lanpostuIzena;
    }

    public void setLanpostuIzena(String lanpostuIzena) {
        this.lanpostuIzena = lanpostuIzena;
    }

    @Override
    public String toString() {
        return "- lanpostuID: " + lanpostuID + ", deskribapena: " + lanpostuIzena + "\n";
    }

}
