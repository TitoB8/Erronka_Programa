package erronka3;

public class Bezero {
	//atributuak
	int id;
	String izena;
	String abizena;
	String helbidea;
	String emaila;
	String erabiltzaile;
	String pasahitza;
	//sortzaileak
	public Bezero(int id, String izena, String abizena, String helbidea, String emaila, String erabiltzaile,
			String pasahitza) {
		super();
		this.id = id;
		this.izena = izena;
		this.abizena = abizena;
		this.helbidea = helbidea;
		this.emaila = emaila;
		this.erabiltzaile = erabiltzaile;
		this.pasahitza = pasahitza;
	}
	
	public Bezero(String izena, String abizena, String helbidea, String emaila, String erabiltzaile,
			String pasahitza) {
		this.izena = izena;
		this.abizena = abizena;
		this.helbidea = helbidea;
		this.emaila = emaila;
		this.erabiltzaile = erabiltzaile;
		this.pasahitza = pasahitza;
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
	public String getAbizena() {
		return abizena;
	}
	public void setAbizena(String abizena) {
		this.abizena = abizena;
	}
	public String getHelbidea() {
		return helbidea;
	}
	public void setHelbidea(String helbidea) {
		this.helbidea = helbidea;
	}
	public String getEmaila() {
		return emaila;
	}
	public void setEmaila(String emaila) {
		this.emaila = emaila;
	}
	public String getErabiltzaile() {
		return erabiltzaile;
	}
	public void setErabiltzaile(String erabiltzaile) {
		this.erabiltzaile = erabiltzaile;
	}
	public String getPasahitza() {
		return pasahitza;
	}
	public void setPasahitza(String pasahitza) {
		this.pasahitza = pasahitza;
	}
}
