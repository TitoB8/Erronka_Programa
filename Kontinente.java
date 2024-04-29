package erronka3;

public class Kontinente {
    private int id;
    private String izena;

    public Kontinente(int id, String izena) {
        this.id = id;
        this.izena = izena;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIzena() {
        return izena;
    }

    public void setIzena(String izena) {
        this.izena = izena;
    }

    @Override
    public String toString() {
        return "- id: " + id + ", izena: " + izena + " \n";
    }  
}
