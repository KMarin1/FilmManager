package hr.algebra.view;

import hr.algebra.dal.Repository;
import hr.algebra.dal.RepositoryFactory;
import hr.algebra.dal.sql.SqlRepository;
import hr.algebra.model.Film;
import hr.algebra.model.Glumac;
import hr.algebra.model.Redatelj;
import hr.algebra.model.Zanr;
import hr.algebra.utilities.DataAccessException;
import hr.algebra.utilities.FileUtils;
import hr.algebra.utilities.IconUtils;
import hr.algebra.utilities.MessageUtils;
import hr.algebra.view.model.FilmTableModel;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.TransferHandler;
import static javax.swing.TransferHandler.MOVE;
import javax.swing.text.JTextComponent;


public class FilmPanel extends javax.swing.JPanel {

            

    public FilmPanel() {

        try {
            initComponents();
            initValidation();
            initRepository();
            initComboBox();
            initZanr();
            initGlumci();
            initTable();
            
        } catch (Exception ex) {
            Logger.getLogger(FilmPanel.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("error", "Cannot init");
            System.exit(1);
        }
            
    }
        private Map<String, Glumac> glumciMap = new HashMap<>();
        private Map<String, Redatelj> redateljiMap = new HashMap<>();
        private Map<String, Zanr> zanroviMap = new HashMap<>();
        private Repository repository;
        private FilmTableModel model;
        private Film selectedFilm;

        private List<JTextComponent> validationFields;
        private List<JLabel> errorLabels;
        
        
    private void initRepository() throws Exception {
        repository = RepositoryFactory.getRepository();
        
    }
    
    private void initComboBox() throws DataAccessException, SQLException {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        List<Redatelj> redatelji = repository.getAllRedatelji();

        for (Redatelj r : redatelji) {
            String name = (r.getIme() + " " + r.getPrezime()).trim().toLowerCase();
            model.addElement(name);
            redateljiMap.put(name, r);
        }
        

        cbRedatelj.setModel(model);
    }
    
    private void initZanr() throws DataAccessException, SQLException{
        DefaultListModel<String> genreModel = new DefaultListModel<>();


        Object data = repository.getAllZanrovi();

        if (data instanceof Zanr[]) {

            Arrays.stream((Zanr[]) data)
                  .forEach(z -> {
                      String naziv = z.getNaziv();
                      genreModel.addElement(naziv);
                      zanroviMap.put(naziv, z);
                  });
        } else if (data instanceof List) {
            
            @SuppressWarnings("unchecked")
            List<Zanr> list = (List<Zanr>) data;
            list.forEach(z -> {
                String naziv = z.getNaziv();
                genreModel.addElement(naziv);
                zanroviMap.put(naziv, z);
            });
        } else {
            throw new IllegalStateException(
                "Očekivao sam Zanr[] ili List<Zanr>, ali dobio: " 
                + data.getClass());
        }

        lsZanr.setModel(genreModel);
        lsZanr.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tbfBtn = new javax.swing.JToolBar();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        spFilm = new javax.swing.JScrollPane();
        tbFilm = new javax.swing.JTable();
        spGlumci = new javax.swing.JScrollPane();
        lsGlumci = new javax.swing.JList<>();
        spZanr = new javax.swing.JScrollPane();
        lsZanr = new javax.swing.JList<>();
        cbRedatelj = new javax.swing.JComboBox<>();
        tfNaziv = new javax.swing.JTextField();
        spGodina = new javax.swing.JSpinner();
        btnUpload = new javax.swing.JButton();
        lblImage = new javax.swing.JLabel();
        lblNaziv = new javax.swing.JLabel();
        lblRedatelj = new javax.swing.JLabel();
        lblGodina = new javax.swing.JLabel();
        lblGlumci = new javax.swing.JLabel();
        lblZanr = new javax.swing.JLabel();
        tfPutanjaSlike = new javax.swing.JTextField();
        lblGlumciError = new javax.swing.JLabel();
        lblZanrError = new javax.swing.JLabel();
        lblNazivError = new javax.swing.JLabel();
        lblRedateljError = new javax.swing.JLabel();
        lblGodinaError = new javax.swing.JLabel();
        lblPutanjaError = new javax.swing.JLabel();
        lblOpis = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        taDesc = new javax.swing.JTextArea();

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
        btnEdit.setFocusable(false);
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

        tbFilm.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbFilm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbFilmMouseClicked(evt);
            }
        });
        tbFilm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbFilmKeyReleased(evt);
            }
        });
        spFilm.setViewportView(tbFilm);

        lsGlumci.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        lsGlumci.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        spGlumci.setViewportView(lsGlumci);

        lsZanr.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        spZanr.setViewportView(lsZanr);

        cbRedatelj.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnUpload.setText("Upload");
        btnUpload.setFocusable(false);
        btnUpload.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUpload.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnUpload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUploadActionPerformed(evt);
            }
        });

        lblImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/no_image.png"))); // NOI18N

        lblNaziv.setText("Naziv");

        lblRedatelj.setText("Redatelj");

        lblGodina.setText("Godina");

        lblGlumci.setText("Glumci");

        lblZanr.setText("Zanr");

        lblGlumciError.setBackground(new java.awt.Color(255, 0, 0));
        lblGlumciError.setForeground(new java.awt.Color(255, 0, 0));
        lblGlumciError.setText("X");

        lblZanrError.setBackground(new java.awt.Color(255, 0, 0));
        lblZanrError.setForeground(new java.awt.Color(255, 0, 0));
        lblZanrError.setText("X");

        lblNazivError.setBackground(new java.awt.Color(255, 0, 0));
        lblNazivError.setForeground(new java.awt.Color(255, 0, 0));
        lblNazivError.setText("X");

        lblRedateljError.setBackground(new java.awt.Color(255, 0, 0));
        lblRedateljError.setForeground(new java.awt.Color(255, 0, 0));
        lblRedateljError.setText("X");

        lblGodinaError.setBackground(new java.awt.Color(255, 0, 0));
        lblGodinaError.setForeground(new java.awt.Color(255, 0, 0));
        lblGodinaError.setText("X");

        lblPutanjaError.setBackground(new java.awt.Color(255, 0, 0));
        lblPutanjaError.setForeground(new java.awt.Color(255, 0, 0));
        lblPutanjaError.setText("X");

        lblOpis.setText("Opis");

        taDesc.setColumns(20);
        taDesc.setRows(5);
        jScrollPane1.setViewportView(taDesc);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tbfBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 787, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 6, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(spFilm, javax.swing.GroupLayout.PREFERRED_SIZE, 653, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(spGlumci, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                            .addComponent(spZanr)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblGlumci)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblGlumciError, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblZanr)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblZanrError, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpload)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblRedatelj)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblRedateljError)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbRedatelj, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblGodina, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblGodinaError)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spGodina, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(lblNaziv)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblNazivError, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfNaziv, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(tfPutanjaSlike, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPutanjaError, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblOpis)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tbfBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblGlumci)
                                    .addComponent(lblGlumciError))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(9, 9, 9)
                                        .addComponent(spGlumci, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(27, 27, 27)
                                                .addComponent(spZanr, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                .addComponent(btnUpload, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(36, 36, 36))
                                                            .addGroup(layout.createSequentialGroup()
                                                                .addGap(26, 26, 26)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                    .addComponent(lblNaziv)
                                                                    .addComponent(lblNazivError)
                                                                    .addComponent(tfNaziv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                            .addComponent(lblRedatelj)
                                                            .addComponent(lblRedateljError)
                                                            .addComponent(cbRedatelj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(lblZanr)
                                                        .addComponent(lblZanrError)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(lblGodina)
                                                    .addComponent(lblGodinaError)
                                                    .addComponent(spGodina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblOpis)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jScrollPane1))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(176, 176, 176)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(tfPutanjaSlike, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblPutanjaError))))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(spFilm))))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnUploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUploadActionPerformed
        File file = FileUtils.uploadFile("images","jpg","jpeg","png");
        if (file == null) {
            return;
        }
        tfPutanjaSlike.setText(file.getAbsolutePath());
        setIcon(lblImage, file);
    }//GEN-LAST:event_btnUploadActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
       
        
        if (!formValid()) {
            return;
        }
        try {
            String localPicturePath = uploadPicture();
            Film film;
            List<String> name = lsGlumci.getSelectedValuesList();
            List<Glumac> glumci = new ArrayList<>();
            
            name.forEach(glumac -> {
                
                String[] details = glumac.split(" ");
                
                try {
                    repository
                            .getGlumacByName(details[0],details[1])
                            .ifPresent(g -> glumci.add((Glumac) g));
                } catch (DataAccessException ex) {
                    Logger.getLogger(FilmPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
            

                String sel = (String) cbRedatelj.getSelectedItem();
                Redatelj r = redateljiMap.get(sel);
                String selGenre = lsZanr.getSelectedValue();
                Zanr z = zanroviMap.get(selGenre);
            film = new Film(
                    tfNaziv.getText().trim(),
                    (int)spGodina.getValue(),
                    taDesc.getText().trim(), 
                    r,
                    z, 
                    glumci,
                    localPicturePath
            );
            
            repository.insertFilm(film);
            addGlumciToFilm(film.getId(), glumci);
            model.setFilms(repository.getAllFilms());

            clearForm();
        } catch (Exception ex) {
            Logger.getLogger(FilmPanel.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("Error", "Unable to create film!");
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
            
        if (selectedFilm == null) {
            MessageUtils.showErrorMessage("Greška", "Odaberite film za uređivanje");
            return;
        }
        if (!formValid()) {
            return;
        }

        try {
            String newSlika = uploadPicture();
            String newPath = tfPutanjaSlike.getText().trim();
            if (!newPath.equals(selectedFilm.getPutanjaSlike())) {
                if (selectedFilm.getPutanjaSlike() != null) {
                    Files.deleteIfExists(Paths.get(selectedFilm.getPutanjaSlike()));
                }
                selectedFilm.setSlikaPutanja(newSlika);
                tfPutanjaSlike.setText(newSlika);
            }

            selectedFilm.setNaziv(tfNaziv.getText().trim());
            selectedFilm.setGodina((int) spGodina.getValue());
            selectedFilm.setOpis(taDesc.getText().trim());

            // Redatelj
            String selDir = (String) cbRedatelj.getSelectedItem();
            selectedFilm.setRedatelj(redateljiMap.get(selDir));

            // Žanr
            String selGenre = lsZanr.getSelectedValue();
            selectedFilm.setZanr(zanroviMap.get(selGenre));

            // Glumci
            List<Glumac> gl = new ArrayList<>();
            
            lsGlumci.getSelectedValuesList()
                .forEach(name ->
                    {
                try {
                    String[] details = name.split(" ");
                    repository.getGlumacByName(details[0],details[1])
                            .ifPresent(g -> gl.add((Glumac) g));
                } catch (DataAccessException ex) {
                    Logger.getLogger(FilmPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                );
            selectedFilm.setGlumci(gl);
            repository.removeGlumacFromFilm(selectedFilm.getId());
            addGlumciToFilm(selectedFilm.getId(),gl);
            repository.updateFilm(selectedFilm);

            model.setFilms(repository.getAllFilms());
            clearForm();
        } catch (Exception ex) {
            System.out.print(ex);
            }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
      if (selectedFilm == null) {
            MessageUtils.showErrorMessage("Greška", "Odaberite film za brisanje");
            return;
        }
        boolean confirmed = MessageUtils.showConfirmDialog(
            "Brisanje filma",
            "Jeste li sigurni da želite obrisati \"" + selectedFilm.getNaziv() + "\"?"
        );
        if (!confirmed) return;

        try {
           
            if (selectedFilm.getPutanjaSlike() != null) {
                Files.deleteIfExists(Paths.get(selectedFilm.getPutanjaSlike()));
            }
            
            repository.deleteFilm(selectedFilm.getId());
            repository.removeGlumacFromFilm(selectedFilm.getId());
            
            
            model.setFilms(repository.getAllFilms());
            clearForm();
        } catch (Exception ex) {
            MessageUtils.showErrorMessage("Greška", "Ne mogu obrisati film");
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void tbFilmKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbFilmKeyReleased
        showFilm();
    }//GEN-LAST:event_tbFilmKeyReleased

    private void tbFilmMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbFilmMouseClicked
        showFilm();
    }//GEN-LAST:event_tbFilmMouseClicked
    
    private void setIcon(JLabel label, File file) {
        try {
            label.setIcon(IconUtils.createIcon(file, label.getWidth(), label.getHeight()));
        } catch (IOException ex) {
            Logger.getLogger(FilmPanel.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("Error", "Unable to set icon!");
        }
    }
    
    
    
    
    private void initValidation() {
        validationFields = Arrays.asList( tfNaziv, tfPutanjaSlike);
        errorLabels = Arrays.asList(lblNazivError, lblPutanjaError,lblGlumciError,lblGodinaError,lblRedateljError,lblZanrError);
        
    };

    private void hideErrors() {
        errorLabels.forEach(e -> e.setVisible(false));
    }


    private void initTable() throws Exception {
        tbFilm.setDragEnabled(true);
        tbFilm.setDropMode(DropMode.INSERT_ROWS);
        tbFilm.setTransferHandler(new TransferHandler() {
            @Override
            public int getSourceActions(JComponent c) {
                return MOVE;
            }

            @Override
            protected Transferable createTransferable(JComponent c) {
                JTable table = (JTable) c;
                int row = table.getSelectedRow();
                int column = table.convertRowIndexToModel(row);
                Film film = (Film) ((FilmTableModel) table.getModel()).getValueAt(row,column);
                return (Transferable) new FilmTransferable(film);
            }

            @Override
            public boolean canImport(TransferHandler.TransferSupport support) {
                return support.isDataFlavorSupported(FilmTransferable.FILM_FLAVOR);
            }

            @Override
            public boolean importData(TransferHandler.TransferSupport support) {
                try {
                    Film film = (Film) support.getTransferable().getTransferData(FilmTransferable.FILM_FLAVOR);
                    JTable.DropLocation dl = (JTable.DropLocation) support.getDropLocation();
                    int index = dl.getRow();
                    ((FilmTableModel) tbFilm.getModel()).moveFilm(film, index);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
        });
        tbFilm.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tbFilm.setAutoCreateRowSorter(true);
        tbFilm.setRowHeight(25);
        model = new FilmTableModel(repository.getAllFilms());
        tbFilm.setModel(model);
        
        
        tbFilm.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                showFilm();
                
            }
        });
    }

    private boolean formValid() {
        hideErrors();
        boolean ok = true;

        for (int i = 0; i < validationFields.size(); i++) {
            ok &= !validationFields.get(i).getText().trim().isEmpty();
            errorLabels.get(i).setVisible(validationFields.get(i).getText().trim().isEmpty());
        }
        return ok;
    }

    private void clearForm() {
        hideErrors();
        validationFields.forEach(e -> e.setText(""));
        URL location = getClass().getResource("/assets/no_image.png");
        if (location != null) {
            lblImage.setIcon(new ImageIcon(location));
        } else {
            lblImage.setIcon(null);
            lblImage.setText("No image");
        }

    }

    private String uploadPicture() throws IOException {
        String picturePath = tfPutanjaSlike.getText();
        String ext = picturePath.substring(picturePath.lastIndexOf("."));
        String pictureName = UUID.randomUUID() + ext;
        String localPicturePath = "assets" + File.separator + pictureName;

        FileUtils.copy(picturePath, localPicturePath);
        return localPicturePath;
    }

    private void showFilm() {
         int selectedRow = tbFilm.getSelectedRow();
        if (selectedRow < 0) {
            // ako nema selekcije, očisti formu i kosi selectedFilm
            clearForm();
            selectedFilm = null;
            return;
        }
        // tek kad je stvarno kliknuto na red:
        int rowIndex = tbFilm.convertRowIndexToModel(selectedRow);
        int selectedFilmId = (int) model.getValueAt(rowIndex, 0);
        try {
            Optional<Film> opt = repository.getFilmById(selectedFilmId);
            if (opt.isPresent()) {
                selectedFilm = opt.get();
                fillForm(selectedFilm);
                btnEdit.setEnabled(true);
                btnDelete.setEnabled(true);
            }
        } catch (Exception ex) {
            MessageUtils.showErrorMessage("Error", "Unable to load film");
        }
    }
    
    private void fillForm(Film film) throws DataAccessException {
        
        
        tfNaziv.setText(film.getNaziv());
        spGodina.setValue(film.getGodina());
        taDesc.setText(film.getOpis());

        String selDir = (String) cbRedatelj.getSelectedItem();
            selectedFilm.setRedatelj(redateljiMap.get(selDir));

            // Žanr
            String selGenre = lsZanr.getSelectedValue();
            selectedFilm.setZanr(zanroviMap.get(selGenre));

            // Glumci
            List<Glumac> gl = new ArrayList<>();
            lsGlumci.getSelectedValuesList()
                .forEach(name ->
                    {
                try {
                    String[] details = name.split(" ");
                    repository.getGlumacByName(details[0],details[1])
                            .ifPresent(g -> gl.add((Glumac) g));
                } catch (DataAccessException ex) {
                    Logger.getLogger(FilmPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                );
            
            selectedFilm.setGlumci(gl);
            tfPutanjaSlike.setText(film.getPutanjaSlike());
            setIcon(lblImage, new File(film.getPutanjaSlike()));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnUpload;
    private javax.swing.JComboBox<String> cbRedatelj;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblGlumci;
    private javax.swing.JLabel lblGlumciError;
    private javax.swing.JLabel lblGodina;
    private javax.swing.JLabel lblGodinaError;
    private javax.swing.JLabel lblImage;
    private javax.swing.JLabel lblNaziv;
    private javax.swing.JLabel lblNazivError;
    private javax.swing.JLabel lblOpis;
    private javax.swing.JLabel lblPutanjaError;
    private javax.swing.JLabel lblRedatelj;
    private javax.swing.JLabel lblRedateljError;
    private javax.swing.JLabel lblZanr;
    private javax.swing.JLabel lblZanrError;
    private javax.swing.JList<String> lsGlumci;
    private javax.swing.JList<String> lsZanr;
    private javax.swing.JScrollPane spFilm;
    private javax.swing.JScrollPane spGlumci;
    private javax.swing.JSpinner spGodina;
    private javax.swing.JScrollPane spZanr;
    private javax.swing.JTextArea taDesc;
    private javax.swing.JTable tbFilm;
    private javax.swing.JToolBar tbfBtn;
    private javax.swing.JTextField tfNaziv;
    private javax.swing.JTextField tfPutanjaSlike;
    // End of variables declaration//GEN-END:variables

    private void initGlumci() throws DataAccessException, SQLException {
        DefaultListModel<String> actorsModel = new DefaultListModel<>();
        Object data = repository.getAllGlumci();

        if (data instanceof Glumac[]) {
            Arrays.stream((Glumac[]) data)
                  .forEach(g -> {
                      String name = (g.getIme() + " " + g.getPrezime()).trim().toLowerCase();
                      actorsModel.addElement(name);
                      glumciMap.put(name, g);
                  });
        } else if (data instanceof List) {
            @SuppressWarnings("unchecked")
            List<Glumac> list = (List<Glumac>) data;
            list.forEach(g -> {
                String name = (g.getIme() + " " + g.getPrezime()).trim().toLowerCase();
                actorsModel.addElement(name);
                glumciMap.put(name, g);
            });
        } else {
            throw new IllegalStateException(
                "Očekivao sam Glumac[] ili List<Glumac>, ali dobio: " 
                + data.getClass());
        }

        lsGlumci.setModel(actorsModel);
        lsGlumci.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    }

    public void addGlumciToFilm(int filmId, List<Glumac> glumci) throws DataAccessException {
        glumci.forEach(g -> {
            try {
                repository.addGlumacToFilm(filmId, g);
            } catch (SQLException ex) {
                Logger.getLogger(SqlRepository.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DataAccessException ex) {
                Logger.getLogger(SqlRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

}
