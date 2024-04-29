package erronka3;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class SaltzaileMain {

	public static void main(String[] args) {
        main_agertu();
    }
	
	public static void main_agertu() {
        JFrame mainFrame= new JFrame("Langilearen Interfazea");

		JLabel ongi_etorri = new JLabel("Ongi Etorri! \n");
        ongi_etorri.setFont(new Font("SansSerif", Font.BOLD, 40));
        ongi_etorri.setHorizontalAlignment(JLabel.CENTER);
        
        JComboBox<String> comboB= new JComboBox<String>();
        comboB.addItem("Bezeroak"); comboB.addItem("Bezero Telefonoak");
        comboB.addItem("Biltegiak"); comboB.addItem("Eskariak");
        comboB.addItem("Eskari Lerro"); comboB.addItem("Herrialdeak"); 
        comboB.addItem("Inbentarioak"); comboB.addItem("Kokalekuak");
        comboB.addItem("Produktuak");
        
        JButton aukeratu=new JButton ("Taula aukeratu");
        aukeratu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switch ((String) comboB.getSelectedItem()){
					case "Bezeroak": {
						BDBezero.lehioAgertu();
						mainFrame.dispose();
						break;
					}
					case "Bezero Telefonoak": {
						BDBezero_Telefono.lehioAgertu();
						mainFrame.dispose();
						break;
					}
					case "Biltegiak": {
						BDBiltegi.lehioAgertu();
						mainFrame.dispose();
						break;
					}
					case "Eskari Lerro": {
						BDEskari_Lerro.lehioAgertu();
						mainFrame.dispose();
						break;
					}
					case "Eskariak": {
						BDEskari.lehioAgertu();
						mainFrame.dispose();
						break;
					}
					case "Herrialdeak": {
						BDHerrialde.lehioAgertu();
						mainFrame.dispose();
						break;
					}
					case "Inbentarioak": {
						BDInbentario.lehioAgertu();
						mainFrame.dispose();
						break;
					}
					case "Kokalekuak": {
						BDKokaleku.lehioAgertu();
						mainFrame.dispose();
						break;
					}
					case "Produktuak": {
						BDProduktu.lehioAgertu();
						mainFrame.dispose();
						break;
					}
				default:
					throw new IllegalArgumentException("Unexpected value: " + comboB.getSelectedItem());
				}
			}
		});
        
        JPanel taulaAukeratu = new JPanel(new GridLayout (2,0));
        taulaAukeratu.add(comboB);
        taulaAukeratu.add(aukeratu);
        
        mainFrame.add(taulaAukeratu, BorderLayout.EAST);
        mainFrame.add(ongi_etorri, BorderLayout.WEST);
        
        JMenu menu = new JMenu ("Aukerak");
		JMenuItem saioItxi = new JMenuItem("Saioa Itxi");
        menu.add(saioItxi);
        saioItxi.addActionListener(e -> {
       	   mainFrame.dispose();
       	   Main.mainAgertu();
          });
        JMenuBar jmb = new JMenuBar ();
        jmb.add(menu);
        mainFrame.setJMenuBar(jmb);
        
        mainFrame.setVisible(true);
        mainFrame.pack();
	}
}
