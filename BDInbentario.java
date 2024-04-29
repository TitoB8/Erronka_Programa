package erronka3;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.text.NumberFormatter;

public class BDInbentario {
	private ArrayList<Inbentario> inbentarioLista;

	public BDInbentario() {
		inbentarioLista=new ArrayList<Inbentario>();
	}

	public void inbentarioKargatu(Inbentario i) {
		inbentarioLista.add(i);
	}

	@Override
	public String toString() {
		return "BDInbentario [inbentarioLista=" + inbentarioLista + "]";
	}
	
	public static void main(String args[]) {
		lehioAgertu();
	}
	
	public static void lehioAgertu() {
		JFrame mainLehio= new JFrame("Inbentario taula kudeatu");
		JPanel combo=new JPanel(new FlowLayout());
		JLabel elementuIzen=new JLabel("Inbentarioaren datuak: ");
		
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
		List<Inbentario> elementuArray=conn.inbentarioLortuArray();
		for (int i = 0; i < elementuArray.size(); i++) {
            elementuCombo.addItem("Id_biltegi: "+elementuArray.get(i).getIdBiltegi()+" Id_produktu: "+elementuArray.get(i).getIdProduktu());
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
				Inbentario inb= conn.inbentarioLortu(Integer.parseInt(tarteak[3]),Integer.parseInt(tarteak[1]));
				elementuEguneratu(inb, mainLehio,elementuCombo);
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
			String[] parteak= str.split(" ");
			conn.deleteInbentario(Integer.parseInt(parteak[1]), Integer.parseInt(parteak[3]));
			System.out.println("Elementua ezabatu da");
		}
		return bool;
	}
	
	private static void elementuEguneratu(Inbentario inb, JFrame lehio,JComboBox<String> izenCombo) {
		Konexioa conn=new Konexioa();
		
		JDialog egunDialog= new JDialog(lehio, "Inbentario eguneratu");
		JPanel top= new JPanel();
		JPanel datuak= new JPanel(new GridLayout(3,2));
		
		JLabel idBiltegiLabel=new JLabel("Biltegiaren id-a: ");
		JTextField idBiltegiText= new JTextField(10);
		idBiltegiText.setText(String.valueOf(inb.getIdBiltegi()));
		idBiltegiText.setEnabled(false);
		datuak.add(idBiltegiLabel);
		datuak.add(idBiltegiText);
		
		JLabel idProduktuLabel=new JLabel("Produktuaren id-a: ");
		JTextField idProduktuText= new JTextField(10);
		idProduktuText.setText(String.valueOf(inb.getIdProduktu()));
		idProduktuText.setEnabled(false);
		datuak.add(idProduktuLabel);
		datuak.add(idProduktuText);
		
		JLabel kopuruLabel = new JLabel("Produktuaren kopurua biltegian: ");
        NumberFormatter formatter = new NumberFormatter();
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        JFormattedTextField kopuruText = new JFormattedTextField(formatter);
        kopuruText.setColumns(10);
        kopuruText.setValue(inb.getKopuru());
        datuak.add(kopuruLabel);
        datuak.add(kopuruText);
			
		JButton egunBotoi= new JButton("Inbentario eguneratu");
		egunBotoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Inbentario inbBerri=new Inbentario(inb.getIdBiltegi(), inb.getIdProduktu(), Integer.parseInt(kopuruText.getText()));
				conn.inbentarioEguneratu(inbBerri);
				System.out.println("Elementu eguneratu da");
				egunDialog.dispose();
			}
		});
		
		JPanel bottom=new JPanel();
		bottom.add(egunBotoi);
		top.add(datuak);
		egunDialog.add(top);
		egunDialog.add(bottom, BorderLayout.SOUTH);
		egunDialog.setVisible(true);
		egunDialog.pack();
	}
	
	private static void elementuGehitu(JFrame lehio, JComboBox<String> izenCombo) {
		Konexioa conn=new Konexioa();
		
		JDialog gehituDialog= new JDialog(lehio, "Biltegi gehitu");
		JPanel top= new JPanel();
		JPanel datuak= new JPanel(new GridLayout(3,2));
		
		JLabel biltegiLabel=new JLabel("Biltegi id-a: ");
		JComboBox<String> comboBiltegi= new JComboBox<String>();
		List<String> biltegiLista= conn.biltegiIdLortuArray();
		for (int i = 0; i < biltegiLista.size(); i++) {
            comboBiltegi.addItem(biltegiLista.get(i));
        }
		comboBiltegi.addActionListener(comboBiltegi);
		datuak.add(biltegiLabel);
		datuak.add(comboBiltegi);
		
		JComboBox<String> comboProduktu= new JComboBox<String>();
		comboBiltegi.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        String selectedBiltegi = (String) comboBiltegi.getSelectedItem();
		        int biltegiId = Integer.parseInt(selectedBiltegi);
		        ArrayList<Integer> produktuLista = conn.ezDaudenProduktuak(biltegiId);
		        comboProduktu.removeAllItems();
		        for (Integer id : produktuLista) {
		            comboProduktu.addItem(String.valueOf(id));
		        }
		    }
		});

		JLabel produktuLabel=new JLabel("Produktuaren id-a: ");
		datuak.add(produktuLabel);
		datuak.add(comboProduktu);
		
		JLabel kopuruLabel = new JLabel("Produktuaren kopurua biltegian: ");
        NumberFormatter formatter = new NumberFormatter();
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        JFormattedTextField kopuruText = new JFormattedTextField(formatter);
        kopuruText.setColumns(10);
        datuak.add(kopuruLabel);
        datuak.add(kopuruText);
		
		JPanel bottom= new JPanel();
		JButton gehituBotoi=new JButton("Bezero gehitu");
		gehituBotoi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Inbentario inb= new Inbentario(Integer.parseInt((String)comboProduktu.getSelectedItem()),Integer.parseInt((String)comboBiltegi.getSelectedItem()),Integer.valueOf(kopuruText.getText()));
				boolean b=conn.insertInbentario(inb);
				if(b) {
					gehituDialog.dispose();
					izenCombo.addItem("Id_biltegi: "+ inb.getIdBiltegi()+ " Id_produktu: " +inb.getIdProduktu());
				}
			}
		});
		top.add(datuak);
		bottom.add(gehituBotoi);
		gehituDialog.add(top);
		gehituDialog.add(bottom, BorderLayout.SOUTH);
		gehituDialog.pack();
		gehituDialog.setVisible(true);
	}
	
}