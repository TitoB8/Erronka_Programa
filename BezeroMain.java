package erronka3;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class BezeroMain {
    
    public static void main_agertu() {
    	Konexioa konexioa = new Konexioa();
    	String bezeroIzena = konexioa.BezeroIzen_Lortu(Main.getId());
        JLabel ongi_etorri = new JLabel("Ongi Etorri " + bezeroIzena + "!");
        ongi_etorri.setFont(new Font("Lexend", Font.BOLD, 40));
        ongi_etorri.setHorizontalAlignment(JLabel.CENTER);
        JButton eskariak = new JButton("Eskariak egin");
        JButton aldatu = new JButton("Kontuaren informazioa aldatu");
        JButton irten = new JButton("Saioa-itxi");

        JPanel southPanel = new JPanel(new GridLayout(4, 0));
        southPanel.add(ongi_etorri, BorderLayout.NORTH);
        southPanel.add(irten, BorderLayout.SOUTH);
        southPanel.add(eskariak, BorderLayout.SOUTH);
        southPanel.add(aldatu, BorderLayout.SOUTH);

        JPanel mainPanela = new JPanel(new BorderLayout());
        mainPanela.add(southPanel);

        JFrame mainFrame = new JFrame("Bezeroaren interfazea");
        mainFrame.add(mainPanela);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);

        irten.addActionListener(e -> {
            mainFrame.dispose();
            Main.mainAgertu();
        });
        
        eskariak.addActionListener(e -> {

            List<String> produktuIzenak = konexioa.produktuIzenLortu();

            JComboBox<String> jcombo = new JComboBox<>();
            for (int i = 0; i < produktuIzenak.size(); i++) {
                jcombo.addItem(produktuIzenak.get(i));
            }

            JLabel izenaJL = new JLabel("Izena:");
            JLabel deskribapenaJL = new JLabel("Deskribapena:");
            JLabel salneurriaJL = new JLabel("Salneurria:");

            JTextField izenaTF = new JTextField(20);
            JTextField deskribapenaTF = new JTextField(20);
            JTextField salneurriaTF = new JTextField(20);

            JMenuBar menuBar = new JMenuBar();
            JMenu menu = new JMenu("Menu");
            JMenuItem bueltatuMenuItem = new JMenuItem("Bueltatu");
            JMenuItem itxiMenuItem = new JMenuItem("Saioa itxi");
            menu.add(bueltatuMenuItem);
            menu.add(itxiMenuItem);
            menuBar.add(menu);
                    
            JButton eskariaEgin = new JButton("Sartu saskian");
            
            JButton saskia = new JButton(" Saskia ikusi");

            JPanel botoiPanel = new JPanel(new GridLayout(0, 2));

            botoiPanel.add(eskariaEgin, BorderLayout.SOUTH);
            botoiPanel.add(saskia, BorderLayout.SOUTH);

            JPanel menuBarPanel = new JPanel();
            menuBarPanel.add(menuBar);

            JPanel eskariPanel = new JPanel(new GridLayout(0, 2));
            eskariPanel.add(new JLabel("Produktua:"));
            eskariPanel.add(jcombo);
            eskariPanel.add(izenaJL);
            eskariPanel.add(izenaTF);
            eskariPanel.add(deskribapenaJL);
            eskariPanel.add(deskribapenaTF);
            eskariPanel.add(salneurriaJL);
            eskariPanel.add(salneurriaTF);

            JPanel eskariMainPanel = new JPanel(new BorderLayout());
            eskariMainPanel.add(menuBarPanel, BorderLayout.NORTH);
            eskariMainPanel.add(eskariPanel, BorderLayout.CENTER);
            eskariMainPanel.add(botoiPanel, BorderLayout.SOUTH);

            JFrame eskariFrame = new JFrame("Eskariak");
            eskariFrame.add(eskariMainPanel);
            eskariFrame.pack();
            eskariFrame.setLocationRelativeTo(null);
            eskariFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            eskariFrame.setVisible(true);
            mainFrame.dispose();
            
            produktuJComboBoxKargatu(jcombo, izenaTF,deskribapenaTF,salneurriaTF);

            bueltatuMenuItem.addActionListener(ebentua -> {
                main_agertu();
                eskariFrame.dispose();

            });

            itxiMenuItem.addActionListener(ebentua -> {
                Main.mainAgertu();
                eskariFrame.dispose();

            });
            jcombo.addActionListener(ebentua -> {
                int aukera = jcombo.getSelectedIndex();
                if (aukera >= 0) {
                    String produktuaIzena = (String) jcombo.getSelectedItem();
                    Produktu produktuAukera = konexioa.produktuBezeroLortu(produktuaIzena);
                    if (produktuAukera != null) {
                        String izena = produktuAukera.getIzena();
                        String deskribapena = produktuAukera.getDeskribapena();
                        double salneurria = produktuAukera.getSalneurria();
                        izenaTF.setText(izena);
                        deskribapenaTF.setText(deskribapena);
                        salneurriaTF.setText(String.valueOf(salneurria));
                    }
                }
            });

            saskia.addActionListener(ebentu -> {
            	try {
					Saskiak.eskariakIkusi();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            	eskariFrame.dispose();
            });
            
            eskariaEgin.addActionListener(ebentu -> {
                String scanner = JOptionPane.showInputDialog(null, "Zenbat produktu nahi duzu?");
                if (scanner != null && !scanner.isEmpty()) {
                    try {
                        long kantitateaLong = Long.parseLong(scanner);
                        if (kantitateaLong > Integer.MAX_VALUE) {
                            JOptionPane.showMessageDialog(null, "Zenbakiaren limitea gainditu duzu.");
                        } else {
                            int kantitatea = (int) kantitateaLong;
                            int idProduktu = produktuJComboBoxKargatu(jcombo, izenaTF, deskribapenaTF, salneurriaTF);
                            double salneurria = Double.parseDouble(salneurriaTF.getText());
                            if (salneurria != 0) {
                                if (konexioa.stockAldatu(idProduktu, kantitatea)) {
                                    EskariLerr eskari = new EskariLerr(Main.getId(), idProduktu, kantitatea, salneurria);
                                    konexioa.insertEskariLerr(eskari);
                                    eskariFrame.dispose();
                                    JOptionPane.showMessageDialog(mainFrame, "Zure eskaria egin da");
                                    main_agertu();
                                } else {
                                    JOptionPane.showMessageDialog(null, "Ez dago nahikoa stock-a.");
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Salneurria 0 da!");
                            }
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Mesedez ipini zenbaki bat");
                    }
                }
            });
        });
        
        aldatu.addActionListener(e -> {
        	BezeroAldatu.main();
        	mainFrame.dispose();
        });
    }
    
    public static int produktuJComboBoxKargatu(JComboBox<String> jCombo, JTextField izenaTF, JTextField deskribapenaTF, JTextField salneurriaTF) {

        Konexioa konexioa = new Konexioa();
        int aukera = jCombo.getSelectedIndex();
        int id = 0;
        if (aukera >= 0) {
            String produktuaIzena = (String) jCombo.getSelectedItem();
            Produktu produktuAukera = konexioa.produktuBezeroLortu(produktuaIzena);
            if (produktuAukera != null) {
            	id = produktuAukera.getId();
                String izena = produktuAukera.getIzena();
                String deskribapena = produktuAukera.getDeskribapena();
                double salneurria = produktuAukera.getSalneurria();
                izenaTF.setText(izena);
                deskribapenaTF.setText(deskribapena);
                salneurriaTF.setText(String.valueOf(salneurria));
            }
        }
        return id;
    }
}
