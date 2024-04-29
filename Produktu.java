package erronka3;

public class Produktu {
	int id;
	String izena;
	String deskribapena;
	double balioa;
	double salneurria;
	int id_kategoria;
	
	public Produktu(int id, String izena, String deskribapena, double balioa, double salneurria, int id_kategoria) {
		super();
		this.id = id;
		this.izena = izena;
		this.deskribapena = deskribapena;
		this.balioa = balioa;
		this.salneurria = salneurria;
		this.id_kategoria = id_kategoria;
	}
	
	public Produktu(int id, String izena, String deskribapena, double salneurria) {
		this.id = id;
		this.izena = izena;
		this.deskribapena = deskribapena;
		this.salneurria = salneurria;
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
	public String getDeskribapena() {
		return deskribapena;
	}
	public void setDeskribapena(String deskribapena) {
		this.deskribapena = deskribapena;
	}
	public double getBalioa() {
		return balioa;
	}
	public void setBalioa(double balioa) {
		this.balioa = balioa;
	}
	public double getSalneurria() {
		return salneurria;
	}
	public void setSalneurria(double salneurria) {
		this.salneurria = salneurria;
	}
	public int getId_kategoria() {
		return id_kategoria;
	}
	public void setId_kategoria(int id_kategoria) {
		this.id_kategoria = id_kategoria;
	}
}
