package erronka3;

public class Nagusi {
	private int ID;
	private String izena;
	private String abizenak;
	public int getId() {
		return ID;
	}
	public void setId(int ID) {
		this.ID = ID;
	}
	public String getIzena() {
		return izena;
	}
	public void setIzena(String izena) {
		this.izena = izena;
	}
	public String getAbizena() {
		return abizenak;
	}
	public void setAbizena(String abizena) {
		this.abizenak = abizena;
	}
	public Nagusi() {}
	public Nagusi(int ID, String izena, String abizena) {
		super();
		this.ID = ID;
		this.izena = izena;
		this.abizenak = abizena;
	}
	@Override
	public String toString() {
		return "- ID: " + ID + ", izena: " + izena + ", abizena: " + abizenak + "\n";
	}
}
