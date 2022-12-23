package agenda;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

/* @author radoi ovidiu*/
public class AdaugaContact extends javax.swing.JDialog {

    private Contact ContactToAdd = null;

    public AdaugaContact(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    private boolean VerificareContact() {
        String nume = tfNume.getText().trim();
        String prenume = tfprenume.getText().trim();
        String dataNastere = tfdatan.getText().trim();
        String nrTel = tftelefom.getText().trim();

        if (tfNume.getText().isEmpty() || tfdatan.getText().isEmpty() || tfprenume.getText().isEmpty() || tftelefom.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nu ati completat toate campurile");
            return false;
        }
        if (nume.length() <= 2) {
            JOptionPane.showMessageDialog(null, "Numele nu este valid");
            return false;
        }
        if (prenume.length() <= 2) {
            JOptionPane.showMessageDialog(null, "Prenumele nu este valid");
            return false;
        }
        if (DataValida(dataNastere) == false) //in format AAAA-LL-ZZ
        {
            JOptionPane.showMessageDialog(null, "Data de nastere nu este in format AAAA-LL-ZZ");
            return false;
        }

        if (nrTel.length() != 10) {
            JOptionPane.showMessageDialog(null, "Numarul de telefon nu este valid");
            return false;
        } else if (nrTel.startsWith("07") || nrTel.startsWith("03") || nrTel.startsWith("02")) {
        } else {
            return false;
        }

        return true;
    }

    private void clearTF() {
        tfNume.setText("");
        tfdatan.setText("");
        tfprenume.setText("");
        tftelefom.setText("");
    }

    private boolean DataValida(String data) {
        // in format AAAA-LL-ZZ

        String[] elem = data.split("-");
        if (elem.length != 3) {
            return false;
        }
        int an = Integer.parseInt(elem[0]);
        int luna = Integer.parseInt(elem[1]);
        int zi = Integer.parseInt(elem[2]);

        if (zi <= 0 || zi > StringUtils.zilePerLuna(an, luna)) {
            return false;
        }
        if (luna < 1 || luna > 12) {
            return false;
        }
        if (an <= 0) {
            return false;
        }
        if (luna < 10 && zi < 10) {
            tfdatan.setText(an + "-0" + luna + "-0" + zi);
        }
        if (luna < 10 && zi > 9) {
            tfdatan.setText(an + "-0" + luna + "-" + zi);
        }
        if (luna > 9 && zi < 10) {
            tfdatan.setText(an + "-" + luna + "-0" + zi);
        }
        return true;

    }

    public Contact getContactToAdd() {
        return ContactToAdd;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        tfNume = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tfprenume = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tfdatan = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        tftelefom = new javax.swing.JTextField();
        btnAdauga = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLocation(new java.awt.Point(400, 300));

        jLabel1.setText("Nume:");

        jLabel2.setText("Prenume:");

        jLabel3.setText("DataNasterii:");

        jLabel4.setText("Telefon:");

        btnAdauga.setText("Adauga");
        btnAdauga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdaugaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAdauga, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfNume, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfprenume, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfdatan, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tftelefom, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tfNume, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(tfprenume, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(tfdatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(tftelefom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAdauga, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                .addGap(5, 5, 5))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAdaugaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdaugaActionPerformed
        if (VerificareContact() == true) {
            if (tftelefom.getText().startsWith("07")) {
                ContactToAdd = new Contact(tfNume.getText(), tfprenume.getText(), tfdatan.getText(), new NrMobil(tftelefom.getText()));
            } else {
                ContactToAdd = new Contact(tfNume.getText(), tfprenume.getText(), tfdatan.getText(), new NrFix(tftelefom.getText()));
            }
            dispose();
        } else {
            clearTF();
        }
    }//GEN-LAST:event_btnAdaugaActionPerformed

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
            java.util.logging.Logger.getLogger(AdaugaContact.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdaugaContact.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdaugaContact.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdaugaContact.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AdaugaContact dialog = new AdaugaContact(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdauga;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField tfNume;
    private javax.swing.JTextField tfdatan;
    private javax.swing.JTextField tfprenume;
    private javax.swing.JTextField tftelefom;
    // End of variables declaration//GEN-END:variables
}
