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
 * Biltegi datu-basearen kudeaketa klasea.
 */
public class BDBiltegi {
    /** Biltegi objektuen lista. */
    private ArrayList<Biltegi> biltegiLista;

    /**
     * BDBiltegi klasearen konstruktorea.
     */
    public BDBiltegi() {
        biltegiLista = new ArrayList<Biltegi>();
    }

    /**
     * Biltegi objektua listan gehitzen duen metodoa.
     * 
     * @param b Gehitu nahi den Biltegi objektua
     */
    public void biltegiKargatu(Biltegi b) {
        biltegiLista.add(b);
    }

    /**
     * Klasearen propietateen string errepresentazioa itzultzen duen metodoa.
     * 
     * @return Klasearen propietateen string errepresentazioa
     */
    @Override
    public String toString() {
        return "BDBiltegi [biltegiLista=" + biltegiLista + "]";
    }
    
    /**
     * Main metoda, aplikazioaren exekuzioa hasteko.
     * 
     * @param args Main metodoaren argumentuak
     */
    public static void main(String args[]) {
        lehioAgertu();
    }
    
    /**
     * Biltegi kudeatzeko leihoa sortzen duen metodoa.
     */
    public static void lehioAgertu() {
        JFrame mainLehio = new JFrame("Biltegi taula kudeatu");
        JPanel combo = new JPanel(new FlowLayout());
        JLabel elementuIzen = new JLabel("Biltegiren id-a: ");
        
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
        
        Konexioa conn = new Konexioa();
        JComboBox<String> elementuCombo = new JComboBox<>();
        List<String> bilArray = conn.biltegiIDLortuArray();
        for (int i = 0; i < bilArray.size(); i++) {
            elementuCombo.addItem("id: " + bilArray.get(i) + " izena: " + conn.biltegiLortu((Integer.parseInt(bilArray.get(i)))).getIzena());
        }
        
        JPanel botoiak = new JPanel(new FlowLayout());
        JButton ezabatu = new JButton("Elementu ezabatu");
        JButton eguneratu = new JButton("Elementu eguneratu");
        JButton gehitu = new JButton("Elementu gehitu");
        botoiak.add(ezabatu);
        botoiak.add(eguneratu);
        botoiak.add(gehitu);
        
        ezabatu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean ezabaketa = elementuEzabatu(elementuCombo.getSelectedItem());
                if (ezabaketa) {
                    Object selectedIndex = elementuCombo.getSelectedItem();
                    elementuCombo.removeItem(selectedIndex);
                }
            }
        });
        
        eguneratu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = (String) elementuCombo.getSelectedItem();
                String[] tarteak = str.split(" ");
                elementuEguneratu(tarteak[1], mainLehio, elementuCombo);
            }
        });
        
        gehitu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = (String) elementuCombo.getSelectedItem();
                String[] tarteak = str.split(" ");
                elementuGehitu(Integer.parseInt(tarteak[1]), mainLehio, elementuCombo);
            }
        });
        
        JPanel mainPanel = new JPanel(new GridLayout(2,0));
        
        combo.add(elementuIzen);
        combo.add(elementuCombo);
        mainPanel.add(combo);
        mainPanel.add(botoiak);
        mainLehio.add(mainPanel);
        mainLehio.pack();
        mainLehio.setVisible(true);
    }
    
    /**
     * Elementua ezabatzeko metodoa.
     * 
     * @param elementu Ezabatu nahi den elementua
     * @return Ezabatu den edo ez adierazten duen boolean balioa
     */
    private static boolean elementuEzabatu(Object elementu) {
        boolean bool = false;
        int dialogButton = JOptionPane.showConfirmDialog(null, "Elementu ezabatu nahi duzu?", "Ezabaketa konfirmatu", JOptionPane.YES_NO_OPTION);
        if (dialogButton == JOptionPane.YES_OPTION){
            bool = true;
            Konexioa conn = new Konexioa();
            String str = (String) elementu;
            String[] parteak = str.split(" ");
            conn.deleteBiltegi(Integer.parseInt(parteak[1]));
            System.out.println("Elementua ezabatu da");
        }
        return bool;
    }
    
    /**
     * Elementua eguneratzeko metodoa.
     * 
     * @param id Eguneratu nahi den elementuaren ID-a
     * @param lehio Framea non eguneraketa gertatu den
     * @param izenCombo JComboBox non elementuaren izenak agertzen diren
     * @return Eguneratutako elementuaren izena
     */
    private static String elementuEguneratu(String id, JFrame lehio, JComboBox<String> izenCombo) {
        Konexioa conn = new Konexioa();
        
        JDialog egunDialog = new JDialog(lehio, "Biltegi eguneratu");
        JPanel top = new JPanel();
        JPanel datuak = new JPanel(new GridLayout(3,2));
        
        JLabel idLabel = new JLabel("Biltegiaren id-a: ");
        JTextField idText = new JTextField(10);
        idText.setText(id);
        idText.setEnabled(false);
        datuak.add(idLabel);
        datuak.add(idText);
        Biltegi bil = conn.biltegiLortu(Integer.parseInt(id));
        
        JLabel izenLabel = new JLabel("Biltegiaren izena: ");
        JTextField izenText = new JTextField(10);
        izenText.setText(bil.getIzena());
        datuak.add(izenLabel);
        datuak.add(izenText);
        
        JLabel kokalekuLabel = new JLabel("Kokaleku id-a: ");
        JComboBox<String> comboKokaleku = new JComboBox<String>();
        List<String> kokalekuLista = conn.kokalekuIdLortuArray();
        for (int i = 0; i < kokalekuLista.size(); i++) {
            comboKokaleku.addItem(kokalekuLista.get(i));
        }
        comboKokaleku.setSelectedItem(String.valueOf(bil.getIdKokaleku()));
        datuak.add(kokalekuLabel);
        datuak.add(comboKokaleku);
        
        
        JButton egunBotoi = new JButton("Biltegi eguneratu");
        egunBotoi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Biltegi b = new Biltegi(Integer.parseInt(idText.getText()), izenText.getText(), Integer.parseInt((String)comboKokaleku.getSelectedItem()));
                conn.biltegiEguneratu(b);
                egunDialog.dispose();

                DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) izenCombo.getModel();
                int index = izenCombo.getSelectedIndex();
                String izenBerria = izenText.getText();
                model.removeElementAt(index);
                model.insertElementAt("id: " + idText.getText() + " izena: " + izenBerria, index);
                izenCombo.setSelectedIndex(index);
            }
        });
        
        JPanel bottom = new JPanel();
        bottom.add(egunBotoi);
        top.add(datuak);
        egunDialog.add(top);
        egunDialog.add(bottom, BorderLayout.SOUTH);
        egunDialog.setVisible(true);
        egunDialog.pack();
        return izenText.getText();
    }
    
    /**
     * Elementua gehitzeko metodoa.
     * 
     * @param id Gehitu nahi den elementuaren ID-a
     * @param lehio Framea non gehitzea gertatu den
     * @param izenCombo JComboBox non elementuaren izenak agertzen diren
     */
    private static void elementuGehitu(int id, JFrame lehio, JComboBox<String> izenCombo) {
        Konexioa conn = new Konexioa();
        
        JDialog gehituDialog = new JDialog(lehio, "Biltegi gehitu");
        JPanel top = new JPanel();
        JPanel datuak = new JPanel(new GridLayout(3,2));
        
        JLabel izenLabel = new JLabel("Biltegiaren izena: ");
        JTextField izenText = new JTextField(10);
        datuak.add(izenLabel);
        datuak.add(izenText);
        
        JLabel kokalekuLabel = new JLabel("Kokaleku id-a: ");
        JComboBox<String> comboKokaleku = new JComboBox<String>();
        List<String> kokalekuLista = conn.kokalekuIdLortuArray();
        for (int i = 0; i < kokalekuLista.size(); i++) {
            comboKokaleku.addItem(kokalekuLista.get(i));
        }
        datuak.add(kokalekuLabel);
        datuak.add(comboKokaleku);
        
        JPanel bottom = new JPanel();
        JButton gehituBotoi = new JButton("Bezero gehitu");
        gehituBotoi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Biltegi bil = new Biltegi(id, izenText.getText(), Integer.valueOf((String)comboKokaleku.getSelectedItem()));
                int idBerri = conn.insertBiltegi(bil);
                gehituDialog.dispose();
                String izenBerria = izenText.getText();
                izenCombo.addItem("id: " + idBerri + " izena: " + izenBerria);
                
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
