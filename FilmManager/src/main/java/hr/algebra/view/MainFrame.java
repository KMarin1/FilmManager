package hr.algebra.view;

import hr.algebra.model.Korisnik;
import hr.algebra.utilities.DataAccessException;
import hr.algebra.utilities.IconUtils;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainFrame extends javax.swing.JFrame {
    
    public MainFrame(Korisnik korisnik) throws DataAccessException {
        initComponents();
        initPanels();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tpMain = new javax.swing.JTabbedPane();
        mbMain = new javax.swing.JMenuBar();
        mFile = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        mData = new javax.swing.JMenu();
        mHelp = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Film Manager");
        setPreferredSize(new java.awt.Dimension(1200, 600));

        tpMain.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tpMain.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        getContentPane().add(tpMain, java.awt.BorderLayout.CENTER);
        tpMain.getAccessibleContext().setAccessibleName("");

        mFile.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        jMenuItem1.setText("Exit");
        mFile.add(jMenuItem1);

        mbMain.add(mFile);

        mData.setText("Data");
        mbMain.add(mData);

        mHelp.setText("Help");
        mbMain.add(mHelp);

        setJMenuBar(mbMain);

        getAccessibleContext().setAccessibleName("MainFrame");

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenu mData;
    private javax.swing.JMenu mFile;
    private javax.swing.JMenu mHelp;
    private javax.swing.JMenuBar mbMain;
    private javax.swing.JTabbedPane tpMain;
    // End of variables declaration//GEN-END:variables

    private void initPanels() throws DataAccessException {
        tpMain.add("Filmovi", new FilmPanel());
        tpMain.add("Glumac", new GlumacPanel());
        tpMain.add("Redatelj", new RedateljPanel());
        tpMain.add("Zanr", new ZanrPanel());
        tpMain.add("Korisnik", new KorisnikPanel());
        tpMain.add("SinkPanel", new SinkPanel());
        tpMain.add("RSS Parser", new RssPanel());

    }
}
