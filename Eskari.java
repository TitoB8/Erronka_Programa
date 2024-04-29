package erronka3;

import java.util.Date;

public class Eskari {
	//atributuak
	int id;
	int idBezero;
	int idEgoera;
	int idSaltzaile;
	Date eskaeraData;
	//sortzaileak
	public Eskari(int id, int idBezero, int idEgoera, int idSaltzaile, Date eskaeraData) {
		super();
		this.id = id;
		this.idBezero = idBezero;
		this.idEgoera = idEgoera;
		this.idSaltzaile = idSaltzaile;
		this.eskaeraData = eskaeraData;
	}
	//metodoak
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdBezero() {
		return idBezero;
	}
	public void setIdBezero(int idBezero) {
		this.idBezero = idBezero;
	}
	public int getIdEgoera() {
		return idEgoera;
	}
	public void setIdEgoera(int idEgoera) {
		this.idEgoera = idEgoera;
	}
	public int getIdSaltzaile() {
		return idSaltzaile;
	}
	public void setIdSaltzaile(int idSaltzaile) {
		this.idSaltzaile = idSaltzaile;
	}
	public Date getEskaeraData() {
		return eskaeraData;
	}
	public void setEskaeraData(Date eskaeraData) {
		this.eskaeraData = eskaeraData;
	}
	@Override
	public String toString() {
		return "Eskari [id=" + id + ", idBezero=" + idBezero + ", idEgoera=" + idEgoera + ", idSaltzaile=" + idSaltzaile
				+ ", eskaeraData=" + eskaeraData + "]";
	}
	
	
}
