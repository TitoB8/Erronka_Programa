package erronka3;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.*;

public class Main {
	static int id;
    public static void main(String[] args) {
    	mainAgertu();
    }
  
    public static void mainAgertu() {

    	   JFrame mainFrame = new JFrame("Saio ireki");
    	 
           JPanel mainpanel = new JPanel(new BorderLayout());
           JLabel erabiltzaile = new JLabel("Erabiltzaile:");
           JLabel pasahitza = new JLabel("Pasahitza:");
           JTextField erabiltzaileIpini = new JTextField(10);
           
           JPasswordField pasahitzaIpini = new JPasswordField(10);
           pasahitzaIpini.setEchoChar('*');


           JPanel northpanel = new JPanel(new BorderLayout());

           JPanel uc = new JPanel(new GridLayout(2, 2));

           northpanel.add(uc, BorderLayout.BEFORE_LINE_BEGINS);

           JComboBox<String> jCombo;
           jCombo = new JComboBox<>();
           jCombo.setPreferredSize(new Dimension(1, 26));
           jCombo.addItem("Bezero gehitu");
           jCombo.addItem("Irten.");
           northpanel.add(jCombo, BorderLayout.NORTH);

           uc.add(erabiltzaile);
           uc.add(erabiltzaileIpini);
           uc.add(pasahitza);
           uc.add(pasahitzaIpini);

           mainpanel.add(northpanel, BorderLayout.NORTH);

           JPanel southpanel = new JPanel(new BorderLayout());

           mainpanel.add(southpanel, BorderLayout.SOUTH);
           JButton ipini = new JButton("Saioa ireki");

           southpanel.add(ipini, BorderLayout.BEFORE_FIRST_LINE);

           ipini.addActionListener(e -> extracted(mainFrame, erabiltzaileIpini, pasahitzaIpini));

           jCombo.addActionListener(e -> {
               String aukeratukoInformazioa = (String) jCombo.getSelectedItem();
               if (aukeratukoInformazioa.equals("Bezero gehitu")) {


                   JPanel panel = new JPanel(new GridLayout(0, 2)); 
                   
                   JPanel northPanel = new JPanel();
                   
                   JMenuBar menuBar = new JMenuBar();
                            
                   JMenuItem irtenMenuItem = new JMenuItem("Irten");
                   
                   menuBar.add(irtenMenuItem);
                   
                   northPanel.add(menuBar);
                                  
                   JPanel southPanel = new JPanel();
                   
                   JButton sartu = new JButton("Sartu");
                   
                   southPanel.add(sartu);
                   
                   JTextField izenaTF = new JTextField();
                   JTextField abizenaTF = new JTextField();
                   JTextField helbideaTF = new JTextField();
                   JTextField emailaTF = new JTextField();
                   JTextField erabiltzaileTF = new JTextField();
                   JTextField pasahitzaTF = new JTextField();

                   JLabel izenaJL = new JLabel("Izena:");
                   JLabel abizenaJL = new JLabel("Abizena:");
                   JLabel helbideaJL = new JLabel("Helbidea(autazkoa):");
                   JLabel emailaJL = new JLabel("Emaila:");
                   JLabel erabiltzaileJL = new JLabel("Erabiltzailea:");
                   JLabel pasahitzaJL = new JLabel("Pasahitza:");


                   panel.add(izenaJL);
                   panel.add(izenaTF);
                   panel.add(abizenaJL);
                   panel.add(abizenaTF);
                   panel.add(helbideaJL);
                   panel.add(helbideaTF);
                   panel.add(emailaJL);
                   panel.add(emailaTF);
                   panel.add(erabiltzaileJL);
                   panel.add(erabiltzaileTF);
                   panel.add(pasahitzaJL);
                   panel.add(pasahitzaTF);

                   JFrame bgFrame = new JFrame("Bezeroa gehitu");
                   
                   bgFrame.add(panel, BorderLayout.CENTER);
                   bgFrame.add(southPanel, BorderLayout.SOUTH);
                   bgFrame.add(northPanel, BorderLayout.NORTH);
                   bgFrame.setLocationRelativeTo(null);
                   bgFrame.pack();
                   bgFrame.setJMenuBar(menuBar);
                   bgFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                   bgFrame.setVisible(true);
                   mainFrame.dispose();
                   
                   sartu.addActionListener(ebentua -> {
                   	JFrame frame = new JFrame("Konfirmazioa");
                   	int erantzuna = JOptionPane.showConfirmDialog(frame, "Bezero berri bat gehitu nahi duzu?");
                   	if(erantzuna ==JOptionPane.YES_OPTION) {
                   		
                   		Konexioa konexioa = new Konexioa();
                           String izena = izenaTF.getText(); 
                           String abizena = abizenaTF.getText(); 
                           String helbidea = helbideaTF.getText(); 
                           String emaila = emailaTF.getText(); 
                           String erabiltzailea = erabiltzaileTF.getText(); 
                           String pass = pasahitzaTF.getText(); 
                           Bezero newBezero = new Bezero(izena,abizena,helbidea,emaila,erabiltzailea,pass);
                           if (izena.isEmpty() || abizena.isEmpty() || emaila.isEmpty() || erabiltzailea.isEmpty() || pass.isEmpty()) {
                           	JOptionPane.showMessageDialog(null, "Derrigorrezko informazioa falta da.");
                       	}else {
                       		konexioa.insertBezero(newBezero);
                       		JOptionPane.showMessageDialog(null, "Bezero berri bat erregistratu da.");
        	                   	mainAgertu();
        	                   	frame.dispose();
        	                   	bgFrame.dispose();
                       	}
                   	}      
                   });

                   irtenMenuItem.addActionListener(evento -> {
                   	mainAgertu();
                   	bgFrame.dispose();
                   	
                   });
                   
               } else {
                   System.exit(0);
               }
               
           });
           
           mainFrame.add(mainpanel);
           mainFrame.pack();
           mainFrame.setLocationRelativeTo(null);
           mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           mainFrame.setVisible(true);
           
       }

	private static void extracted(JFrame mainFrame, JTextField erabiltzaileIpini, JPasswordField pasahitzaIpini) {
		Konexioa konexioa = new Konexioa();
		String izena = erabiltzaileIpini.getText();
		@SuppressWarnings("deprecation")
		String pass = pasahitzaIpini.getText();
		if (!izena.isEmpty() && !pass.isEmpty()) {
		   	 if (konexioa.langilePasahitzaAurkitu(izena, pass)) {
		            JOptionPane.showMessageDialog(null, "Ongi etorri," +izena+ "!" );
		            SaltzaileMain.main_agertu();
		            mainFrame.dispose();
		   	 } if (konexioa.erabiltzailePasahitzaAurkitu(izena, pass)) {
		   		id = konexioa.BezeroID_Lortu(izena, pass);
		        setId(id);
		         String bezeroIzena = konexioa.BezeroIzen_Lortu(Main.getId());
		       	  JOptionPane.showMessageDialog(null, "Ongi etorri," +bezeroIzena+ "!" );
		       	  BezeroMain.main_agertu();
		       	  mainFrame.dispose();
		       } else if(!konexioa.erabiltzailePasahitzaAurkitu(izena, pass) && !konexioa.langilePasahitzaAurkitu(izena, pass)) {
		           JOptionPane.showMessageDialog(null, "Erabiltzailea edo pasahitza ez da zuzena.");
		       }
		   } else {
		       JOptionPane.showMessageDialog(null, "Mesedez, bete erabiltzailea eta pasahitza.");
		   }
	}
    

	public static int getId() {
		return id;
	}

	public static void setId(int pid) {
		pid = id;
	}
    
}