package erronka3;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BezeroAldatu {

	public static void main(String[] args) {
		main();

	}
	public static void main(){
		
		  Konexioa konexioa = new Konexioa();
		
		  JPanel panel = new JPanel(new GridLayout(0, 2)); 
          
          JPanel northPanel = new JPanel();
          
          JMenuBar menuBar = new JMenuBar();
                   
          JMenuItem irtenMenuItem = new JMenuItem("Irten");
          
          menuBar.add(irtenMenuItem);
          
          northPanel.add(menuBar);
                         
          JPanel southPanel = new JPanel();
          
          JButton aldatu = new JButton("aldatu");
          
          southPanel.add(aldatu);
          
          JTextField izenaTF = new JTextField(20);
          JTextField abizenaTF = new JTextField(20);
          JTextField helbideaTF = new JTextField(20);
          JTextField emailaTF = new JTextField(20);
          JTextField erabiltzaileTF = new JTextField(20);
          JTextField pasahitzaTF = new JTextField(20);

          JLabel izenaJL = new JLabel("Izena:");
          JLabel abizenaJL = new JLabel("Abizena:");
          JLabel helbideaJL = new JLabel("Helbidea:");
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
          
          Bezero bezero = konexioa.bezeroInformazioa(Main.getId());
          
          izenaTF.setText(bezero.getIzena());
          abizenaTF.setText(bezero.getAbizena());
          helbideaTF.setText(bezero.getHelbidea());
          emailaTF.setText(bezero.getEmaila());
          erabiltzaileTF.setText(bezero.getErabiltzaile());
          pasahitzaTF.setText(bezero.getPasahitza());
          
          irtenMenuItem.addActionListener(ebentua -> {
        	  BezeroMain.main_agertu();
        	  bgFrame.dispose();
          });
       
          aldatu.addActionListener(ebentua -> {
              JFrame frame = new JFrame("Aldaketa");
              int erantzuna = JOptionPane.showConfirmDialog(frame, "Datuak aldatu nahi duzu?");
              if(erantzuna == JOptionPane.YES_OPTION) {
            	  int id = Main.getId();
                  String izena = izenaTF.getText(); 
                  String abizena = abizenaTF.getText(); 
                  String helbidea = helbideaTF.getText(); 
                  String emaila = emailaTF.getText(); 
                  String erabiltzailea = erabiltzaileTF.getText(); 
                  String pass = pasahitzaTF.getText(); 

                  if (izena.isEmpty() || abizena.isEmpty() || emaila.isEmpty() || erabiltzailea.isEmpty() || pass.isEmpty() || helbidea.isEmpty()) {
                      JOptionPane.showMessageDialog(null, "Derrigorrezko informazioa falta da.");
                  } else {
                      Bezero newbezero = new Bezero(id, izena,abizena,helbidea,emaila,erabiltzailea,pass);
                      bezero.setIzena(izena);
                      bezero.setAbizena(abizena);
                      bezero.setHelbidea(helbidea);
                      bezero.setEmaila(emaila);
                      bezero.setErabiltzaile(erabiltzailea);
                      bezero.setPasahitza(pass);
                      bezero.setId(id); 
                      
                    
                      konexioa.updateBezero(newbezero);
                      System.out.println(newbezero.getId());
                      System.out.println(newbezero.getAbizena());
                      System.out.println(newbezero.getHelbidea());
                      System.out.println(newbezero.getEmaila());
                      System.out.println(newbezero.getErabiltzaile());
                      System.out.println(newbezero.getPasahitza());

                      JOptionPane.showMessageDialog(null, konexioa.BezeroIzen_Lortu(Main.getId()) + " informazioa aldatu da");
                      frame.dispose();
                      bgFrame.dispose();
                      BezeroMain.main_agertu();
                  }
              }      
          });

	}
}
