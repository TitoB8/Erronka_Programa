package erronka3;

public class Biltegi {
	//atributuak
	int id;
	String izena;
	int idKokaleku;
	//sortzaileak
	public Biltegi(int id, String izena, int idKokaleku){
		super();
		this.id = id;
		this.izena = izena;
		this.idKokaleku = idKokaleku;
	}
	
	//metodoak
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
	public int getIdKokaleku() {
		return idKokaleku;
	}
	public void setIdKokaleku(int idKokaleku) {
		this.idKokaleku = idKokaleku;
	}
}
