package erronka3;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;


/**
 * 
 */
public class BDBezero_Telefono {
	private ArrayList<Bezero_Telefono> telefonoLista;

	public BDBezero_Telefono() {
		telefonoLista=new ArrayList<Bezero_Telefono>();
	}

	public void telefonoKargatu(Bezero_Telefono bt) {
		telefonoLista.add(bt);
	}

	@Override
	public String toString() {
		return "BDBezero_Telefono [telefonoLista=" + telefonoLista + "]";
	}	
	
	public static void main(String args[]) {
		lehioAgertu();
	}
	
	public static void lehioAgertu() {
		JFrame mainLehio= new JFrame("Bezero Telefono taula kudeatu");
		JPanel combo=new JPanel(new FlowLayout());
		JLabel elementuIzen=new JLabel("Telefonoak: ");
		
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
		List<String> bezIdArray=conn.telefonoBezeroIdLortuArray();
		for (int i = 0; i < bezIdArray.size(); i++) {
            elementuCombo.addItem(bezIdArray.get(i));
        }
		
		JPanel botoiak= new JPanel(new FlowLayout());
		JButton ezabatu=new JButton("Elementu ezabatu");
		JButton eguneratu=new JButton("Elementu eguneratu");
		botoiak.add(ezabatu);
		botoiak.add(eguneratu);
		
		ezabatu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean ezabaketa=elementuEzabatu(elementuCombo.getSelectedItem());
				if (ezabaketa) {
					String selectedItem = (String) elementuCombo.getSelectedItem();
		            int selectedIndex = elementuCombo.getSelectedIndex();
		            String[] parteak = selectedItem.split(" ");
		            parteak[3] = "NULL";
		            String modifiedItem = String.join(" ", parteak);
		            elementuCombo.removeItemAt(selectedIndex);
		            elementuCombo.insertItemAt(modifiedItem, selectedIndex);
		            elementuCombo.setSelectedIndex(selectedIndex);
				}
			}
		});
		
		eguneratu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String str=(String) elementuCombo.getSelectedItem();
				String[] tarteak= str.split(" ");
				Bezero_Telefono bt= new Bezero_Telefono(1, Integer.parseInt(tarteak[1]),tarteak[3]);
				elementuEguneratu(bt, mainLehio,elementuCombo);
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
			String[] parteak= str.split(" ");
			conn.deleteTelefono(Integer.parseInt(parteak[1]));
			System.out.println("Elementua ezabatu da");
		}
		return bool;
	}
	
	private static String elementuEguneratu(Bezero_Telefono bt, JFrame lehio,JComboBox<String> combo) {
		Konexioa conn=new Konexioa();
		
		JDialog egunDialog= new JDialog(lehio, "Bezero telefono eguneratu");
		JPanel top= new JPanel();
		JPanel datuak= new JPanel(new GridLayout(2,2));
		
		JLabel izenLabel=new JLabel("Bezeroaren ID-a: ");
		JTextField idText= new JTextField(10);
        idText.setText(String.valueOf(bt.getIdBezero()));
        idText.setEnabled(false);
		datuak.add(izenLabel);
		datuak.add(idText);
		
		JLabel zenbakiLabel=new JLabel("Telefono Zenbakia: ");
		JTextField zenbakiText= new JTextField(10);
		zenbakiText.setText(bt.getZenbakia());
		datuak.add(zenbakiLabel);
		datuak.add(zenbakiText);
		
		
		JButton egunBotoi= new JButton("Bezero eguneratu");
		egunBotoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Bezero_Telefono bT=new Bezero_Telefono(1, Integer.parseInt(idText.getText()), zenbakiText.getText());
				conn.telefonoEguneratu(bT, bt.getZenbakia());
				egunDialog.dispose();
				DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) combo.getModel();
	            int index = combo.getSelectedIndex();
	            model.removeElementAt(index);
	            model.insertElementAt("Bezero_Id: "+ idText.getText()+ " Zenbakia: " +zenbakiText.getText(), index);
	            combo.setSelectedIndex(index);
			}
		});
		
		JPanel bottom=new JPanel();
		bottom.add(egunBotoi);
		top.add(datuak);
		egunDialog.add(top);
		egunDialog.add(bottom, BorderLayout.SOUTH);
		egunDialog.setVisible(true);
		egunDialog.pack();
		return zenbakiText.getText();
	}
}