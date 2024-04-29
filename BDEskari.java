package erronka3;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.*;

/**
 * Eskari objektuen datu-base interfazea kudeatzeko klasea.
 */
public class BDEskari {
    private ArrayList<Eskari> listaEskari;

    /**
     * BDEskari objektua sortzen du.
     */
    public BDEskari() {
        listaEskari = new ArrayList<Eskari>();
    }

    /**
     * Eskari objektua datu-basean kargatzen du.
     * 
     * @param e Kargatu nahi den Eskari objektua.
     */
    public void eskariKargatu(Eskari e) {
        listaEskari.add(e);
    }

    @Override
    public String toString() {
        return "BDEskari [listaEskari=" + listaEskari + "]";
    }

    /**
     * Eskari objektuen kudeaketarako interfaze grafikoa sortzen duen main metodoa.
     * 
     * @param args Komando-lerro argumentuak (erabil ezazu ez).
     */
    public static void main(String args[]) {
        lehioAgertu();
    }

    /**
     * Eskari objektuen kudeaketarako interfazea erakusten duen metodoa.
     */
    public static void lehioAgertu() {
        JFrame mainLehio = new JFrame("Eskari taula kudeatu");
        JPanel combo = new JPanel(new FlowLayout());
        JLabel elementuIzen = new JLabel("Eskariaren id-a: ");

        JMenu menu = new JMenu("Aukerak");
        JMenuItem bueltatu = new JMenuItem("Bueltatu");
        menu.add(bueltatu);
        JMenuItem saioItxi = new JMenuItem("Saioa itxi");
        menu.add(saioItxi);
        JMenuBar jmb = new JMenuBar();
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
        List<String> elementuArray = conn.eskariIdLortuArray();
        for (int i = 0; i < elementuArray.size(); i++) {
            elementuCombo.addItem(elementuArray.get(i));
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
                elementuEguneratu(str, mainLehio, elementuCombo);
            }
        });

        gehitu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = (String) elementuCombo.getSelectedItem();
                elementuGehitu(Integer.parseInt(str), mainLehio, elementuCombo);
            }
        });

        JPanel mainPanel = new JPanel(new GridLayout(2, 0));

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
     * @param elementu Ezabatu nahi den elementua.
     * @return Elementua egoki ezabatu da ala ez.
     */
    private static boolean elementuEzabatu(Object elementu) {
        boolean bool = false;
        int dialogButton = JOptionPane.showConfirmDialog(null, "Elementu ezabatu nahi duzu?", "Ezabaketa konfirmatu",
                JOptionPane.YES_NO_OPTION);
        if (dialogButton == JOptionPane.YES_OPTION) {
            bool = true;
            Konexioa conn = new Konexioa();
            String str = (String) elementu;
            conn.deleteEskari(Integer.parseInt(str));
            System.out.println("Elementua ezabatu da");
        }
        return bool;
    }

    /**
     * Eskaria eguneratzeko metodoa.
     * 
     * @param id        Eskariaren identifikazioa.
     * @param lehio     JFrame lehioa.
     * @param izenCombo Eskariaren izenak.
     */
    private static void elementuEguneratu(String id, JFrame lehio, JComboBox<String> izenCombo) {
        Konexioa conn = new Konexioa();

        JDialog egunDialog = new JDialog(lehio, "Eskari eguneratu");
        JPanel top = new JPanel();
        JPanel datuak = new JPanel(new GridLayout(7, 2));

        JLabel idLabel = new JLabel("Eskariaren id-a: ");
        JTextField idText = new JTextField(10);
        idText.setText(id);
        idText.setEnabled(false);
        datuak.add(idLabel);
        datuak.add(idText);
        Eskari esk = conn.eskariLortu(Integer.parseInt(id));

        JLabel idBezLabel = new JLabel("Bezeroaren id-a: ");
        JComboBox<String> comboIdBez = new JComboBox<String>();
        List<String> idBezLista = conn.bezeroIDLortuArray();
        for (int i = 0; i < idBezLista.size(); i++) {
            String str = idBezLista.get(i);
            String[] parteak = str.split(" ");
            comboIdBez.addItem(parteak[1]);
        }
        comboIdBez.setSelectedItem(String.valueOf(esk.getIdBezero()));
        datuak.add(idBezLabel);
        datuak.add(comboIdBez);

        JLabel idEgoeraLabel = new JLabel("Eskariaren egoera: ");
        JComboBox<String> comboIdEgoera = new JComboBox<String>();
        List<String> idEgoeraLista = conn.egoeraIDLortuArray();
        for (int i = 0; i < idEgoeraLista.size(); i++) {
            comboIdEgoera.addItem(idEgoeraLista.get(i));
        }
        comboIdEgoera.setSelectedItem(String.valueOf(esk.getIdEgoera()));
        datuak.add(idEgoeraLabel);
        datuak.add(comboIdEgoera);

        JLabel idSalLabel = new JLabel("Saltzailearen id-a: ");
        JComboBox<String> comboIdSal = new JComboBox<String>();
        List<String> idSalLista = conn.saltzaileIDLortuArray();
        for (int i = 0; i < idSalLista.size(); i++) {
            comboIdSal.addItem(idSalLista.get(i));
        }
        comboIdSal.setSelectedItem(String.valueOf(esk.getIdSaltzaile()));
        datuak.add(idSalLabel);
        datuak.add(comboIdSal);

        JLabel dataLabel = new JLabel("Eskaera data: ");
        SpinnerDateModel model = new SpinnerDateModel(esk.getEskaeraData(), null, null, Calendar.DAY_OF_MONTH);
        JSpinner spinner = new JSpinner(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "dd/MM/yyyy");
        if (editor instanceof JSpinner.DefaultEditor) {
            JSpinner.DefaultEditor spinnerEditor = (JSpinner.DefaultEditor) editor;
            spinnerEditor.getTextField().setEditable(false);
        }
        spinner.setEditor(editor);
        datuak.add(dataLabel);
        datuak.add(spinner);

        JButton egunBotoi = new JButton("Eskari eguneratu");
        egunBotoi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Eskari esk = new Eskari(Integer.parseInt(idText.getText()),
                        Integer.parseInt((String) comboIdBez.getSelectedItem()),
                        Integer.parseInt((String) comboIdEgoera.getSelectedItem()),
                        Integer.parseInt((String) comboIdSal.getSelectedItem()), (Date) spinner.getValue());
                conn.eskariEguneratu(esk);
                egunDialog.dispose();
            }
        });

        JPanel bottom = new JPanel();
        bottom.add(egunBotoi);
        top.add(datuak);
        egunDialog.add(top);
        egunDialog.add(bottom, BorderLayout.SOUTH);
        egunDialog.setVisible(true);
        egunDialog.pack();
    }

    /**
     * Eskaria gehitzeko metodoa.
     * 
     * @param id        Eskariaren identifikazioa.
     * @param lehio     JFrame lehioa.
     * @param izenCombo Eskariaren izenak.
     */
    private static void elementuGehitu(int id, JFrame lehio, JComboBox<String> izenCombo) {
        Konexioa conn = new Konexioa();

        JDialog gehituLehio = new JDialog(lehio, "Eskari gehitu");
        gehituLehio.setVisible(true);
        JPanel top = new JPanel();
        JPanel datuak = new JPanel(new GridLayout(4, 2));

        JLabel idBezLabel = new JLabel("Bezeroaren id-a: ");
        JComboBox<String> comboIdBez = new JComboBox<String>();
        List<String> idBezLista = conn.bezeroIDLortuArray();
        for (int i = 0; i < idBezLista.size(); i++) {
            comboIdBez.addItem(idBezLista.get(i));
        }
        datuak.add(idBezLabel);
        datuak.add(comboIdBez);

        JLabel idEgoeraLabel = new JLabel("Eskariaren egoera: ");
        JComboBox<String> comboIdEgoera = new JComboBox<String>();
        List<String> idEgoeraLista = conn.egoeraIDLortuArray();
        for (int i = 0; i < idEgoeraLista.size(); i++) {
            comboIdEgoera.addItem(idEgoeraLista.get(i));
        }
        datuak.add(idEgoeraLabel);
        datuak.add(comboIdEgoera);

        JLabel idSalLabel = new JLabel("Saltzailearen id-a: ");
        JComboBox<String> comboIdSal = new JComboBox<String>();
        List<String> idSalLista = conn.saltzaileIDLortuArray();
        for (int i = 0; i < idSalLista.size(); i++) {
            comboIdSal.addItem(idSalLista.get(i));
        }
        datuak.add(idSalLabel);
        datuak.add(comboIdSal);

        JLabel dataLabel = new JLabel("Eskaera data: ");
        SpinnerDateModel model = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        JSpinner spinner = new JSpinner(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "dd/MM/yyyy");
        if (editor instanceof JSpinner.DefaultEditor) {
            JSpinner.DefaultEditor spinnerEditor = (JSpinner.DefaultEditor) editor;
            spinnerEditor.getTextField().setEditable(false);
        }
        spinner.setEditor(editor);
        datuak.add(dataLabel);
        datuak.add(spinner);

        JPanel bottom = new JPanel();
        JButton gehituBotoi = new JButton("Eskari gehitu");
        gehituBotoi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Eskari esk = new Eskari(0, Integer.parseInt((String) comboIdBez.getSelectedItem()),
                        Integer.parseInt((String) comboIdEgoera.getSelectedItem()),
                        Integer.parseInt((String) comboIdSal.getSelectedItem()), (Date) spinner.getValue());
                int idBerri = conn.insertEskari(esk);
                gehituLehio.dispose();
                izenCombo.addItem(String.valueOf(idBerri));
            }
        });

        top.add(datuak);
        bottom.add(gehituBotoi);
        gehituLehio.add(top);
        gehituLehio.add(bottom, BorderLayout.SOUTH);
        gehituLehio.pack();
    }
}
