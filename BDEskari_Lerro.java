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
 * Eskari lerroen datu-basea kudeatzeko klasea.
 */
public class BDEskari_Lerro {
    private ArrayList<Eskari_Lerro> listaLerro;

    /**
     * BDEskari_Lerro klasearen eraikitzailea.
     */
    public BDEskari_Lerro() {
        listaLerro=new ArrayList<Eskari_Lerro>();
    }

    /**
     * Eskari lerro bat datu-basean kargatzeko metodoa.
     * @param el Kargatu nahi den Eskari_Lerro objektua.
     */
    public void lerroKargatu (Eskari_Lerro el) {
        listaLerro.add(el);
    }

    @Override
    public String toString() {
        return "BDEskari_Lerro [listaLerro=" + listaLerro + "]";
    }
    
    /**
     * Eskari lerroen kudeaketako leiho nagusia erakusteko metodoa.
     */
    public static void lehioAgertu() {
        JFrame mainLehio= new JFrame("Eskari lerro-ren taula kudeatu");
        JPanel combo=new JPanel(new FlowLayout());
        JLabel elementuIzen=new JLabel("Eskari lerroren id-ak: ");
        
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
        List<String> elementuArray=conn.eskariLerroLortuArray();
        for (int i = 0; i < elementuArray.size(); i++) {
            elementuCombo.addItem(elementuArray.get(i));
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
                Eskari_Lerro eL= conn.eskariLerroLortu(Integer.parseInt(tarteak[3]),Integer.parseInt(tarteak[1]));
                elementuEguneratu(eL, mainLehio,elementuCombo);
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
    
    /**
     * Elementua datu-basean ezabatzeko metodoa.
     * @param elementu Ezabatu nahi den objektua.
     * @return True, ezabatzea arrakastaz egin bada, false bestela.
     */
    private static boolean elementuEzabatu(Object elementu) {
        boolean bool= false;
        int dialogButton=JOptionPane.showConfirmDialog(null, "Elementu ezabatu nahi duzu?", "Ezabaketa konfirmatu", JOptionPane.YES_NO_OPTION);
        if (dialogButton==JOptionPane.YES_OPTION){
            bool=true;
            Konexioa conn= new Konexioa();
            String str= (String) elementu;
            String[] parteak= str.split(" ");
            conn.deleteEskLerro(Integer.parseInt(parteak[1]), Integer.parseInt(parteak[3]));
            System.out.println("Elementua ezabatu da");
        }
        return bool;
    }
    
    /**
     * Elementua datu-basean eguneratzeko metodoa.
     * @param eL Eguneratu nahi den Eskari_Lerro objektua.
     * @param lehio Nagusiko leihoa.
     * @param izenCombo Elementuen combo-erakusle.
     * @return Eguneratutako elementuaren id-a.
     */
    private static String elementuEguneratu(Eskari_Lerro eL, JFrame lehio,JComboBox<String> izenCombo) {
        Konexioa conn=new Konexioa();
        
        JDialog egunDialog= new JDialog(lehio, "Eskari lerro eguneratu");
        JPanel top= new JPanel();
        JPanel datuak= new JPanel(new GridLayout(5,2));
        
        JLabel idEskariLabel=new JLabel("Eskariaren id-a: ");
        JTextField idEskariText= new JTextField(10);
        idEskariText.setText(String.valueOf(eL.getIdEskari()));
        idEskariText.setEnabled(false);
        datuak.add(idEskariLabel);
        datuak.add(idEskariText);
        
        JLabel idLerroLabel=new JLabel("Lerroaren id-a: ");
        JTextField idLerroText= new JTextField(10);
        idLerroText.setText(String.valueOf(eL.getIdLerro()));
        idLerroText.setEnabled(false);
        datuak.add(idLerroLabel);
        datuak.add(idLerroText);
        
        JLabel idProduktuLabel=new JLabel("Lerroaren produktu id-a: ");
        JComboBox<String> comboProduktu= new JComboBox<String>();
        List<String> produktuLista=conn.produktuIdLortuArray();
        for (int i = 0; i < produktuLista.size(); i++) {
            comboProduktu.addItem(produktuLista.get(i));
        }
        comboProduktu.setSelectedItem(String.valueOf(eL.getIdProduktu()));
        datuak.add(idProduktuLabel);
        datuak.add(comboProduktu);
        
        JLabel kopuruLabel=new JLabel("Produktu kopurua: ");
        JTextField kopuruText= new JTextField(10);
        kopuruText.setText(String.valueOf(eL.getKopuru()));
        datuak.add(kopuruLabel);
        datuak.add(kopuruText);
        
        JLabel salneurriLabel=new JLabel("Produktuaren salneurria: ");
        JTextField salneurriText= new JTextField(10);
        salneurriText.setText(String.valueOf(eL.getSalneurri()));
        datuak.add(salneurriLabel);
        datuak.add(salneurriText);
        
        JButton egunBotoi= new JButton("Eskari_Lerro eguneratu");
        egunBotoi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Eskari_Lerro lerro=new Eskari_Lerro(eL.getIdEskari(), eL.getIdLerro(), Integer.parseInt((String)comboProduktu.getSelectedItem()), Integer.parseInt(kopuruText.getText()), Double.parseDouble(salneurriText.getText()));
                conn.eskariLerroEguneratu(lerro);
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
        return idLerroText.getText();
    }
}
