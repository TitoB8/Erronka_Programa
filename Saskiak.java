package erronka3;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.SQLException;

public class Saskiak {

    public static void main(String[] args) throws SQLException {
        Saskiak.eskariakIkusi();
    }

    public static void eskariakIkusi() throws SQLException {
        JLabel infoJL = new JLabel("Eskari Informazioa:");
        JLabel eskariKopurua = new JLabel("Eskari Kopurua:");

        JTextArea area = new JTextArea(10, 40);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem bueltatuMenuItem = new JMenuItem("Bueltatu");
        JMenuItem kanzelatuMenuItem = new JMenuItem("Eskaria kanzelatu");
        JMenuItem itxiMenuItem = new JMenuItem("Saioa itxi");
        menu.add(bueltatuMenuItem);
        menu.add(kanzelatuMenuItem);
        menu.add(itxiMenuItem);
        menuBar.add(menu);

        JPanel menuBarPanel = new JPanel();
        menuBarPanel.add(menuBar);

        JPanel eskariPanel = new JPanel(new BorderLayout());
        eskariPanel.add(infoJL, BorderLayout.NORTH);
        eskariPanel.add(scrollPane, BorderLayout.CENTER);
        eskariPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel countPanel = new JPanel(new BorderLayout());
        countPanel.add(eskariKopurua, BorderLayout.WEST);
        JTextField countTF = new JTextField(10);
        countTF.setEditable(false);
        countPanel.add(countTF, BorderLayout.CENTER);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(menuBarPanel, BorderLayout.NORTH);
        mainPanel.add(eskariPanel, BorderLayout.CENTER);
        mainPanel.add(countPanel, BorderLayout.SOUTH);

        JFrame eskariFrame = new JFrame("Eskariak");
        eskariFrame.add(mainPanel);
        eskariFrame.pack();
        eskariFrame.setLocationRelativeTo(null);
        eskariFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        eskariFrame.setVisible(true);

        bueltatuMenuItem.addActionListener(ebentua -> {
            BezeroMain.main_agertu();
            eskariFrame.dispose();
        });
        itxiMenuItem.addActionListener(ebentua -> {
            Main.mainAgertu();
            eskariFrame.dispose();
        });

        kanzelatuMenuItem.addActionListener(ebentua -> {
            JPanel panel = new JPanel(new GridLayout(0, 2));

            JComboBox<String> eskariComboBox = new JComboBox<>();
            panel.add(new JLabel("Aukeratu Eskaria:"));
            panel.add(eskariComboBox);

            Konexioa konexioa = new Konexioa();
            ArrayList<EskariLerr> eskariak = (ArrayList<EskariLerr>) konexioa.eskariakIkusi(Main.getId());
            int index = 1;
            for (EskariLerr eskari : eskariak) {
                eskariComboBox.addItem("Eskaria " + index++ + ": " + eskari.getId());
            }


            JButton aldatu = new JButton("Aldatu");
            panel.add(new JLabel()); 
            panel.add(aldatu);

            JFrame bgFrame = new JFrame("Bezeroa gehitu");
            bgFrame.add(panel);
            bgFrame.setLocationRelativeTo(null);
            bgFrame.pack();
            bgFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            bgFrame.setVisible(true);

            aldatu.addActionListener(ebentua2 -> {
                String selectedEskari = (String) eskariComboBox.getSelectedItem();
                if (selectedEskari != null) {
                    int idEskari = Integer.parseInt(selectedEskari.substring(selectedEskari.lastIndexOf(" ") + 1));
                    konexioa.cancelEskari(idEskari);
					JOptionPane.showMessageDialog(null, "Eskaria " + idEskari + " kanzelatu da.");
					bgFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Ez da eskaria aukeratu.");
                }
            });

        });
        eskariakKargatu(area, countTF);
    }

    private static String formatDate(Date date) {
        SimpleDateFormat dataFormatoa = new SimpleDateFormat("yyyy-MM-dd");
        return dataFormatoa.format(date);
    }

    public static void eskariakKargatu(JTextArea area, JTextField countTF) {
        Konexioa konexioa = new Konexioa();
        ArrayList<EskariLerr> eskariLista = (ArrayList<EskariLerr>) konexioa.eskariakIkusi(Main.getId());

        StringBuilder sb = new StringBuilder();
        for (EskariLerr eskari : eskariLista) {
            sb.append("Bezero ID: ").append(eskari.getIdBezero()).append("\n");
            sb.append("Eskari ID: ").append(eskari.getId()).append("\n");
            sb.append("Produktu ID: ").append(eskari.getIdProduktu()).append("\n");
            sb.append("Deskribapena: ").append(eskari.getDeskribapena()).append("\n");
            sb.append("Eskaera data: ").append(formatDate((Date) eskari.getEskaeraData())).append("\n");
            sb.append("Salneurria: ").append(eskari.getSalneurria()).append("\n");
            sb.append("Kopurua: ").append(eskari.getKopurua()).append("\n");
            sb.append("------------------------------------\n");
        }

        area.setText(sb.toString());
        countTF.setText(String.valueOf(eskariLista.size()));
    }
}
