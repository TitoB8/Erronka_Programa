package erronka3;

public class Prueba {

	public static void main(String[] args) {
		Konexioa konexioa = new Konexioa();
		String izena = "jburch";
		String erabiltzaile = "jburch";
		String con = null;
		
		
		if(konexioa.erabiltzailePasahitzaAurkitu(izena, erabiltzaile)) {
			con = "bien";
			System.out.println(con);
		}else {
			con = "mal";
			System.out.println(con);
		}
	}
}
