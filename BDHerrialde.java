package erronka3;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class BDHerrialde {
	private ArrayList<Herrialde> listaHerrialde;
	
	public BDHerrialde(){
		listaHerrialde= new ArrayList<Herrialde>();
	}

	public void herrialdeKargatu(Herrialde h) {
		listaHerrialde.add(h);		
	}

	@Override
	public String toString() {
		return "listaHerrialde: " + listaHerrialde;
	}
	
	public static void main(String args[]) {
		lehioAgertu();
	}
	
	public static void lehioAgertu() {
		JFrame mainLehio= new JFrame("Herrialde taula kudeatu");
		JPanel combo=new JPanel(new FlowLayout());
		JLabel elementuIzen=new JLabel("Herrialdearen izena: ");
		
		JMenu menu = new JMenu ("Aukerak");
		JMenuItem bueltatu = new JMenuItem("Bueltatu");
        menu.add(bueltatu);
        JMenuItem saioItxi = new JMenuItem("Saioa itxi");
        menu.add(saioItxi);
        JMenuBar jmb = new JMenuBar ();
        jmb.add(menu);
        mainLehio.setJMenuBar(jmb);
        bueltatu.addActionListener(e -> {
       	   mainLehio.dispose();
       	   SaltzaileMain.main_agertu();
          });
        saioItxi.addActionListener(e -> {
      	   mainLehio.dispose();
      	   Main.mainAgertu();
         });
        
		Konexioa conn=new Konexioa();
		JComboBox<String> elementuCombo= new JComboBox<>();
		List<String> elementuArray=conn.herrialdeIdLortuArray();
		for (int i = 0; i < elementuArray.size(); i++) {
            elementuCombo.addItem("id: "+elementuArray.get(i)+" izena: "+conn.herrialdeLortu(elementuArray.get(i)).getIzena());
        }
		JPanel botoiak= new JPanel(new FlowLayout());
		JButton ezabatu=new JButton("Elementu ezabatu");
		JButton gehitu=new JButton("Elementu gehitu");
		botoiak.add(ezabatu);
		botoiak.add(gehitu);
		
		ezabatu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean ezabaketa=elementuEzabatu(elementuCombo.getSelectedItem());
				if (ezabaketa) {
					Object selectedIndex = elementuCombo.getSelectedItem();
					elementuCombo.removeItem(selectedIndex);
				}
			}
		});
		
		gehitu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				elementuGehitu(mainLehio,elementuCombo);
				
			}
		});
		
		JPanel mainPanel= new JPanel(new GridLayout(2,0));
		
		combo.add(elementuIzen);
		combo.add(elementuCombo);
		mainPanel.add(combo);
		mainPanel.add(botoiak);
		mainLehio.add(mainPanel);
		mainLehio.pack();
		mainLehio.setVisible(true);
	}
	
	private static boolean elementuEzabatu(Object elementu) {
		boolean bool= false;
		int dialogButton=JOptionPane.showConfirmDialog(null, "Elementu ezabatu nahi duzu?", "Ezabaketa konfirmatu", JOptionPane.YES_NO_OPTION);
		if (dialogButton==JOptionPane.YES_OPTION){
			bool=true;
			Konexioa conn= new Konexioa();
			String str= (String) elementu;
			String[] parteak=str.split(" ");
			conn.deleteHerrialde(parteak[1]);
			System.out.println("Elementua ezabatu da");
		}
		return bool;
	}
	
	private static JComboBox<String> elementuGehitu(JFrame lehio, JComboBox<String> izenCombo) {
		Konexioa conn=new Konexioa();

		JDialog gehituLehio=new JDialog(lehio,"Herrialde gehitu");
		gehituLehio.setVisible(true);
		JPanel top= new JPanel();
		JPanel datuak= new JPanel(new GridLayout(3,2));
		
		JLabel idHerriLabel=new JLabel("Herrialdearen id-a: ");
		JTextField idHerriText= new JTextField(10);
		datuak.add(idHerriLabel);
		datuak.add(idHerriText);
		
		JLabel herriIzenLabel=new JLabel("Herrialdearen izena: ");
		JTextField herriIzenText= new JTextField(10);
		datuak.add(herriIzenLabel);
		datuak.add(herriIzenText);
		
		JLabel idKontinenteLabel=new JLabel("Kontinentearen id-a: ");
		JComboBox<String> comboIdKontinente= new JComboBox<String>();
		List<String> idKontinenteLista= conn.kontinenteIDLortuArray();
		for (int i = 0; i < idKontinenteLista.size(); i++) {
            comboIdKontinente.addItem(idKontinenteLista.get(i));
        }
		datuak.add(idKontinenteLabel);
		datuak.add(comboIdKontinente);
		
		JPanel bottom= new JPanel();
		JButton gehituBotoi=new JButton("Herialde gehitu");
		gehituBotoi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Herrialde her=new Herrialde(idHerriText.getText(), herriIzenText.getText(), Integer.parseInt((String)comboIdKontinente.getSelectedItem()));
				boolean sartuta=conn.insertHerrialde(her);
				if (sartuta) {
					izenCombo.addItem("id: "+String.valueOf(her.getId())+" izena: "+her.getIzena());
					gehituLehio.dispose();
				}
				else {
					int dialogButton=JOptionPane.showConfirmDialog(null, "Sartutako ID-a existitzen da, aktualizatu nahi duzu ID hori daukan herrialdea?", "ID errepikatuta", JOptionPane.YES_NO_OPTION);
					if (dialogButton==JOptionPane.YES_OPTION){
						conn.herrialdeEguneratu(her);
						List<String> elementuArray=conn.herrialdeIdLortuArray();
						izenCombo.removeAllItems();
						for (int i = 0; i < elementuArray.size(); i++) {
				            izenCombo.addItem("id: "+elementuArray.get(i)+" izena: "+conn.herrialdeLortu(elementuArray.get(i)).getIzena());
				        }
						gehituLehio.dispose();
					}
				}
			}
		});
		
		top.add(datuak);
		bottom.add(gehituBotoi);
		gehituLehio.add(top);
		gehituLehio.add(bottom, BorderLayout.SOUTH);
		gehituLehio.pack();
		return izenCombo;
	}
}
