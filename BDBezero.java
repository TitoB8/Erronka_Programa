package erronka3;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BDBezero {
	private ArrayList<Bezero> bezeroLista;

	public BDBezero() {
		bezeroLista= new ArrayList<Bezero>();
	}

	public void bezeroKargatu(Bezero b) {
		bezeroLista.add(b);
	}

	@Override
	public String toString() {
		return "BDBezero [bezeroLista=" + bezeroLista + "]";
	}
	
	//metodoak
		public void setLista(ArrayList<Bezero> lista) {
			this.bezeroLista = lista;
		}

		public void gehituBezero(Bezero bezero) {
			bezeroLista.add(bezero);
		}
		
		public void kenduBezero(Bezero bezero) {
			bezeroLista.remove(bezero);
		}
	
	public static void main(String args[]) {
		lehioAgertu();
	}
	
	public static void lehioAgertu() {
		JFrame mainLehio= new JFrame("Bezero taula kudeatu");
		JPanel combo=new JPanel(new FlowLayout());
		JLabel elementuIzen=new JLabel("Bezeroaren id-a: ");
		
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
		List<String> elementuArray=conn.bezeroIDLortuArray();
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
				String str=(String) elementuCombo.getSelectedItem();
				String[] tarteak= str.split(" ");
				elementuGehitu(Integer.parseInt(tarteak[1]),mainLehio,elementuCombo);
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
	
	private static void elementuGehitu(int id,JFrame lehio, JComboBox<String> izenCombo) {
		Konexioa conn=new Konexioa();

		JDialog gehituLehio=new JDialog(lehio,"Bezeroa gehitu");
		gehituLehio.setVisible(true);
		JPanel top= new JPanel();
		JPanel datuak= new JPanel(new GridLayout(6,2));
		
		JLabel izenLabel=new JLabel("Bezeroaren izena: ");
		JTextField izenText= new JTextField(10);
		datuak.add(izenLabel);
		datuak.add(izenText);
		
		JLabel abizenLabel=new JLabel("Bezeroaren abizena: ");
		JTextField abizenText= new JTextField(10);
		datuak.add(abizenLabel);
		datuak.add(abizenText);
		
		JLabel helbideLabel=new JLabel("Bezeroaren helbidea: ");
		JTextField helbideText= new JTextField(10);
		datuak.add(helbideLabel);
		datuak.add(helbideText);
		
		JLabel emailLabel=new JLabel("Bezeroaren e-maila: ");
		JTextField emailText= new JTextField(10);
		datuak.add(emailLabel);
		datuak.add(emailText);
		
		JLabel erabiltzaileLabel=new JLabel("Bezeroaren erabiltzailea: ");
		JTextField erabiltzaileText= new JTextField(10);
		datuak.add(erabiltzaileLabel);
		datuak.add(erabiltzaileText);
		
		JLabel pasahitzaLabel=new JLabel("Bezeroaren erabiltzailea: ");
		JTextField pasahitzaText= new JTextField(10);
		datuak.add(pasahitzaLabel);
		datuak.add(pasahitzaText);
		top.add(datuak);
		
		JPanel bottom= new JPanel();
		JButton gehituBotoi=new JButton("Bezero gehitu");
		gehituBotoi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Bezero bez= new Bezero(0,izenText.getText(),abizenText.getText(),helbideText.getText(),emailText.getText(),erabiltzaileText.getText(),pasahitzaText.getText());
				int idBerri=conn.insertBezero(bez);
				gehituLehio.dispose();
				String izenBerria = izenText.getText();
				izenCombo.addItem("id: "+ idBerri+ " izena: " +izenBerria);
			}
		});
		
		bottom.add(gehituBotoi);
		gehituLehio.add(top);
		gehituLehio.add(bottom, BorderLayout.SOUTH);
		gehituLehio.pack();
	}
	
	private static boolean elementuEzabatu(Object elementu) {
		boolean bool= false;
		int dialogButton=JOptionPane.showConfirmDialog(null, "Elementu ezabatu nahi duzu?", "Ezabaketa konfirmatu", JOptionPane.YES_NO_OPTION);
		if (dialogButton==JOptionPane.YES_OPTION){
			bool=true;
			Konexioa conn= new Konexioa();
			String str= (String) elementu;
			String[] parteak= str.split(" ");
			conn.deleteBezero(Integer.parseInt(parteak[1]));
			System.out.println("Elementua ezabatu da");
		}
		return bool;
	}
	
	private static String elementuEguneratu(String id, JFrame lehio,JComboBox<String> izenCombo) {
		Konexioa conn=new Konexioa();
		
		JDialog egunDialog= new JDialog(lehio, "Bezero eguneratu");
		JPanel top= new JPanel();
		JPanel datuak= new JPanel(new GridLayout(7,2));
		
		JLabel idLabel=new JLabel("Bezeroaren id-a: ");
		JTextField idText= new JTextField(10);
		idText.setText(id);
		idText.setEnabled(false);
		datuak.add(idLabel);
		datuak.add(idText);
		Bezero bez= conn.bezeroLortu(Integer.parseInt(id));
		
		JLabel izenLabel=new JLabel("Bezeroaren izena: ");
		JTextField izenText= new JTextField(10);
		izenText.setText(bez.getIzena());
		datuak.add(izenLabel);
		datuak.add(izenText);
		
		JLabel abizenLabel=new JLabel("Bezeroaren abizena: ");
		JTextField abizenText= new JTextField(10);
		abizenText.setText(bez.getAbizena());
		datuak.add(abizenLabel);
		datuak.add(abizenText);
		
		JLabel helbideLabel=new JLabel("Bezeroaren helbidea: ");
		JTextField helbideText= new JTextField(10);
		helbideText.setText(bez.getHelbidea());
		datuak.add(helbideLabel);
		datuak.add(helbideText);
		
		JLabel emailLabel=new JLabel("Bezeroaren e-maila: ");
		JTextField emailText= new JTextField(10);
		emailText.setText(bez.getEmaila());
		datuak.add(emailLabel);
		datuak.add(emailText);
		
		JLabel erabiltzaileLabel=new JLabel("Bezeroaren erabiltzailea: ");
		JTextField erabiltzaileText= new JTextField(10);
		erabiltzaileText.setText(bez.getErabiltzaile());
		datuak.add(erabiltzaileLabel);
		datuak.add(erabiltzaileText);
		
		JLabel pasahitzaLabel=new JLabel("Bezeroaren erabiltzailea: ");
		JTextField pasahitzaText= new JTextField(10);
		pasahitzaText.setText(bez.getPasahitza());
		datuak.add(pasahitzaLabel);
		datuak.add(pasahitzaText);
		
		JButton egunBotoi= new JButton("Bezero eguneratu");
		egunBotoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Bezero b=new Bezero(Integer.parseInt(idText.getText()), izenText.getText(), abizenText.getText(), helbideText.getText(), emailText.getText(), erabiltzaileText.getText(), pasahitzaText.getText());
				conn.bezeroEguneratu(b);
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
}

	
