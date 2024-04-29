package erronka3;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BDKokaleku {
	private ArrayList<Kokaleku> kokalekuLista;

	public BDKokaleku() {
		kokalekuLista=new ArrayList<Kokaleku>();
	}

	public void kokalekuKargatu(Kokaleku k) {
		kokalekuLista.add(k);
	}

	@Override
	public String toString() {
		return "BDKokaleku [kokalekuLista=" + kokalekuLista + "]";
	}	
	
	public static void main(String args[]) {
		lehioAgertu();
	}
	
	public static void lehioAgertu() {
		JFrame mainLehio= new JFrame("Kokaleku taula kudeatu");
		JPanel combo=new JPanel(new FlowLayout());
		JLabel elementuIzen=new JLabel("Kokalekuren izena: ");
		
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
		List<String> elementuArray=conn.kokalekuIdLortuArray();
		for (int i = 0; i < elementuArray.size(); i++) {
            elementuCombo.addItem("id: "+elementuArray.get(i)+" helbidea: "+conn.kokalekuLortu(Integer.valueOf(elementuArray.get(i))).getHelbide());
        }
		JPanel botoiak= new JPanel(new FlowLayout());
		JButton ezabatu=new JButton("Elementu ezabatu");
		JButton eguneratu= new JButton ("Elementu eguneratu");
		JButton gehitu=new JButton("Elementu gehitu");
		botoiak.add(ezabatu);
		botoiak.add(eguneratu);
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
		
		eguneratu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String str=(String) elementuCombo.getSelectedItem();
				String[] tarteak= str.split(" ");
				elementuEguneratu(tarteak[1], mainLehio,elementuCombo);
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
			Konexioa conn= new Konexioa();
			String str= (String) elementu;
			String[] parteak= str.split(" ");
			bool=conn.deleteKokaleku(Integer.parseInt(parteak[1]));
		}
		return bool;
	}
	
	private static String elementuEguneratu(String id, JFrame lehio,JComboBox<String> izenCombo) {
		Konexioa conn=new Konexioa();
		
		JDialog egunDialog= new JDialog(lehio, "Kokaleku eguneratu");
		JPanel top= new JPanel();
		JPanel datuak= new JPanel(new GridLayout(6,2));
		
		JLabel idLabel=new JLabel("Kokalekuaren id-a: ");
		JTextField idText= new JTextField(10);
		idText.setText(id);
		idText.setEnabled(false);
		datuak.add(idLabel);
		datuak.add(idText);
		Kokaleku kok= conn.kokalekuLortu(Integer.parseInt(id));
		
		JLabel helbideLabel=new JLabel("Kokalekuaren helbidea: ");
		JTextField helbideText= new JTextField(10);
		helbideText.setText(kok.getHelbide());
		datuak.add(helbideLabel);
		datuak.add(helbideText);
		
		JLabel postakodeLabel=new JLabel("Kokalekuaren postakodea: ");
		JTextField postakodeText= new JTextField(10);
		postakodeText.setText(kok.getPostakodea());
		datuak.add(postakodeLabel);
		datuak.add(postakodeText);
		
		JLabel udalerriLabel=new JLabel("Kokalekuaren udalerria: ");
		JTextField udalerriText= new JTextField(10);
		udalerriText.setText(kok.getUdalerri());
		datuak.add(udalerriLabel);
		datuak.add(udalerriText);
		
		JLabel probintziaLabel=new JLabel("Kokalekuaren probintzia: ");
		JTextField probintziaText= new JTextField(10);
		probintziaText.setText(kok.getProbintzia());
		datuak.add(probintziaLabel);
		datuak.add(probintziaText);
		
		JLabel herrialdeLabel=new JLabel("Herrialdearen id: ");
		JComboBox<String> herrialdeCombo= new JComboBox<String>();
		List<String> herrialdeIdLista= conn.herrialdeIdLortuArray();
		for (int i = 0; i < herrialdeIdLista.size(); i++) {
            herrialdeCombo.addItem(herrialdeIdLista.get(i));
        }
		herrialdeCombo.setSelectedItem(kok.getIdHerrialde());
		datuak.add(herrialdeLabel);
		datuak.add(herrialdeCombo);
		
		JButton egunBotoi= new JButton("Kokaleku eguneratu");
		egunBotoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Kokaleku k=new Kokaleku(Integer.parseInt(id), helbideText.getText(), postakodeText.getText(), udalerriText.getText(), probintziaText.getText(), (String)herrialdeCombo.getSelectedItem());
				conn.kokalekuEguneratu(k);
				egunDialog.dispose();

				DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) izenCombo.getModel();
	            int index = izenCombo.getSelectedIndex();
	            String izenBerria = helbideText.getText();
	            model.removeElementAt(index);
	            model.insertElementAt("id: "+ idText.getText()+ " izena: " +izenBerria, index);
	            izenCombo.setSelectedIndex(index);
			}
		});
		
		JPanel bottom=new JPanel();
		bottom.add(egunBotoi);
		top.add(datuak);
		egunDialog.add(top);
		egunDialog.add(bottom, BorderLayout.SOUTH);
		egunDialog.setVisible(true);
		egunDialog.pack();
		return helbideText.getText();
	}
	
	private static void elementuGehitu(JFrame lehio, JComboBox<String> izenCombo) {
		Konexioa conn=new Konexioa();

		JDialog gehituLehio=new JDialog(lehio,"Kokaleku gehitu");
		gehituLehio.setVisible(true);
		JPanel top= new JPanel();
		JPanel datuak= new JPanel(new GridLayout(5,2));
		
		JLabel helbideLabel=new JLabel("Kokalekuaren helbidea: ");
		JTextField helbideText= new JTextField(10);
		datuak.add(helbideLabel);
		datuak.add(helbideText);
		
		JLabel postakodeLabel=new JLabel("Kokalekuaren postakodea: ");
		JTextField postakodeText= new JTextField(10);
		datuak.add(postakodeLabel);
		datuak.add(postakodeText);
		
		JLabel probintziaLabel=new JLabel("Kokalekuaren probintzia: ");
		JTextField probintziaText= new JTextField(10);
		datuak.add(probintziaLabel);
		datuak.add(probintziaText);
		
		JLabel udalerriLabel=new JLabel("Kokalekuaren udalerria: ");
		JTextField udalerriText= new JTextField(10);
		datuak.add(udalerriLabel);
		datuak.add(udalerriText);

		JLabel herrialdeLabel=new JLabel("Kokalekuaren herrialde id-a: ");
		JComboBox<String> herrialdeCombo= new JComboBox<String>();
		List<String> herrialdeIdLista= conn.herrialdeIdLortuArray();
		for (int i = 0; i < herrialdeIdLista.size(); i++) {
            herrialdeCombo.addItem(herrialdeIdLista.get(i));
        }
		datuak.add(herrialdeLabel);
		datuak.add(herrialdeCombo);
		
		top.add(datuak);
		
		JPanel bottom= new JPanel();
		JButton gehituBotoi=new JButton("Kokaleku gehitu");
		gehituBotoi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Kokaleku kok= new Kokaleku(0,helbideText.getText(),postakodeText.getText(),probintziaText.getText(),udalerriText.getText(),String.valueOf(herrialdeCombo.getSelectedItem()));
				int idBerri=conn.insertKokaleku(kok);
				gehituLehio.dispose();
				String helbideBerria = helbideText.getText();
				izenCombo.addItem("id: "+ idBerri+ " helbidea: " +helbideBerria);
			}
		});
		
		bottom.add(gehituBotoi);
		gehituLehio.add(top);
		gehituLehio.add(bottom, BorderLayout.SOUTH);
		gehituLehio.pack();
	}
}
