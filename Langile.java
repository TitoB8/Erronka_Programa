package erronka3;

import java.util.Date;

class Langile {
    private int ID;
    private String izena;
    private String abizenak;
    private String email;
    private String telefonoa;
    private Date data;
    private Nagusi nagusia;

    public Langile() {
        
    }

    public Langile(int pID, String pIzena, String pAbizenak, String pEmail, String pTelefonoa, Date pData, Nagusi pNagusia) {
        ID = pID;
        izena = pIzena;
        abizenak = pAbizenak;
        email = pEmail;
        telefonoa = pTelefonoa;
        data = pData;
        nagusia = pNagusia;
    }

    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        ID = iD;
    }

    public String getIzena() {
        return izena;
    }

    public void setIzena(String izena) {
        this.izena = izena;
    }

    public String getAbizenak() {
        return abizenak;
    }

    public void setAbizenak(String abizenak) {
        this.abizenak = abizenak;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefonoa() {
        return telefonoa;
    }

    public void setTelefonoa(String telefonoa) {
        this.telefonoa = telefonoa;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Nagusi getNagusia() {
        return nagusia;
    }

    public void setNagusia(Nagusi nagusia) {
        this.nagusia = nagusia;
    }

    @Override
    public String toString() {
        return "- ID: " + ID + ", izena: " + izena + ", abizenak: " + abizenak + ", email: " + email + ", telefonoa: "
                + telefonoa + ", data: " + data + ", nagusiaId: " + nagusia.getId() + ", nagusiaIzena: " + nagusia.getIzena() + ", nagusiaAbizena: " + nagusia.getAbizena() +"\n";
    }
}
