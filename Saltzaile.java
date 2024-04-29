package erronka3;

import java.util.Date;

public class Saltzaile {
	//atributuak
	int id;
	String izena;
	String abizena;
	String emaila;
	String telefonoa;
	Date kontratazioData;
	int idNagusi;
	//sortzaileak
	public Saltzaile(int id, String izena, String abizena, String emaila, String telefonoa, Date kontratazioData,
			int idNagusi) {
		super();
		this.id = id;
		this.izena = izena;
		this.abizena = abizena;
		this.emaila = emaila;
		this.telefonoa = telefonoa;
		this.kontratazioData = kontratazioData;
		this.idNagusi = idNagusi;
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
	public String getEmaila() {
		return emaila;
	}
	public void setEmaila(String emaila) {
		this.emaila = emaila;
	}
	public String getTelefonoa() {
		return telefonoa;
	}
	public void setTelefonoa(String telefonoa) {
		this.telefonoa = telefonoa;
	}
	public Date getKontratazioData() {
		return kontratazioData;
	}
	public void setKontratazioData(Date kontratazioData) {
		this.kontratazioData = kontratazioData;
	}
	public int getIdNagusi() {
		return idNagusi;
	}
	public void setIdNagusi(int idNagusi) {
		this.idNagusi = idNagusi;
	}
}
