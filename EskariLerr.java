package erronka3;

import java.util.Date;

public class EskariLerr {
    // atributos
    int id;
    int idBezero;
    int idLerro;
    int kopurua;
    int idProduktu;
    int idSaltzaile;
    Date eskaeraData;
    String deskribapena;
    double salneurria;
    String produktuIzena;

    // sortzaileak
    public EskariLerr(int idBezero, int idLerro, int kopurua, int idProduktu, int idSaltzaile, Date eskaeraData, String deskribapena, double salneurria, String produktuIzena) {
        this.idBezero = idBezero;
        this.idLerro = idLerro;
        this.kopurua = kopurua;
        this.idProduktu = idProduktu;
        this.idSaltzaile = idSaltzaile;
        this.eskaeraData = eskaeraData;
        this.deskribapena = deskribapena;
        this.salneurria = salneurria;
        this.produktuIzena = produktuIzena;
    }
    
    //Eskariak ikusteko
    public EskariLerr(int idBezero, int id,int idProduktu,Date eskaeraData, String deskribapena, double salneurria, int kopurua) {
        this.idBezero = idBezero;
        this.id = id;
        this.idProduktu = idProduktu;
        this.idProduktu = idProduktu;
        this.eskaeraData = eskaeraData;
        this.deskribapena = deskribapena;
        this.salneurria = salneurria;
        this.kopurua = kopurua;
    }
    
    
    //ESKARI INSERTATZEKO. BESTEA AUTOMATIKOKI INSERTATZEN DA.
	public EskariLerr(int idBezero,int idProduktu, int kopurua, double salneurria) {
		this.idBezero = idBezero;
		this.idProduktu = idProduktu;
		this.kopurua = kopurua;
		this.salneurria = salneurria;
	}

	
	public String getProduktuIzena() {
		return produktuIzena;
	}

	public void setProduktuIzena(String produktuIzena) {
		this.produktuIzena = produktuIzena;
	}

	//metodoak
	public int getIdLerro() {
		return idLerro;
	}

	public void setIdLerro(int idLerro) {
		this.idLerro = idLerro;
	}

	public int getKopurua() {
		return kopurua;
	}

	public void setKopurua(int kopurua) {
		this.kopurua = kopurua;
	}

	public int getIdProduktu() {
		return idProduktu;
	}

	public void setIdProduktu(int idProduktu) {
		this.idProduktu = idProduktu;
	}

	public int getId() {
		return id;
	}
	public String getDeskribapena() {
		return deskribapena;
	}

	public void setDeskribapena(String deskribapena) {
		this.deskribapena = deskribapena;
	}

	public double getSalneurria() {
		return salneurria;
	}

	public void setSalneurria(double salneurria) {
		this.salneurria = salneurria;
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
		return idProduktu;
	}
	public void setIdEgoera(int idEgoera) {
		this.idProduktu = idEgoera;
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
        return "ID Bezero: " + idBezero + " - ID Eskaria: " + id + " - Deskribapena: " + deskribapena;
    }
}
