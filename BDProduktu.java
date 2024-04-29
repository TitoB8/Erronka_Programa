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
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

public class BDProduktu {
    private ArrayList<Produktu> produktuLista;

    public BDProduktu() {
        produktuLista = new ArrayList<Produktu>();
    }

    
    public ArrayList<Produktu> getLista() {
		return produktuLista;
	}

	public void setLista(ArrayList<Produktu> lista) {
		this.produktuLista = lista;
	}
	
	public void gehituProduktu(Produktu produktu) {
		produktuLista.add(produktu);
	}
	
	public void kenduProduktu(Produktu produktu) {
		produktuLista.remove(produktu);
	}
    
    public void telefonoKargatu(Produktu p) {
        produktuLista.add(p);
    }

    @Override
	public String toString() {
		return "BDProduktu [produktuLista=" + produktuLista + "]";
    }
	public static void main(String args[]) {
		lehioAgertu();
	}
	
	public static void lehioAgertu() {
		JFrame mainLehio= new JFrame("Produktu taula kudeatu");
		JPanel combo=new JPanel(new FlowLayout());
		JLabel elementuIzen=new JLabel("Produktuaren izena: ");
		
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
		List<String> elementuArray=conn.produktuIdIzenLortuArray();
		for (int i = 0; i < elementuArray.size(); i++) {
	           elementuCombo.addItem(elementuArray.get(i));
	       }
		JPanel botoiak= new JPanel(new FlowLayout());
		JButton ezabatu=new JButton("Elementu ezabatu");
		JButton eguneratu=new JButton("Elementu eguneratu");
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
			bool=true;
			Konexioa conn= new Konexioa();
			String str= (String) elementu;
			String[] parteak=str.split(" ");
			conn.deleteProduktu(Integer.parseInt(parteak[1]));
			System.out.println("Elementua ezabatu da");
		}
		return bool;
	}
	
	private static String elementuEguneratu(String id, JFrame lehio,JComboBox<String> izenCombo) {
		Konexioa conn=new Konexioa();
		
		JDialog egunDialog= new JDialog(lehio, "Produktu eguneratu");
		JPanel top= new JPanel();
		JPanel datuak= new JPanel(new GridLayout(6,2));
		
		JLabel idLabel=new JLabel("Produktuaren id-a: ");
		JTextField idText= new JTextField(10);
		idText.setText(id);
		idText.setEnabled(false);
		datuak.add(idLabel);
		datuak.add(idText);
		Produktu prod= conn.produktuLortu(Integer.parseInt(id));
		
		JLabel izenLabel=new JLabel("Produktuaren izena: ");
		JTextField izenText= new JTextField(10);
		izenText.setText(prod.getIzena());
		datuak.add(izenLabel);
		datuak.add(izenText);
		
		JLabel deskLabel=new JLabel("Produktuaren deskribapena: ");
		JTextField deskText= new JTextField(10);
		deskText.setText(prod.getIzena());
		datuak.add(deskLabel);
		datuak.add(deskText);
		
		JPanel subPanelBalio= new JPanel(new FlowLayout());
		JLabel balioLabel = new JLabel("Produktuaren balioa: ");
        NumberFormatter formatterEuro = new NumberFormatter();
        formatterEuro.setValueClass(Integer.class);
        formatterEuro.setMinimum(0);
        formatterEuro.setMaximum(Integer.MAX_VALUE);
        formatterEuro.setAllowsInvalid(false);
        JFormattedTextField balioEuroText = new JFormattedTextField(formatterEuro);
        balioEuroText.setColumns(5);
        balioEuroText.setValue((int)prod.getBalioa());
        NumberFormatter formatterCentimo = new NumberFormatter();
        formatterCentimo.setValueClass(Integer.class);
        formatterCentimo.setMinimum(0);
        formatterCentimo.setMaximum(99);
        formatterCentimo.setAllowsInvalid(false);
        JFormattedTextField balioCentimoText = new JFormattedTextField(formatterCentimo);
        balioCentimoText.setColumns(5);
        balioCentimoText.setValue((prod.getBalioa()-(int)prod.getBalioa())*100);
        JLabel komaBalio=new JLabel(",");
        subPanelBalio.add(balioEuroText);
        subPanelBalio.add(komaBalio);
        subPanelBalio.add(balioCentimoText);
        datuak.add(balioLabel);
        datuak.add(subPanelBalio);
		
        JPanel subPanelSalneurri= new JPanel(new FlowLayout());
		JLabel salneurriLabel = new JLabel("Produktuaren salneurria: ");
        JFormattedTextField salneurriEuroText = new JFormattedTextField(formatterEuro);
        salneurriEuroText.setColumns(5);
        salneurriEuroText.setValue((int)prod.getSalneurria());
        JFormattedTextField salneurriCentimoText = new JFormattedTextField(formatterCentimo);
        salneurriCentimoText.setColumns(5);
        salneurriCentimoText.setValue((prod.getSalneurria()-(int)prod.getSalneurria())*100);
        JLabel komaSalneurri=new JLabel(",");
        subPanelSalneurri.add(salneurriEuroText);
        subPanelSalneurri.add(komaSalneurri);
        subPanelSalneurri.add(salneurriCentimoText);
        datuak.add(salneurriLabel);
        datuak.add(subPanelSalneurri);
		
        JLabel idKategoriaLabel=new JLabel("Kategoriaren id-a: ");
		JComboBox<String> comboIdKategoria= new JComboBox<String>();
		List<String> idKategoriaLista= conn.kontinenteIDLortuArray();
		for (int i = 0; i < idKategoriaLista.size(); i++) {
            comboIdKategoria.addItem(idKategoriaLista.get(i));
        }
		datuak.add(idKategoriaLabel);
		datuak.add(comboIdKategoria);
				
		JButton egunBotoi= new JButton("Produktu eguneratu");
		egunBotoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Produktu p=new Produktu(Integer.parseInt(idText.getText()), izenText.getText(), deskText.getText(), (Double.parseDouble(balioEuroText.getText())+(Double.parseDouble(balioCentimoText.getText())/100)), (Double.parseDouble(salneurriEuroText.getText())+(Double.parseDouble(salneurriCentimoText.getText())/100)), Integer.parseInt((String)comboIdKategoria.getSelectedItem()));
				conn.produktuEguneratu(p);
				egunDialog.dispose();

				DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) izenCombo.getModel();
	            int index = izenCombo.getSelectedIndex();
	            String izenBerria = izenText.getText();
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
		return izenText.getText();
	}
	
	private static void elementuGehitu(JFrame lehio,JComboBox<String> izenCombo) {
		Konexioa conn=new Konexioa();
		
		JDialog egunDialog= new JDialog(lehio, "Produktu eguneratu");
		JPanel top= new JPanel();
		JPanel datuak= new JPanel(new GridLayout(5,2));
		
		JLabel izenLabel=new JLabel("Produktuaren izena: ");
		JTextField izenText= new JTextField(10);
		datuak.add(izenLabel);
		datuak.add(izenText);
		
		JLabel deskLabel=new JLabel("Produktuaren deskribapena: ");
		JTextField deskText= new JTextField(10);
		datuak.add(deskLabel);
		datuak.add(deskText);
		
		JPanel subPanelBalio= new JPanel(new FlowLayout());
		JLabel balioLabel = new JLabel("Produktuaren balioa: ");
        NumberFormatter formatterEuro = new NumberFormatter();
        formatterEuro.setValueClass(Integer.class);
        formatterEuro.setMinimum(0);
        formatterEuro.setMaximum(Integer.MAX_VALUE);
        formatterEuro.setAllowsInvalid(false);
        JFormattedTextField balioEuroText = new JFormattedTextField(formatterEuro);
        balioEuroText.setColumns(5);
        NumberFormatter formatterCentimo = new NumberFormatter();
        formatterCentimo.setValueClass(Integer.class);
        formatterCentimo.setMinimum(0);
        formatterCentimo.setMaximum(99);
        formatterCentimo.setAllowsInvalid(false);
        JFormattedTextField balioCentimoText = new JFormattedTextField(formatterCentimo);
        balioCentimoText.setColumns(5);
        JLabel komaBalio=new JLabel(",");
        subPanelBalio.add(balioEuroText);
        subPanelBalio.add(komaBalio);
        subPanelBalio.add(balioCentimoText);
        datuak.add(balioLabel);
        datuak.add(subPanelBalio);
		
        JPanel subPanelSalneurri= new JPanel(new FlowLayout());
		JLabel salneurriLabel = new JLabel("Produktuaren salneurria: ");
        JFormattedTextField salneurriEuroText = new JFormattedTextField(formatterEuro);
        salneurriEuroText.setColumns(5);
        JFormattedTextField salneurriCentimoText = new JFormattedTextField(formatterCentimo);
        salneurriCentimoText.setColumns(5);
        JLabel komaSalneurri=new JLabel(",");
        subPanelSalneurri.add(salneurriEuroText);
        subPanelSalneurri.add(komaSalneurri);
        subPanelSalneurri.add(salneurriCentimoText);
        datuak.add(salneurriLabel);
        datuak.add(subPanelSalneurri);
		
        JLabel idKategoriaLabel=new JLabel("Kategoriaren id-a: ");
		JComboBox<String> comboIdKategoria= new JComboBox<String>();
		List<String> idKategoriaLista= conn.kontinenteIDLortuArray();
		for (int i = 0; i < idKategoriaLista.size(); i++) {
            comboIdKategoria.addItem(idKategoriaLista.get(i));
        }
		datuak.add(idKategoriaLabel);
		datuak.add(comboIdKategoria);
				
		JButton gehituBotoi= new JButton("Produktu gehitu");
		gehituBotoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Produktu p=new Produktu(0, izenText.getText(), deskText.getText(), (Double.parseDouble(balioEuroText.getText())+(Double.parseDouble(balioCentimoText.getText())/100)), (Double.parseDouble(salneurriEuroText.getText())+(Double.parseDouble(salneurriCentimoText.getText())/100)), Integer.parseInt((String)comboIdKategoria.getSelectedItem()));
				int idBerri=conn.produktuGehitu(p);
				egunDialog.dispose();
				if (idBerri!=0){
					izenCombo.addItem("id: "+idBerri+" izena: "+izenText.getText());
				}
			}
		});
		
		JPanel bottom=new JPanel();
		bottom.add(gehituBotoi);
		top.add(datuak);
		egunDialog.add(top);
		egunDialog.add(bottom, BorderLayout.SOUTH);
		egunDialog.setVisible(true);
		egunDialog.pack();
	}
}
