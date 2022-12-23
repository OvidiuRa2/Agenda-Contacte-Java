package agenda;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/*\ @author radoi ovidiu*/
public class Agenda extends javax.swing.JFrame {

    //parola: proiect
    final String parola = "proiect";
    private static boolean cod = false; //verifica daca a fost introdusa parola
    private int indxPozaCurenta = 0;
    private static List<Contact> LstContact = new ArrayList<>();
    private List<Contact> LstFiltrata = new ArrayList<>();
    private DefaultListModel model = new DefaultListModel();

    public Agenda() {
        initComponents();
        SplashScreen ss = new SplashScreen(this, true);
        ss.setVisible(true);
        ModShareWare();
        jlContacte.setModel(model);
        // populateModel();
    }

    void populateModel() {
        LstContact.add(new Contact("mihai", "gheorghe", "2000-05-02", new NrFix("0222321388")));
        LstContact.add(new Contact("ana", "maria", "2003-03-23", new NrMobil("0722561388")));
        LstContact.add(new Contact("mari", "maria", "2003-03-25", new NrMobil("0722561388")));
        LstContact.add(new Contact("mihaela", "ion", "1997-03-25", new NrFix("0333321388")));
        LstContact.add(new Contact("alexandru", "vasile", "2001-10-10", new NrMobil("0722456388")));
        regenereazaModel(LstContact);
    }

    private void ModShareWare() {
        DeschideFisier.hide();
        Salvare.hide();
        File fisierCurent = new File("reclame");
        File[] poze = fisierCurent.listFiles(p -> p.getName().toLowerCase().endsWith(".jfif"));
        List<File> pozeSelectate = Arrays.asList(poze);

        ActionListener task = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (indxPozaCurenta == pozeSelectate.size() - 1) {
                    indxPozaCurenta = 0;
                    File pozaCurenta = pozeSelectate.get(indxPozaCurenta);
                    ImageIcon poza = new ImageIcon(pozaCurenta.getAbsolutePath());
                    lbReclame.setIcon(poza);
                } else {
                    indxPozaCurenta++;
                    File pozaCurenta = pozeSelectate.get(indxPozaCurenta);
                    ImageIcon poza = new ImageIcon(pozaCurenta.getAbsolutePath());
                    lbReclame.setIcon(poza);
                }
            }
        };
        javax.swing.Timer timer = new javax.swing.Timer(1000, task);
        timer.setRepeats(rootPaneCheckingEnabled);
        timer.start();
    }

    void regenereazaModel(List<Contact> c) {
        model.clear();
        if (!c.isEmpty()) {
            for (Contact ct : c) {
                model.addElement(ct);
            }
        }
    }

    private void filtreazaNrFix() {
        LstFiltrata.clear();
        for (Contact ct : LstContact) {
            if (ct.getNrtel().isNrFix()) {
                LstFiltrata.add(ct);
            }
        }
        tfFiltru.setText("");
        regenereazaModel(LstFiltrata);

    }

    private void filtreazaNrMobil() {
        LstFiltrata.clear();
        for (Contact ct : LstContact) {
            if (ct.getNrtel().isNrMobil()) {
                LstFiltrata.add(ct);
            }
        }
        tfFiltru.setText("");
        regenereazaModel(LstFiltrata);

    }

    private void filtreazaNascutiAstazi() {
        LstFiltrata.clear();
        for (Contact ct : LstContact) {
            LocalDate ld = LocalDate.now().withYear(ct.getAnNastere());
            if (ct.getData_nasterii().endsWith(ld.toString())) {
                LstFiltrata.add(ct);
            }
        }
        tfFiltru.setText("");
        regenereazaModel(LstFiltrata);
    }

    private void filtreazaNascutiLunaCurenta() {
        LstFiltrata.clear();
        for (Contact ct : LstContact) {
            LocalDate ld = LocalDate.now();

            if (ct.getLunaNastere() == ld.getMonthValue() && ct.getZiNastere() >= ld.getDayOfMonth()) {
                LstFiltrata.add(ct);
            }
        }
        tfFiltru.setText("");
        regenereazaModel(LstFiltrata);
    }

    private void filtreazaPersonalizat() {
        List<Contact> lsPersonalizat = new ArrayList();

        if (tfFiltru.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nu ati introdus nici-un text in casuta Filtru");
        } else {
            String filtru = tfFiltru.getText();
            for (Contact ct : LstContact) {
                if (ct.getNume().contains(filtru) || ct.getPrenume().contains(filtru) || ct.getNrtel().toString().contains(filtru)) {
                    lsPersonalizat.add(ct);
                }
            }
            regenereazaModel(lsPersonalizat);
        }
    }

    private void ordoneaza(Comparator<Contact> comp) {
        if (LstFiltrata.isEmpty()) {
            Collections.sort(LstContact, comp);
            regenereazaModel(LstContact);
        } else {
            Collections.sort(LstFiltrata, comp);
            regenereazaModel(LstFiltrata);
        }

    }

    class ComparatorDupaVarstaDesc implements Comparator<Contact> {

        @Override
        public int compare(Contact c1, Contact c2) {
            if (c1.getAnNastere() != c2.getAnNastere()) {
                return c1.getAnNastere() - c2.getAnNastere();
            } else if (c1.getLunaNastere() != c2.getLunaNastere()) {
                return c1.getLunaNastere() - c2.getLunaNastere();
            } else {
                return c1.getZiNastere() - c2.getZiNastere();
            }

        }

    }

    class ComparatorDupaVarstaCresc implements Comparator<Contact> {

        @Override
        public int compare(Contact c1, Contact c2) {
            if (c1.getAnNastere() != c2.getAnNastere()) {
                return c2.getAnNastere() - c1.getAnNastere();
            } else if (c1.getLunaNastere() != c2.getLunaNastere()) {
                return c2.getLunaNastere() - c1.getLunaNastere();
            } else {
                return c2.getZiNastere() - c1.getZiNastere();
            }
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jfcDeschide = new javax.swing.JFileChooser();
        jfcSalvare = new javax.swing.JFileChooser();
        jLabel1 = new javax.swing.JLabel();
        cbFiltrare = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        tfFiltru = new javax.swing.JTextField();
        btnFiltreaza = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        cbOrdonare = new javax.swing.JComboBox<>();
        btnOrdoneaza = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jlContacte = new javax.swing.JList<>();
        btnAdaugaContact = new javax.swing.JButton();
        btnSterge = new javax.swing.JButton();
        btnEditeaza = new javax.swing.JButton();
        lbReclame = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        DeschideFisier = new javax.swing.JMenuItem();
        Salvare = new javax.swing.JMenuItem();
        Iesire = new javax.swing.JMenuItem();
        edit = new javax.swing.JMenu();
        Inregistrare = new javax.swing.JMenuItem();
        despre = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(400, 150));

        jLabel1.setText("Filtrare:");

        cbFiltrare.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NrFix", "NrMobil", "NascutiAstazi", "NascutiLunaCurenta", "Personalizat", "AnulareFiltre" }));
        cbFiltrare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFiltrareActionPerformed(evt);
            }
        });

        jLabel2.setText("filtru:");

        btnFiltreaza.setText("Filtreaza");
        btnFiltreaza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiltreazaActionPerformed(evt);
            }
        });

        jLabel3.setText("Ordonare:");

        cbOrdonare.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "dupaNume", "dupaPrenume", "dupaVarstaCrescator", "dupaVarstaDescrescator" }));

        btnOrdoneaza.setText("Ordoneaza");
        btnOrdoneaza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrdoneazaActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(jlContacte);

        btnAdaugaContact.setText("Adauga Contact");
        btnAdaugaContact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdaugaContactActionPerformed(evt);
            }
        });

        btnSterge.setText("Sterge Contact Selectat");
        btnSterge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStergeActionPerformed(evt);
            }
        });

        btnEditeaza.setText("Editeaza contact selectat");
        btnEditeaza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditeazaActionPerformed(evt);
            }
        });

        lbReclame.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jMenu1.setText("File");

        DeschideFisier.setText("Deschidere");
        DeschideFisier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeschideFisierActionPerformed(evt);
            }
        });
        jMenu1.add(DeschideFisier);

        Salvare.setText("Salvare");
        Salvare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalvareActionPerformed(evt);
            }
        });
        jMenu1.add(Salvare);

        Iesire.setText("Iesire");
        Iesire.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IesireActionPerformed(evt);
            }
        });
        jMenu1.add(Iesire);

        jMenuBar1.add(jMenu1);

        edit.setText("Edit");

        Inregistrare.setText("Inregistrare");
        Inregistrare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InregistrareActionPerformed(evt);
            }
        });
        edit.add(Inregistrare);

        despre.setText("Despre");
        despre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                despreActionPerformed(evt);
            }
        });
        edit.add(despre);

        jMenuBar1.add(edit);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAdaugaContact, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSterge, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditeaza, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cbFiltrare, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfFiltru, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cbOrdonare, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnFiltreaza, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnOrdoneaza, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(8, 8, 8))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lbReclame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnFiltreaza)
                        .addComponent(tfFiltru, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbFiltrare, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbOrdonare, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnOrdoneaza))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnEditeaza, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSterge, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAdaugaContact, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbReclame, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SalvareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalvareActionPerformed
        //se vor putea salva doar documente txt
        //salvarea se realizeaza intr-un fisier deja existent de tipul txt, numit Salvare care se afla in arhiva 
        if (jfcSalvare.showOpenDialog(this) == JFileChooser.OPEN_DIALOG) {
            File f = jfcSalvare.getSelectedFile();
            if (!f.isFile()) {
                JOptionPane.showMessageDialog(this, "Nu ati selectat un fisier");
            } else if (!f.canRead() || !f.canExecute()) {
                JOptionPane.showConfirmDialog(this, "Fisierul selectat nu poate fi citit sau executat!");
            } else if (!f.toString().endsWith(".txt")) {
                JOptionPane.showConfirmDialog(this, "Nu ai selectat un fisier txt");
            } else {
                salvareFisier(f);
                JOptionPane.showConfirmDialog(this, "Salvarea s-a efecutat cu succes");
            }
            ActionListener taskSalvare = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    salvareFisier(f);
                }
            };
            javax.swing.Timer salvare = new javax.swing.Timer(6000, taskSalvare);
            salvare.start();
        }
    }//GEN-LAST:event_SalvareActionPerformed

    private void salvareFisier(File f) {
        FileWriter write = null;
        try {
            write = new FileWriter(f);
            for (Contact c : LstFiltrata) {
                write.append(c.toString());
                write.append("\n");
            }
            write.flush();
            write.close();

        } catch (IOException ex) {
            JOptionPane.showConfirmDialog(this, "Nu s-a putut salva");

        }
    }

    private void IesireActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IesireActionPerformed
        int a = JOptionPane.showConfirmDialog(null, "Doriti sa parasiti aplicatia?", "Confirmare", JOptionPane.YES_NO_OPTION, 2);
        if (a == 0) {
            System.exit(0);
        }
    }//GEN-LAST:event_IesireActionPerformed

    private void InregistrareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InregistrareActionPerformed
        String pw = JOptionPane.showInputDialog(this, "introduceti parola:");
        if (pw.equals(parola)) {
            cod = true;
            DeschideFisier.setVisible(true);
            Salvare.setVisible(true);
            edit.setVisible(true);
            despre.setVisible(true);
            lbReclame.hide();
            Inregistrare.hide();
        } else {
            JOptionPane.showMessageDialog(this, "Parola este gresita!");
        }
    }//GEN-LAST:event_InregistrareActionPerformed

    private void cbFiltrareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFiltrareActionPerformed
    }//GEN-LAST:event_cbFiltrareActionPerformed

    private void btnFiltreazaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltreazaActionPerformed
        if (cod == true) {
            String FiltruSelectat = cbFiltrare.getSelectedItem().toString();
            if (FiltruSelectat.equalsIgnoreCase("nrfix")) {
                filtreazaNrFix();
            } else if (FiltruSelectat.equalsIgnoreCase("nrmobil")) {
                filtreazaNrMobil();
            } else if (FiltruSelectat.equalsIgnoreCase("nascutiastazi")) {
                filtreazaNascutiAstazi();
            } else if (FiltruSelectat.equalsIgnoreCase("nascutilunacurenta")) {
                filtreazaNascutiLunaCurenta();
            } else if (FiltruSelectat.equalsIgnoreCase("personalizat")) {
                filtreazaPersonalizat();
            } else {
                tfFiltru.setText("");
                regenereazaModel(LstContact);
                LstFiltrata.clear();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Trebuie introdusa  parola", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnFiltreazaActionPerformed

    private void btnOrdoneazaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrdoneazaActionPerformed
        if (cod == true) {
            String ordineSelect = cbOrdonare.getSelectedItem().toString();
            if (ordineSelect.equalsIgnoreCase("dupanume")) {
                Comparator<Contact> cNume = (p1, p2) -> p1.getNume().compareToIgnoreCase(p2.getNume());
                ordoneaza(cNume);
            } else if (ordineSelect.equalsIgnoreCase("dupaprenume")) {
                Comparator<Contact> cPrenume = (p1, p2) -> p1.getPrenume().compareToIgnoreCase(p2.getPrenume());
                ordoneaza(cPrenume);
            } else if (ordineSelect.equalsIgnoreCase("dupavarstacrescator")) {
                Comparator<Contact> cVarstaCresc = new ComparatorDupaVarstaCresc();
                ordoneaza(cVarstaCresc);

            } else {
                Comparator<Contact> cVarstaDesc = new ComparatorDupaVarstaDesc();
                ordoneaza(cVarstaDesc);

            }
        } else {
            JOptionPane.showMessageDialog(this, "Trebuie introdusa  parola", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnOrdoneazaActionPerformed

    private void btnAdaugaContactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdaugaContactActionPerformed
        if (cod == true) {
            AdaugaContact dlgadd = new AdaugaContact(this, true);
            dlgadd.setVisible(true);
            boolean duplicat = false;
            if (dlgadd.getContactToAdd() != null) {
                if (dlgadd.getContactToAdd().getLunaNastere() == LocalDate.now().getMonthValue() && dlgadd.getContactToAdd().getZiNastere() == LocalDate.now().getDayOfMonth()) {
                    JOptionPane.showMessageDialog(null, "La multi ani!!");
                }

                for (Contact c : LstContact) {
                    if (c.equals(dlgadd.getContactToAdd())) {
                        duplicat = true;
                    }
                }
                if (duplicat == false) {
                    LstContact.add(dlgadd.getContactToAdd());
                    regenereazaModel(LstContact);
                } else {
                    JOptionPane.showMessageDialog(this, "Persoana introdusa deja exista.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Trebuie introdusa  parola", "Error", JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_btnAdaugaContactActionPerformed

    private void btnStergeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStergeActionPerformed
        if (cod == true) {
            int idxsel = jlContacte.getSelectedIndex();
            if (idxsel >= 0) {
                int a = JOptionPane.showConfirmDialog(null, "Doriti sa stergeti contactul " + LstContact.get(idxsel) + "?", "Confirmare", JOptionPane.YES_NO_OPTION, 2);
                if (a == 0) {
                    LstContact.remove(idxsel);
                    regenereazaModel(LstContact);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Nu a fost selectat nici-un contact", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Trebuie introdusa  parola", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnStergeActionPerformed

    private void btnEditeazaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditeazaActionPerformed
        if (cod == true) {
            int idxsel = jlContacte.getSelectedIndex();
            if (idxsel >= 0) {
                Contact CSelect = LstContact.get(idxsel);
                editeazaContact dlgedit = new editeazaContact(this, true);
                dlgedit.setVisible(true);
                if (!dlgedit.getTfNume().equals("")) {
                    CSelect.setNume(dlgedit.getTfNume());
                }
                if (!dlgedit.getTfPrenume().equals("")) {
                    CSelect.setPrenume(dlgedit.getTfPrenume());
                }
                if (!dlgedit.getTfDataNastere().equals("")) {
                    CSelect.setData_nasterii(dlgedit.getTfDataNastere());
                }
                if (!dlgedit.getTelefon().equals("")) {
                    if (dlgedit.getTelefon().startsWith("07")) {
                        CSelect.setNrtel(new NrMobil(dlgedit.getTelefon()));
                    } else {
                        CSelect.setNrtel(new NrFix(dlgedit.getTelefon()));
                    }
                }
                regenereazaModel(LstContact);
            } else {
                JOptionPane.showMessageDialog(this, "Nu a fost selectat nici-un contact", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Trebuie introdusa  parola", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEditeazaActionPerformed

    private void DeschideFisierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeschideFisierActionPerformed
        //Se vor putea deschide doar fisier txt
        if (jfcDeschide.showOpenDialog(this) == JFileChooser.OPEN_DIALOG) {
            File f = jfcDeschide.getSelectedFile();
            if (!f.isFile()) {
                JOptionPane.showMessageDialog(this, "Nu ati selectat un fisier");
            } else if (!f.canRead() || !f.canExecute()) {
                JOptionPane.showConfirmDialog(this, "Fisierul selectat nu poate fi citit sau executat!");
            } else {
                try {
                    FileReader fr = new FileReader(f);
                    BufferedReader br = new BufferedReader(fr);
                    String contact;
                    while ((contact = br.readLine()) != null) {
                        String[] elemen = contact.split(",");
                        if (elemen[3].startsWith("07")) {
                            LstContact.add(new Contact(elemen[0], elemen[1], elemen[2], new NrMobil(elemen[3])));
                        } else {
                            LstContact.add(new Contact(elemen[0], elemen[1], elemen[2], new NrFix(elemen[3])));
                        }
                    }
                    br.close();
                    fr.close();
                    regenereazaModel(LstContact);
                    Sarbatoriti sarb = new Sarbatoriti(this, true);
                    sarb.setVisible(true);
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                }
            }
        }
    }//GEN-LAST:event_DeschideFisierActionPerformed

    private void despreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_despreActionPerformed
        Despre dlgdesp = new Despre(this, true);
        dlgdesp.setVisible(true);
    }//GEN-LAST:event_despreActionPerformed

    public static List<Contact> getLstContact() {
        return LstContact;
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Agenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Agenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Agenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Agenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Agenda().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem DeschideFisier;
    private javax.swing.JMenuItem Iesire;
    private javax.swing.JMenuItem Inregistrare;
    private javax.swing.JMenuItem Salvare;
    private javax.swing.JButton btnAdaugaContact;
    private javax.swing.JButton btnEditeaza;
    private javax.swing.JButton btnFiltreaza;
    private javax.swing.JButton btnOrdoneaza;
    private javax.swing.JButton btnSterge;
    private javax.swing.JComboBox<String> cbFiltrare;
    private javax.swing.JComboBox<String> cbOrdonare;
    private javax.swing.JMenuItem despre;
    private javax.swing.JMenu edit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JFileChooser jfcDeschide;
    private javax.swing.JFileChooser jfcSalvare;
    private javax.swing.JList<String> jlContacte;
    private javax.swing.JLabel lbReclame;
    private javax.swing.JTextField tfFiltru;
    // End of variables declaration//GEN-END:variables
}
