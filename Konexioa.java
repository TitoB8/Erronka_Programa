package erronka3;

import java.sql.*;
import java.util.*;
import java.util.Date;

import javax.swing.JOptionPane;

import oracle.jdbc.OracleTypes;

public class Konexioa {

   private String url;
   private String user;
   private String pass;
   
   //defektuzko sortzailea 
   public Konexioa(){
	  this.url="jdbc:oracle:thin:@localhost:1521:xe";
      this.user="ERRONKA";
      this.pass="ERRONKA";
   }  
   //konexioa egin
   public Connection konexioa (){
      Connection conn = null;
      try{
         conn = DriverManager.getConnection(url, user, pass);
         return conn;
      } catch (SQLException e){
         System.out.println("Konexio errorea: "+e);
      }
      return conn;
   }
   
   public void eskariEguneratu(Eskari eskari) {
	    String updateSQL = "{CALL ESKARI_EGUNERATU(?,?,?,?,?)}";
	    try {
	        Connection conn = konexioa();
	        CallableStatement cS = conn.prepareCall(updateSQL);
	        cS.setInt(1, eskari.getId());
	        cS.setInt(2, eskari.getIdBezero());
	        cS.setInt(3, eskari.getIdEgoera());
	        cS.setInt(4, eskari.getIdSaltzaile());
	        java.sql.Date sqlDate = new java.sql.Date(eskari.getEskaeraData().getTime());
	        cS.setDate(5, sqlDate);
	        cS.execute();
	        conn.close();
	    } catch (SQLException e) {
	        System.out.println("Errorea eskaria eguneratzen: " + e);
	    }
	}
   
   public void biltegiEguneratu(Biltegi biltegi) {
	   String updateSQL = "UPDATE BILTEGI SET IZENA=?, ID_KOKALEKU=? WHERE ID="+biltegi.getId();
	   try {
	        Connection conn = konexioa();
	        PreparedStatement pS = conn.prepareStatement(updateSQL);
	        pS.setString(1, biltegi.getIzena());
	        pS.setInt(2, biltegi.getIdKokaleku());
	        pS.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println("Errorea biltegia eguneratzen: " + e);
	    }
   }
   
   public void produktuEguneratu(Produktu produktu) {
	   String updateSQL = "UPDATE PRODUKTU SET IZENA=?, DESKRIBAPENA=?, BALIOA=?, SALNEURRIA=?, ID_KATEGORIA=? WHERE ID="+produktu.getId();
	   try {
	        Connection conn = konexioa();
	        PreparedStatement pS = conn.prepareStatement(updateSQL);
	        pS.setString(1, produktu.getIzena());
	        pS.setString(2, produktu.getDeskribapena());
	        pS.setDouble(3, produktu.getBalioa());
	        pS.setDouble(4, produktu.getSalneurria());
	        pS.setInt(5, produktu.getId_kategoria());
	        pS.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println("Errorea produktua eguneratzen: " + e);
	    }
   }
   
   public int produktuGehitu(Produktu produktu) {
	     String updateSQL = "INSERT INTO PRODUKTU(IZENA, DESKRIBAPENA, BALIOA, SALNEURRIA, ID_KATEGORIA) VALUES(?, ?, ?, ?, ?)";
	     int idBerri=0;
	    try {
	        Connection conn = konexioa();
	        PreparedStatement pS = conn.prepareStatement(updateSQL);
	     
	        pS.setString(1, produktu.getIzena());
	        pS.setString(2, produktu.getDeskribapena());
	        pS.setDouble(3, produktu.getBalioa());
	        pS.setDouble(4, produktu.getSalneurria());
	        pS.setInt(5, produktu.getId_kategoria());
	        pS.executeUpdate();
	        
	        Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery("SELECT MAX(ID) AS KODE FROM PRODUKTU");
	        if (rs.next()) {
	            idBerri = rs.getInt("KODE");
	        }
	    } catch (SQLException e) {
	        System.out.println("Errorea produktua gehitzeko: " + e);
	    }
	    return idBerri;
	}
   
   public boolean deleteBiltegi(int id) {
	   boolean bool=false;
	   String sql = "{CALL BILTEGI_EZABATU(?)}";
		   try (Connection conn = konexioa();
				   CallableStatement cStmt = conn.prepareCall(sql)) {
			   cStmt.setInt(1, id);
			   cStmt.execute();
			   bool=true;
		   }catch (SQLException e) {
			   System.out.println("Errorea biltegia ezabatzerakoan: "+e);
			   }
		   return bool;
	}
   
   public boolean deleteKokaleku(int id) {
	   boolean bool=true;
	   String sql = "{CALL KOKALEKU_EZABATU(?)}";
		   try (Connection conn = konexioa();
				   CallableStatement cStmt = conn.prepareCall(sql)) {
			   cStmt.setInt(1, id);
			   cStmt.execute();
		   }catch (SQLException e) {
			   System.out.println("Errorea biltegia ezabatzerakoan: "+e);
			   bool=true;
			   }
		   return bool;
	}
   
   public Biltegi biltegiLortu(int id) {
	   Biltegi bil=null;
	   String sql = "{CALL BILTEGI_LORTU(?,?,?)}";
	   try (Connection conn = konexioa();
			   CallableStatement cStmt = conn.prepareCall(sql)) {
		   cStmt.setInt(1, id);
		   cStmt.registerOutParameter(2,Types.VARCHAR);
		   cStmt.registerOutParameter(3,Types.INTEGER);
		   cStmt.execute();
		   bil= new Biltegi(id, cStmt.getString(2), cStmt.getInt(3));
		   
			   }	catch (SQLException e) {
				   System.out.println("Errorea bezeroa lortzerakoan: "+e);
			   }
	   return bil;
   }
   
   public Eskari eskariLortu(int id) {
	   Eskari esk=null;
	   String sql = "SELECT ID_BEZERO, ID_EGOERA, ID_SALTZAILE, ESKAERA_DATA FROM ESKARI WHERE ID=?";
	   try (Connection conn = konexioa();
			   PreparedStatement pS = conn.prepareStatement(sql)) {
			   pS.setInt(1, id);
			   ResultSet rs = pS.executeQuery();
			   while (rs.next()) {
				   int idBez = rs.getInt("ID_BEZERO");
				   int idEgo = rs.getInt("ID_EGOERA");
				   int idSal = rs.getInt("ID_SALTZAILE");
				   Date eskData = rs.getDate("ESKAERA_DATA");
			       esk = new Eskari(id, idBez,idEgo, idSal,eskData);
		      }	
	   }catch (SQLException e) {
				   System.out.println("Errorea bezeroa lortzerakoan: "+e);
			   }
	   return esk;
   }
   
   public Produktu produktuLortu(int id) {
	   Produktu prod=null;
	   String sql = "SELECT IZENA, DESKRIBAPENA, BALIOA, SALNEURRIA, ID_KATEGORIA FROM PRODUKTU WHERE ID=?";
	   try (Connection conn = konexioa();
			   PreparedStatement pS = conn.prepareStatement(sql)) {
			   pS.setInt(1, id);
			   ResultSet rs = pS.executeQuery();
			   while (rs.next()) {
				   String izena= rs.getString("IZENA");
				   String deskribapena= rs.getString("DESKRIBAPENA");
				   double balioa = rs.getDouble("BALIOA");
				   double salneurri= rs.getDouble("SALNEURRIA");
				   int id_kategoria= rs.getInt("ID_KATEGORIA");
			       prod = new Produktu(id, izena,deskribapena, balioa,salneurri,id_kategoria);
		      }	
	   }catch (SQLException e) {
				   System.out.println("Errorea bezeroa lortzerakoan: "+e);
			   }
	   return prod;
   }
   
   
   public Herrialde herrialdeLortu(String id) {
	   Herrialde her=null;
	   String sql = "SELECT IZENA, ID_KONTINENTE FROM HERRIALDE WHERE ID=?";
	   try (Connection conn = konexioa();
			   PreparedStatement pS = conn.prepareStatement(sql)) {
			   pS.setString(1, id);
			   ResultSet rs = pS.executeQuery();
			   while (rs.next()) {
				   String izen = rs.getString("IZENA");
				   int idKon = rs.getInt("ID_KONTINENTE");
			       her = new Herrialde(id, izen,idKon);
		      }	
	   }catch (SQLException e) {
				   System.out.println("Errorea bezeroa lortzerakoan: "+e);
			   }
	   return her;
   }
   
   public List<String> biltegiIDLortuArray() {
	    List<String> idBezero= new ArrayList<>();
	    String sql = "SELECT ID FROM BILTEGI";
	    try (Connection conn = konexioa();
	        PreparedStatement pS = conn.prepareStatement(sql)) {
	        ResultSet rs = pS.executeQuery();
	        while (rs.next()) {
	            idBezero.add(rs.getString("ID"));
	        }
	    } catch (SQLException e) {
	        System.out.println("Errorea biltegi ID-ak kontsultatzean: " + e);
	    }
	    return idBezero;
	}
   
   public boolean deleteTelefono(int IDB) {
	   boolean bool=false;
	   String sql = "{CALL TELEFONO_EZABATU(?)}";
		   try (Connection conn = konexioa();
				   CallableStatement cStmt = conn.prepareCall(sql)) {
			   cStmt.setInt(1, IDB);
			   cStmt.execute();
			   bool=true;
		   }catch (SQLException e) {
			   System.out.println("Errorea telefonoa ezabatzerakoan: "+e);
			   }
		   return bool;
	}
   
   public void telefonoEguneratu(Bezero_Telefono bT, String lehenZenbaki) {
	   String updateSQL = "UPDATE BEZERO_TELEFONO SET ZENBAKIA=? WHERE ID_BEZERO=?";
	   try {
	        Connection conn = konexioa();
	        PreparedStatement pS = conn.prepareStatement(updateSQL);
	    
	        pS.setString(1, bT.getZenbakia());
	        pS.setInt(2,bT.getIdBezero());
	        pS.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println("Errorea bezeroa eguneratzen: " + e);
	    }
   }
   
   public List<String> kokalekuIdLortuArray() {
	    List<String> idKokaleku= new ArrayList<>();
	    String sql = "SELECT ID FROM KOKALEKU";
	    try (Connection conn = konexioa();
	        PreparedStatement pS = conn.prepareStatement(sql)) {
	        ResultSet rs = pS.executeQuery();
	        while (rs.next()) {
	            idKokaleku.add(rs.getString("ID"));
	        }
	    } catch (SQLException e) {
	        System.out.println("Errorea kokalekuaren ID-ak kontsultatzean: " + e);
	    }
	    return idKokaleku;
	}
   
   public List<String> produktuIdLortuArray() {
	    List<String> idProduktu= new ArrayList<>();
	    String sql = "SELECT ID FROM PRODUKTU";
	    try (Connection conn = konexioa();
	        PreparedStatement pS = conn.prepareStatement(sql)) {
	        ResultSet rs = pS.executeQuery();
	        while (rs.next()) {
	            idProduktu.add(rs.getString("ID"));
	        }
	    } catch (SQLException e) {
	        System.out.println("Errorea produktuaren ID-ak kontsultatzean: " + e);
	    }
	    return idProduktu;
	}
   
   public List<String> produktuIdIzenLortuArray() {
	    List<String> idProduktu= new ArrayList<>();
	    String sql = "SELECT ID, IZENA FROM PRODUKTU ORDER BY ID";
	    try (Connection conn = konexioa();
	        PreparedStatement pS = conn.prepareStatement(sql)) {
	        ResultSet rs = pS.executeQuery();
	        while (rs.next()) {
	            idProduktu.add("id: "+rs.getString("ID")+" izena: "+rs.getString("IZENA"));
	        }
	    } catch (SQLException e) {
	        System.out.println("Errorea produktuaren ID-ak kontsultatzean: " + e);
	    }
	    return idProduktu;
	}
   
   public List<Inbentario> inbentarioLortuArray() {
	    List<Inbentario> inbLista= new ArrayList<>();
	    String sql = "SELECT * FROM INBENTARIO ORDER BY ID_BILTEGI";
	    try (Connection conn = konexioa();
	    		PreparedStatement pS = conn.prepareStatement(sql)) {
	        	ResultSet rs = pS.executeQuery();
	        while (rs.next()) {
	        	int idProd= rs.getInt("ID_PRODUKTU");
	    		int idBil= rs.getInt("ID_BILTEGI");
	    		int Kopuru= rs.getInt("KOPURUA");
	    		Inbentario inb = new Inbentario (idProd, idBil,Kopuru);
	            inbLista.add(inb);
	        }
	    } catch (SQLException e) {
	        System.out.println("Errorea inbentarioak kontsultatzean: " + e);
	    }
	    return inbLista;
	}
   
   public ArrayList<Integer> ezDaudenProduktuak(int kode) {
	   ArrayList<Integer> idLista= new ArrayList<Integer>();		
	   try (Connection conn=konexioa();
	        CallableStatement stmt = conn.prepareCall("{CALL EZ_DAUDEN_PRODUKTUAK(?, ?)}")) {
	   			stmt.setInt(1, kode);
	            stmt.registerOutParameter(2, OracleTypes.CURSOR);
	            stmt.execute();
	            ResultSet rs = (ResultSet) stmt.getObject(2);
	            while (rs.next()) {
	                int id_produktu = rs.getInt("ID_PRODUKTU");
	                idLista.add(id_produktu);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	   return idLista;
   }
   
   public Inbentario inbentarioLortu(int idProd, int idBil) {
	   Inbentario inb=null;
	   String sql = "SELECT KOPURUA FROM INBENTARIO WHERE ID_PRODUKTU=? AND ID_BILTEGI=?";
	   try (Connection conn = konexioa();
			   PreparedStatement pS = conn.prepareStatement(sql)) {
			   pS.setInt(1, idProd);
			   pS.setInt(2, idBil);
			   ResultSet rs = pS.executeQuery();
			   while (rs.next()) {
				   int kop = rs.getInt("KOPURUA");
			       inb = new Inbentario(idProd, idBil, kop);
		      }	
	   }catch (SQLException e) {
				   System.out.println("Errorea bezeroa lortzerakoan: "+e);
			   }
	   return inb;
   }
   
   public Kokaleku kokalekuLortu(int id) {
	   Kokaleku kok=null;
	   String sql = "SELECT HELBIDEA, POSTAKODEA, UDALERRIA, PROBINTZIA, ID_HERRIALDE FROM KOKALEKU WHERE ID=?";
	   try (Connection conn = konexioa();
			   PreparedStatement pS = conn.prepareStatement(sql)) {
			   pS.setInt(1, id);
			   ResultSet rs = pS.executeQuery();
			   while (rs.next()) {
				   String helbide = rs.getString("HELBIDEA");
				   String postaKode=rs.getString("POSTAKODEA");
				   if (postaKode==null) {
					   postaKode=" ";
				   }
				   String udalerria= rs.getString("UDALERRIA");
				   String probintzia= rs.getString("PROBINTZIA");
				   if (probintzia==null) {
					   probintzia=" ";
				   }
				   String idHerrialde= rs.getString("ID_HERRIALDE");
			       kok = new Kokaleku(id, helbide, postaKode, udalerria, probintzia, idHerrialde );
		      }	
	   }catch (SQLException e) {
				   System.out.println("Errorea bezeroa lortzerakoan: "+e);
			   }
	   return kok;
   }
   
   public List<String> eskariIdLortuArray() {
	    List<String> idKokaleku= new ArrayList<>();
	    String sql = "SELECT ID FROM ESKARI";
	    try (Connection conn = konexioa();
	        PreparedStatement pS = conn.prepareStatement(sql)) {
	        ResultSet rs = pS.executeQuery();
	        while (rs.next()) {
	            idKokaleku.add(rs.getString("ID"));
	        }
	    } catch (SQLException e) {
	        System.out.println("Errorea eskarien ID-ak kontsultatzean: " + e);
	    }
	    return idKokaleku;
	}
   
   public List<String> biltegiIdLortuArray() {
	    List<String> idBiltegi= new ArrayList<>();
	    String sql = "SELECT ID FROM BILTEGI";
	    try (Connection conn = konexioa();
	        PreparedStatement pS = conn.prepareStatement(sql)) {
	        ResultSet rs = pS.executeQuery();
	        while (rs.next()) {
	            idBiltegi.add(rs.getString("ID"));
	        }
	    } catch (SQLException e) {
	        System.out.println("Errorea biltegien ID-ak kontsultatzean: " + e);
	    }
	    return idBiltegi;
	}
   
   public List<String> telefonoBezeroIdLortuArray() {
	    List<String> idTelefono= new ArrayList<>();
	    String sql = "SELECT ID_BEZERO, ZENBAKIA FROM BEZERO_TELEFONO ORDER BY ID_BEZERO";
	    try (Connection conn = konexioa();
	        PreparedStatement pS = conn.prepareStatement(sql)) {
	        ResultSet rs = pS.executeQuery();
	        while (rs.next()) {
	            idTelefono.add("Bezero_Id: "+rs.getString("ID_BEZERO")+" Zenbakia: "+rs.getString("ZENBAKIA"));
	        }
	    } catch (SQLException e) {
	        System.out.println("Errorea telefonoaren bezero ID-ak kontsultatzean: " + e);
	    }
	    return idTelefono;
	}
  
  public String telefonoZenbakiLortu(int BezID) {
	   String telefonoZenbaki= null;
	   String sql = "{CALL TELEFONO_BEZERO_LORTU(?,?)}";
	   try (Connection conn = konexioa();
			   CallableStatement cStmt = conn.prepareCall(sql)) {
		   cStmt.setInt(1, BezID);
		   cStmt.registerOutParameter(2,Types.VARCHAR);
		   cStmt.execute();
		   telefonoZenbaki= cStmt.getString(2);
			   }	catch (SQLException e) {
				   System.out.println("Errorea bezeroa lortzerakoan: "+e);
			   }
			   return telefonoZenbaki;
  }
   
   public boolean erabiltzailePasahitzaAurkitu(String erabiltzaile, String pasahitza) {
       boolean konfirmazioa = false;
       String prozedura = "{CALL KONFIRMAZIOA_ERABILTZAILE(?, ?, ?)}";

       try (Connection conn = konexioa();
            CallableStatement cStmt = conn.prepareCall(prozedura)) {
           cStmt.setString(1, erabiltzaile);
           cStmt.setString(2, pasahitza);
           cStmt.registerOutParameter(3, Types.BOOLEAN);
           cStmt.execute();
           konfirmazioa = cStmt.getBoolean(3);
       } catch (SQLException e) {
           System.out.println("Errorea erabiltzailea egiaztatzean: " + e);
       }

       return konfirmazioa;
   }
   
   public String bezeroIzenLortu(int id) {
	   String izenBezero= null;
	   String sql = "{CALL BEZERO_IZENA_LORTU(?,?)}";
	   try (Connection conn = konexioa();
			   CallableStatement cStmt = conn.prepareCall(sql)) {
		   cStmt.setInt(1, id);
		   cStmt.registerOutParameter(2,Types.VARCHAR);
		   cStmt.execute();
		   izenBezero = cStmt.getString(2);
			   }	catch (SQLException e) {
				   System.out.println("Errorea bezeroa lortzerakoan: "+e);
			   }
			   return izenBezero;
   }
   
   public boolean langilePasahitzaAurkitu(String erabiltzaile, String pasahitza) {
       boolean konfirmazioa = false;
       String prozedura = "{CALL KONFIRMAZIOA_SALTZAILEA(?, ?, ?)}";

       try (Connection conn = konexioa();
            CallableStatement cStmt = conn.prepareCall(prozedura)) {
           cStmt.setString(1, erabiltzaile);
           cStmt.setString(2, pasahitza);
           cStmt.registerOutParameter(3, Types.BOOLEAN);
           cStmt.execute();
           konfirmazioa = cStmt.getBoolean(3);
       } catch (SQLException e) {
           System.out.println("Errorea erabiltzailea egiaztatzean: " + e);
       }

       return konfirmazioa;
   }
   
   public List<String> produktuIzenLortu() {
	    List<String> izenProduktu = new ArrayList<>();
	    String sql = "SELECT IZENA FROM PRODUKTU";
	    try (Connection conn = konexioa();
	         PreparedStatement pS = conn.prepareStatement(sql)) {
	        ResultSet rs = pS.executeQuery();
	        while (rs.next()) {
	            izenProduktu.add(rs.getString("IZENA"));
	        }
	    } catch (SQLException e) {
	        System.out.println("Errorea produktu izenak kontsultatzean: " + e);
	    }
	    return izenProduktu;
	}

   public Produktu produktuBezeroLortu(String productName) {
	    Produktu produktu = null;
	    String sql = "SELECT ID, IZENA,DESKRIBAPENA, SALNEURRIA FROM PRODUKTU WHERE IZENA = ?";
	    try (Connection conn = konexioa();
	         PreparedStatement pS = conn.prepareStatement(sql)) {
	        pS.setString(1, productName);
	        ResultSet rs = pS.executeQuery();
	        while (rs.next()) {
	            int id = rs.getInt("ID");
	            String izena = rs.getString("IZENA");
	            String deskribapena = rs.getString("DESKRIBAPENA");
	            double salneurria = rs.getDouble("SALNEURRIA");
	         
	            produktu = new Produktu(id, izena,deskribapena, salneurria);
	        }
	    } catch (SQLException e) {
	        System.out.println("Errorea produktua kontsultatzean: " + e);
	    }
	    return produktu;
	}
   
   public List<EskariLerr> eskariakIkusi(int kodea) {
	    List<EskariLerr> eskariak = new ArrayList<>();

	    String sql = "{CALL ESKARIAK_IKUSI(?, ?)}";
	    try (Connection conn = konexioa();
	         CallableStatement cStmt = conn.prepareCall(sql)) {
	        cStmt.setInt(1, kodea);
	        cStmt.registerOutParameter(2, OracleTypes.CURSOR);
	        cStmt.execute();

	        try (ResultSet rs = (ResultSet) cStmt.getObject(2)) {
	            while (rs.next()) {
	                int idBezero = rs.getInt("ID_BEZERO");
	                int idEskari = rs.getInt("ID_ESKARI");
	                int idProduktu = rs.getInt("ID_PRODUKTU");
	                Date eskaeraData = rs.getDate("ESKAERA_DATA");
	                String deskribapena = rs.getString("DESKRIBAPENA");
	                double salneurria = rs.getDouble("SALNEURRIA");
	                int kopurua = rs.getInt("KOPURUA");

	                EskariLerr eskaria = new EskariLerr(idBezero, idEskari,idProduktu, eskaeraData, deskribapena, salneurria,kopurua);
	                eskariak.add(eskaria);
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("Errorea produktua kontsultatzean: " + e);
	    }

	    return eskariak;
	}
   
   public String BezeroIzen_Lortu(int id) {
	    String izenBezero = null;
	    String sql = "{CALL BEZERO_IZENA_LORTU(?, ?)}";
	    try (Connection conn = konexioa();
	         CallableStatement cStmt = conn.prepareCall(sql)) {
	        cStmt.setInt(1, id);
	        cStmt.registerOutParameter(2, Types.VARCHAR);
	        cStmt.execute();
	        izenBezero = cStmt.getString(2);
	    } catch (SQLException e) {
	        System.out.println("Errorea bezeroa lortzerakoan: " + e);
	    }
	    return izenBezero;
	}
   
   public int BezeroID_Lortu(String izena, String pass) {
	    int idBezero = 0;
	    String sql = "{CALL BEZERO_ID_LORTU(?, ?, ?)}";
	    try (Connection conn = konexioa();
	         CallableStatement cStmt = conn.prepareCall(sql)) {
	        cStmt.setString(1, izena);
	        cStmt.setString(2, pass);
	        cStmt.registerOutParameter(3, Types.INTEGER);
	        cStmt.execute();
	        idBezero = cStmt.getInt(3);
	    } catch (SQLException e) {
	        System.out.println("Errorea bezeroa lortzerakoan: " + e);
	    }
	    return idBezero;
	}
   
   public List<String> bezeroIDLortuArray() {
	    List<String> idBezero= new ArrayList<>();
	    String sql = "SELECT ID, IZENA FROM BEZERO ORDER BY ID";
	    try (Connection conn = konexioa();
	        PreparedStatement pS = conn.prepareStatement(sql)) {
	        ResultSet rs = pS.executeQuery();
	        while (rs.next()) {
	            idBezero.add("id: "+rs.getString("ID")+" izena: "+rs.getString("IZENA"));
	        }
	    } catch (SQLException e) {
	        System.out.println("Errorea bezero ID-ak kontsultatzean: " + e);
	    }
	    return idBezero;
	}
   
   public List<String> egoeraIDLortuArray() {
	    List<String> idEgoera= new ArrayList<>();
	    String sql = "SELECT ID FROM ESKARI_EGOERA";
	    try (Connection conn = konexioa();
	        PreparedStatement pS = conn.prepareStatement(sql)) {
	        ResultSet rs = pS.executeQuery();
	        while (rs.next()) {
	            idEgoera.add(rs.getString("ID"));
	        }
	    } catch (SQLException e) {
	        System.out.println("Errorea egoera ID-ak kontsultatzean: " + e);
	    }
	    return idEgoera;
	}
   
   public List<String> saltzaileIDLortuArray() {
	    List<String> idSaltzaile= new ArrayList<>();
	    String sql = "SELECT ID FROM SALTZAILE";
	    try (Connection conn = konexioa();
	        PreparedStatement pS = conn.prepareStatement(sql)) {
	        ResultSet rs = pS.executeQuery();
	        while (rs.next()) {
	            idSaltzaile.add(rs.getString("ID"));
	        }
	    } catch (SQLException e) {
	        System.out.println("Errorea saltzailearen ID-ak kontsultatzean: " + e);
	    }
	    return idSaltzaile;
	}
   
   public List<String> kontinenteIDLortuArray() {
	    List<String> idKontinente= new ArrayList<>();
	    String sql = "SELECT ID FROM Kontinente";
	    try (Connection conn = konexioa();
	        PreparedStatement pS = conn.prepareStatement(sql)) {
	        ResultSet rs = pS.executeQuery();
	        while (rs.next()) {
	            idKontinente.add(rs.getString("ID"));
	        }
	    } catch (SQLException e) {
	        System.out.println("Errorea kontinentearen ID-ak kontsultatzean: " + e);
	    }
	    return idKontinente;
	}
   
   public void bezeroEguneratu(Bezero bezero) {
	   String updateSQL = "UPDATE BEZERO SET IZENA=?, ABIZENA=?,HELBIDEA=?, EMAILA=?, ERABILTZAILE=?, PASAHITZA=? WHERE ID="+bezero.getId();
	   try {
	        Connection conn = konexioa();
	        PreparedStatement pS = conn.prepareStatement(updateSQL);
	     
	        pS.setString(1, bezero.getIzena());
	        pS.setString(2, bezero.getAbizena());
	        pS.setString(3, bezero.getHelbidea());
	        pS.setString(4, bezero.getEmaila());
	        pS.setString(5, bezero.getErabiltzaile());
	        pS.setString(6, bezero.getPasahitza());
	        pS.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println("Errorea bezeroa eguneratzen: " + e);
	    }
   }
   
   public void kokalekuEguneratu(Kokaleku kokaleku) {
	   String updateSQL = "UPDATE KOKALEKU SET HELBIDEA=?, POSTAKODEA=?, UDALERRIA=?, PROBINTZIA=?, ID_HERRIALDE=? WHERE ID="+kokaleku.getId();
	   try {
	        Connection conn = konexioa();
	        PreparedStatement pS = conn.prepareStatement(updateSQL);
	     
	        pS.setString(1, kokaleku.getHelbide());
	        pS.setString(2, kokaleku.getPostakodea());
	        pS.setString(3, kokaleku.getUdalerri());
	        pS.setString(4, kokaleku.getProbintzia());
	        pS.setString(5, kokaleku.getIdHerrialde());
	        pS.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println("Errorea kokaleku eguneratzen: " + e);
	    }
   }
   
   public void inbentarioEguneratu(Inbentario inbentario) {
	   String updateSQL = "UPDATE INBENTARIO SET KOPURUA=? WHERE ID_BILTEGI=? AND ID_PRODUKTU=?";
	   try {
	        Connection conn = konexioa();
	        PreparedStatement pS = conn.prepareStatement(updateSQL);
	        pS.setInt(1, inbentario.getKopuru());
	        pS.setInt(2, inbentario.getIdBiltegi());
	        pS.setInt(3, inbentario.getIdProduktu());
	        pS.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println("Errorea inbentarioa eguneratzen: " + e);
	    }
   }
   
   public Bezero bezeroLortu(int id) {
	   Bezero bez=null;
	   String sql = "{CALL BEZERO_LORTU(?,?,?,?,?,?,?)}";
	   try (Connection conn = konexioa();
			   CallableStatement cStmt = conn.prepareCall(sql)) {
		   cStmt.setInt(1, id);
		   cStmt.registerOutParameter(2,Types.VARCHAR);
		   cStmt.registerOutParameter(3,Types.VARCHAR);
		   cStmt.registerOutParameter(4,Types.VARCHAR);
		   cStmt.registerOutParameter(5,Types.VARCHAR);
		   cStmt.registerOutParameter(6,Types.VARCHAR);
		   cStmt.registerOutParameter(7,Types.VARCHAR);
		   cStmt.execute();
		   bez= new Bezero(id, cStmt.getString(2), cStmt.getString(3), cStmt.getString(4),cStmt.getString(5), cStmt.getString(6), cStmt.getString(7));
		   
			   }	catch (SQLException e) {
				   System.out.println("Errorea bezeroa lortzerakoan: "+e);
			   }
	   return bez;
   }
   
   public void updateBezero(Bezero bezero) {
	    String sql = "UPDATE BEZERO SET IZENA=?, ABIZENA=?, HELBIDEA =?,EMAILA =?,ERABILTZAILE=?, PASAHITZA =? WHERE ID = ?";
	        try {
			    Connection conn = konexioa();
			    PreparedStatement pS = conn.prepareStatement(sql);
			    pS.setString(1, bezero.getIzena());
			    pS.setString(2, bezero.getAbizena());
			    pS.setString(3, bezero.getHelbidea());
			    pS.setString(4, bezero.getEmaila());
			    pS.setString(5, bezero.getErabiltzaile());
			    pS.setString(6, bezero.getPasahitza());
			    pS.setInt(7, bezero.getId());
			    pS.executeUpdate();
			    System.out.println("Bezero " + bezero.getIzena() + " eguneratu da.");
			} catch (SQLException e) {
			    System.out.println("Errorea bezeroa eguneratzean: " + e);
			    e.printStackTrace();
			}
	}
   
   public void cancelEskari(int idEskari) {
	    String sql = "UPDATE ESKARI SET ID_EGOERA = 2 WHERE ID = ?";
	    try {
	        Connection conn = konexioa();
	        PreparedStatement pS = conn.prepareStatement(sql);
	        pS.setInt(1, idEskari);
	        pS.executeUpdate();
	        System.out.println("Eskari " + idEskari + " kanzelatu da.");
	    } catch (SQLException e) {
	    	JOptionPane.showMessageDialog(null, "Eskaria kanzelatuta dago");
	    }   
	}
   
  public boolean stockAldatu(int produktuId, int kopurua) {
	    String sql = "{CALL STOCKA(?, ?)}";
	    try {
	        Connection conn = konexioa();
	        PreparedStatement pS = conn.prepareStatement(sql);
	        pS.setInt(1, produktuId);
	        pS.setInt(2, kopurua);
	        pS.executeUpdate();
	        System.out.println(".");
	    } catch (SQLException e) {
	    	System.out.println("Errorea:" + e.getMessage());
	    	return false;
	    }
		return true;
  }
   
   public void insertEskariLerr(EskariLerr eskari) {
	    String callSQL = "{CALL INSERT_ESKARI_LERRO(?, ?, ?, ?)}";

	    try (Connection conn = konexioa();
	         CallableStatement cS = conn.prepareCall(callSQL)) {
	        
	        cS.setInt(1, eskari.getIdBezero());
	        cS.setInt(2, eskari.getIdProduktu());
	        cS.setInt(3, eskari.getKopurua());
	        cS.setDouble(4, eskari.getSalneurria());
	        
	        cS.executeUpdate();
	        System.out.println("Eskaria ondo sartu da.");
	    } catch (SQLException e) {
	        System.out.println("Errorea eskaria sartzerakoan: " + e.getMessage());
	    }
	}
   
   public Bezero bezeroInformazioa(int idBezero) {
	   Bezero bezero = null;
	    String sql = "SELECT ID, IZENA,ABIZENA, HELBIDEA,EMAILA,ERABILTZAILE,PASAHITZA FROM BEZERO WHERE ID = ?";
	    try (Connection conn = konexioa();
	         PreparedStatement pS = conn.prepareStatement(sql)) {
	        pS.setInt(1, idBezero);
	        ResultSet rs = pS.executeQuery();
	        while (rs.next()) {
	            int id = rs.getInt("ID");
	            String izena = rs.getString("IZENA");
	            String abizena = rs.getString("ABIZENA");
	            String helbidea = rs.getString("HELBIDEA");
	            String emaila = rs.getString("EMAILA");
	            String erabiltzaile = rs.getString("ERABILTZAILE");
	            String pasahitza = rs.getString("PASAHITZA");
	         
	            bezero = new Bezero(id, izena,abizena,helbidea,emaila,erabiltzaile,pasahitza);
	        }
	    } catch (SQLException e) {
	        System.out.println("Errorea bezeroa kontsultatzean: " + e);
	    }
	    return bezero;
	}
   
   public int bezeroIdLortu(String izena, String pass) {
	   int bezeroID=0;
	   String sql = "{CALL BEZERO_ID_LORTU(?,?,?)}";
	   try (Connection conn = konexioa();
			   CallableStatement cStmt=conn.prepareCall(sql)){
		   cStmt.setString(1, izena);
		   cStmt.setString(2, pass);
		   cStmt.registerOutParameter(3, Types.INTEGER);
		   cStmt.execute();
		   bezeroID=cStmt.getInt(3);
		   }	catch (SQLException e) {
			   System.out.println("Errorea id-a lortzerakoan: "+e);
		   }
	   return bezeroID;
   }
   
   public int insertBezero(Bezero bezero) {
	     String updateSQL = "INSERT INTO BEZERO(IZENA, ABIZENA,HELBIDEA, EMAILA, ERABILTZAILE, PASAHITZA) VALUES(?, ?, ?, ?, ?, ?)";

	    try {
	        Connection conn = konexioa();
	        PreparedStatement pS = conn.prepareStatement(updateSQL);
	     
	        pS.setString(1, bezero.getIzena());
	        pS.setString(2, bezero.getAbizena());
	        pS.setString(3, bezero.getHelbidea());
	        pS.setString(4, bezero.getEmaila());
	        pS.setString(5, bezero.getErabiltzaile());
	        pS.setString(6, bezero.getPasahitza());
	        pS.executeUpdate();
	        
	    } catch (SQLException e) {
	        System.out.println("Errorea bezeroa gehitzeko: " + e);
	    }
	    return bezeroIdLortu(bezero.getErabiltzaile(), bezero.getPasahitza());
	}
  
   public int insertKokaleku(Kokaleku kokaleku) {
	     String updateSQL = "INSERT INTO KOKALEKU(HELBIDEA, POSTAKODEA, UDALERRIA, PROBINTZIA, ID_HERRIALDE) VALUES(?, ?, ?, ?, ?)";
	     int idBerri=0;
	    try {
	        Connection conn = konexioa();
	        PreparedStatement pS = conn.prepareStatement(updateSQL);
	     
	        pS.setString(1, kokaleku.getHelbide());
	        pS.setString(2, kokaleku.getPostakodea());
	        pS.setString(3, kokaleku.getProbintzia());
	        pS.setString(4, kokaleku.getUdalerri());
	        pS.setString(5, kokaleku.getIdHerrialde());
	        pS.executeUpdate();
	        
	        Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery("SELECT MAX(ID) AS KODE FROM KOKALEKU");
	        if (rs.next()) {
	            idBerri = rs.getInt("KODE");
	        }
	    } catch (SQLException e) {
	        System.out.println("Errorea kokalekua gehitzeko: " + e);
	    }
	    return idBerri;
	}
   
   public boolean insertInbentario(Inbentario inbentario) {
	     String updateSQL = "INSERT INTO INBENTARIO(ID_PRODUKTU, ID_BILTEGI, KOPURUA) VALUES(?, ?, ?)";
	     boolean bool=true;
	    try {
	        Connection conn = konexioa();
	        PreparedStatement pS = conn.prepareStatement(updateSQL);
	        pS.setInt(1, inbentario.getIdProduktu());
	        pS.setInt(2, inbentario.getIdBiltegi());
	        pS.setInt(3, inbentario.getKopuru());
	        pS.executeUpdate();
	        
	    } catch (SQLException e) {
	        System.out.println("Errorea inbentarioa gehitzeko: " + e);
	        bool=false;
	    }
	    return bool;
	}
   
  public boolean deleteBezero(int id) {
	   boolean bool=false;
	   String sql = "{CALL BEZERO_EZABATU(?)}";
		   try (Connection conn = konexioa();
				   CallableStatement cStmt = conn.prepareCall(sql)) {
			   cStmt.setInt(1, id);
			   cStmt.execute();
			   bool=true;
		   }catch (SQLException e) {
			   System.out.println("Errorea bezeroa ezabatzerakoan: "+e);
			   }
		   return bool;
	}
  
  public boolean deleteProduktu(int id) {
	   boolean bool=true;
	   String sql = "{CALL PRODUKTU_EZABATU(?)}";
		   try (Connection conn = konexioa();
				   CallableStatement cStmt = conn.prepareCall(sql)) {
			   cStmt.setInt(1, id);
			   cStmt.execute();
			   bool=true;
		   }catch (SQLException e) {
			   System.out.println("Errorea produktua ezabatzerakoan: "+e);
			   bool=false;
			   }
		   return bool;
	}
   
  public boolean deleteEskari(int id) {
	   boolean bool=false;
	   String sql = "{CALL ESKARI_EZABATU(?)}";
		   try (Connection conn = konexioa();
				   CallableStatement cStmt = conn.prepareCall(sql)) {
			   cStmt.setInt(1, id);
			   cStmt.execute();
			   bool=true;
		   }catch (SQLException e) {
			   System.out.println("Errorea eskaria ezabatzerakoan: "+e);
			   }
		   return bool;
	}
  
  
  
   public int langileIdLortu(String izena, String pass) {
	   int langileID=0;
	   String sql = "{CALL LANGILE_ID_LORTU(?,?,?)}";
	   try (Connection conn = konexioa();
			   CallableStatement cStmt=conn.prepareCall(sql)){
		   cStmt.setString(1, izena);
		   cStmt.setString(2, pass);
		   cStmt.registerOutParameter(3, Types.INTEGER);
		   cStmt.execute();
		   langileID=cStmt.getInt(3);
		   }	catch (SQLException e) {
			   System.out.println("Errorea id-a lortzerakoan: "+e);
		   }
	   return langileID;
   }
   
   public String langileIzenLortu(int id) {
	   String izenLangile=null;
	   String sql="{CALL LANGILE_IZEN_LORTU(?,?)}";
	   try (Connection conn = konexioa();
			   CallableStatement cStmt = conn.prepareCall(sql)) {
		   cStmt.setInt(1, id);
		   cStmt.registerOutParameter(2,Types.VARCHAR);
		   cStmt.execute();
		   izenLangile= cStmt.getString(2);
			   }	catch (SQLException e) {
				   System.out.println("Errorea langilea lortzerakoan: "+e);
			   }
			   return izenLangile;
   }
   
   public int insertBiltegi(Biltegi bil) {
	    String updateSQL = "INSERT INTO BILTEGI(IZENA, ID_KOKALEKU) VALUES(?, ?)";
	    int idBerri = 0;
	    try {
	        Connection conn = konexioa();
	        PreparedStatement pS = conn.prepareStatement(updateSQL);

	        pS.setString(1, bil.getIzena());
	        pS.setInt(2, bil.getIdKokaleku());
	        pS.executeUpdate();

	        Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery("SELECT MAX(ID) AS KODE FROM BILTEGI");
	        if (rs.next()) {
	            idBerri = rs.getInt("KODE");
	        }

	    } catch (SQLException e) {
	        System.out.println("Errorea Biltegia gehitzeko: " + e);
	    }
	    return idBerri;
	}

   public int insertEskari(Eskari esk) {
	    String updateSQL = "INSERT INTO ESKARI(ID_BEZERO, ID_EGOERA, ID_SALTZAILE, ESKAERA_DATA) VALUES(?, ?, ?, ?)";
	    int idBerri = 0;
	    try {
	        Connection conn = konexioa();
	        PreparedStatement pS = conn.prepareStatement(updateSQL);

	        pS.setInt(1, esk.getIdBezero());
	        pS.setInt(2, esk.getIdEgoera());
	        pS.setInt(3, esk.getIdSaltzaile());
	        java.sql.Date sqlDate = new java.sql.Date(esk.getEskaeraData().getTime());
	        pS.setDate(4, sqlDate);
	        pS.executeUpdate();

	        Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery("SELECT MAX(ID) AS KODE FROM ESKARI");
	        if (rs.next()) { 
	            idBerri = rs.getInt("KODE");
	        }

	    } catch (SQLException e) {
	        System.out.println("Errorea eskaria gehitzeko: " + e);
	    }
	    return idBerri;
	}
      
   public Boolean insertHerrialde(Herrialde her) {
	    String updateSQL = "INSERT INTO HERRIALDE(ID, IZENA, ID_KONTINENTE) VALUES(?, ?, ?)";
	    boolean b=true;
	    try {
	        Connection conn = konexioa();
	        PreparedStatement pS = conn.prepareStatement(updateSQL);

	        pS.setString(1, her.getId());
	        pS.setString(2, her.getIzena());
	        pS.setInt(3, her.getIdKontinente());
	        pS.executeUpdate();

	    } catch (SQLException e) {
	        b=false;
	    }
	    return b;
	}
   
   
   
   public List<String> eskariLerroLortuArray() {
	    List<String> idEskLerro= new ArrayList<>();
	    String sql = "SELECT * FROM ESKARI_LERRO";
	    try (Connection conn = konexioa();
	        PreparedStatement pS = conn.prepareStatement(sql)) {
	        ResultSet rs = pS.executeQuery();
	        while (rs.next()) {
	            idEskLerro.add("Eskari_ID: "+rs.getString(1)+" Lerro_ID: "+rs.getString(2));
	        }
	    } catch (SQLException e) {
	        System.out.println("Errorea eskari lerroa kontsultatzean: " + e);
	    }
	    return idEskLerro;
	}
   
   public Eskari_Lerro eskariLerroLortu(int id_lerr, int id_esk) {
	   Eskari_Lerro eskL=null;
	   String sql = "{CALL ESKARI_LERRO_LORTU(?,?,?,?,?)}";
	   try (Connection conn = konexioa();
			   CallableStatement cStmt = conn.prepareCall(sql)) {
		   cStmt.setInt(1, id_lerr);
		   cStmt.setInt(2, id_esk);
		   cStmt.registerOutParameter(3,Types.INTEGER);
		   cStmt.registerOutParameter(4,Types.INTEGER);
		   cStmt.registerOutParameter(5,Types.FLOAT);
		   cStmt.execute();
		   eskL= new Eskari_Lerro(id_esk,id_lerr, cStmt.getInt(3), cStmt.getInt(4),cStmt.getDouble(5));
			   }	catch (SQLException e) {
				   System.out.println("Errorea eskari lerro lortzerakoan: "+e);
			   }
	   System.out.println(eskL.toString());
	   return eskL;
   }
   
      public boolean deleteEskLerro(int id_eskari, int id_lerro) {
	   boolean bool=false;
	   String sql = "DELETE FROM ESKARI_LERRO WHERE ID_ESKARI=? AND ID_LERRO=?";
		   try (Connection conn = konexioa();
			   CallableStatement cStmt = conn.prepareCall(sql)) {
			   cStmt.setInt(1, id_eskari);
			   cStmt.setInt(2, id_lerro);
			   cStmt.execute();
			   bool=true;
		   }catch (SQLException e) {
			   System.out.println("Errorea eskari lerroa ezabatzerakoan: "+e);
			   }
		   return bool;
	}
   
      public boolean deleteInbentario(int id_produktu, int id_biltegi) {
   	   boolean bool=false;
   	   String sql = "DELETE FROM INBENTARIO WHERE ID_PRODUKTU=? AND ID_BILTEGI=?";
   		   try (Connection conn = konexioa();
   			   CallableStatement cStmt = conn.prepareCall(sql)) {
   			   cStmt.setInt(1, id_produktu);
   			   cStmt.setInt(2, id_biltegi);
   			   cStmt.execute();
   			   bool=true;
   		   }catch (SQLException e) {
   			   System.out.println("Errorea inbentario ezabatzerakoan: "+e);
   			   }
   		   return bool;
   	}
      
   public void eskariLerroEguneratu(Eskari_Lerro eL) {
	   String updateSQL = "UPDATE ESKARI_LERRO SET ID_PRODUKTU=?, KOPURUA=?, SALNEURRIA=? WHERE ID_ESKARI=? AND ID_LERRO=?";
	   try {
	        Connection conn = konexioa();
	        PreparedStatement pS = conn.prepareStatement(updateSQL);
	        pS.setInt(1, eL.getIdProduktu());
	        pS.setInt(2,eL.getKopuru());
	        pS.setDouble(3, eL.getSalneurri());
	        pS.setInt(4, eL.getIdEskari());
	        pS.setInt(5, eL.getIdLerro());
	        pS.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println("Errorea eskari lerro eguneratzen: " + e);
	    }
   }

   public boolean herrialdeEguneratu(Herrialde h) {
	   String updateSQL = "UPDATE HERRIALDE SET IZENA=?, ID_KONTINENTE=? WHERE ID=?";
	   boolean b=true;
	   try {
	        Connection conn = konexioa();
	        PreparedStatement pS = conn.prepareStatement(updateSQL);
	        pS.setString(1, h.getIzena());
	        pS.setInt(2,h.getIdKontinente());
	        pS.setString(3, h.getId());
	        pS.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println("Errorea herrialdea eguneratzen: " + e);
	        b=false;
	    }
	   return b;
   }
   
   public List<String> herrialdeIdLortuArray() {
	    List<String> izenHerrialde= new ArrayList<>();
	    String sql = "SELECT ID FROM HERRIALDE";
	    try (Connection conn = konexioa();
	        PreparedStatement pS = conn.prepareStatement(sql)) {
	        ResultSet rs = pS.executeQuery();
	        while (rs.next()) {
	            izenHerrialde.add(rs.getString("ID"));
	        }
	    } catch (SQLException e) {
	        System.out.println("Errorea herrialdearen izenak kontsultatzean: " + e);
	    }
	    return izenHerrialde;
	}
   
   
   
   public boolean deleteHerrialde(String id) {
	    boolean bool = false;
	    String sql = "{CALL HERRIALDE_EZABATU(?)}";
	    try (Connection conn = konexioa();
	    	CallableStatement cStmt = conn.prepareCall(sql)) {
	        cStmt.setString(1, id);
	        cStmt.execute();
	        } catch (SQLException e) {
	        System.out.println("Errorea herrialdea ezabatzerakoan: " + e);
	    }
	    return bool;
	}
}
