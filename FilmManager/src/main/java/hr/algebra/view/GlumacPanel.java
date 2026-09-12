package hr.algebra.view;

import hr.algebra.dal.Repository;
import hr.algebra.dal.RepositoryFactory;
import hr.algebra.model.Glumac;
import hr.algebra.utilities.DataAccessException;
import hr.algebra.utilities.MessageUtils;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

public class GlumacPanel extends javax.swing.JPanel {

    private Repository repository;
    private DefaultListModel<Glumac> model = new DefaultListModel<>();

    public GlumacPanel() {
        repository = RepositoryFactory.getRepository();
        initComponents();
        init();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tbfBtn = new javax.swing.JToolBar();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        tfIme = new javax.swing.JTextField();
        tfPrezime = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lsGlumci = new javax.swing.JList<>();

        tbfBtn.setRollover(true);

        btnAdd.setText("Add");
        btnAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAdd.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAdd.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        tbfBtn.add(btnAdd);

        btnEdit.setText("Edit");
        btnEdit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEdit.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        tbfBtn.add(btnEdit);

        btnDelete.setText("Delete");
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnDelete.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDelete.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        tbfBtn.add(btnDelete);

        jLabel1.setText("Ime");

        jLabel2.setText("Prezime");

        jScrollPane1.setViewportView(lsGlumci);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(tfPrezime, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfIme, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(272, Short.MAX_VALUE))
            .addComponent(tbfBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tbfBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfIme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfPrezime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(83, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    Glumac selectedGlumac = null;

    private void showGlumac() {
        int selectedRow = lsGlumci.getSelectedIndex();
        if (selectedRow < 0) {
            // ako nema selekcije, očisti formu i kosi selectedGlumac
            clearForm();
            selectedGlumac = null;
            return;
        }
        // tek kad je stvarno kliknuto na red:

        selectedGlumac = model.getElementAt(selectedRow);
        tfIme.setText(selectedGlumac.getIme());
        tfPrezime.setText(selectedGlumac.getPrezime());
    }

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (selectedGlumac == null) {
            MessageUtils.showErrorMessage("Greška", "Odaberite glumca za brisanje.");
            return;
        }
        try {
            repository.deleteGlumac(selectedGlumac.getId());
            model.removeElement(selectedGlumac);
            clearForm();
        } catch (DataAccessException e) {
            Logger.getLogger(GlumacPanel.class.getName()).log(Level.SEVERE, null, e);
        }

    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        if (selectedGlumac == null || !formValid()) {
            MessageUtils.showErrorMessage("Greška", "Odaberite glumca i unesite valjane podatke.");
            return;
        }
        try {
            selectedGlumac.setIme(tfIme.getText().trim());
            selectedGlumac.setPrezime(tfPrezime.getText().trim());
            repository.updateGlumac(selectedGlumac);
            lsGlumci.repaint();
            clearForm();
        } catch (DataAccessException e) {
            Logger.getLogger(GlumacPanel.class.getName()).log(Level.SEVERE, null, e);
        }

    }//GEN-LAST:event_btnEditActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {
        if (!formValid()) {
            MessageUtils.showErrorMessage("Greška", "Unesite ime i prezime glumca.");
            return;
        }

        try {
            String ime = tfIme.getText().trim();
            String prezime = tfPrezime.getText().trim();

            Glumac glumac = new Glumac(ime, prezime);
            repository.insertGlumac(glumac);
            MessageUtils.showInformationMessage("Uspjeh", "Glumac je dodan.");
            clearForm();

        } catch (DataAccessException ex) {
            Logger.getLogger(GlumacPanel.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("Greška", "Ne mogu spremiti glumca.");
        }
    }

    private boolean formValid() {
        return !tfIme.getText().trim().isEmpty() && !tfPrezime.getText().trim().isEmpty();
    }

    private void clearForm() {
        tfIme.setText("");
        tfPrezime.setText("");
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<Glumac> lsGlumci;
    private javax.swing.JToolBar tbfBtn;
    private javax.swing.JTextField tfIme;
    private javax.swing.JTextField tfPrezime;
    // End of variables declaration//GEN-END:variables

    private void init() {
        lsGlumci.setModel(model);
        lsGlumci.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                    showGlumac();
            }
        });
        loadGlumci();

    }

    private void loadGlumci() {
        try {
            model.clear();
            repository.getAllGlumci().forEach(g -> {
                model.addElement((Glumac) g);
            });
        } catch (Exception ex) {
            MessageUtils.showErrorMessage("Greška", "Ne mogu dohvatiti glumce");
            Logger.getLogger(GlumacPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
