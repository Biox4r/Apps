/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package valentic.registracija.view;

import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import valentic.registracija.controller.ObradaZaposlenika;
import valentic.registracija.model.Zaposlenik;
import valentic.registracija.utility.Pomocno;
import valentic.registracija.view.GlavniProzor;

/**
 *
 * @author Basic
 */
public class ZaposleniciLogin extends javax.swing.JFrame {

    private final ObradaZaposlenika obr;

    /**
     * Creates new form LoginProzor
     */
    public ZaposleniciLogin() {
        initComponents();

        obr = new ObradaZaposlenika();
        setLocationRelativeTo(null);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        login = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lozinka = new javax.swing.JPasswordField();
        korisnickoIme = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        login.setText("Login");
        login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Login", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Aharoni", 1, 12), new java.awt.Color(255, 0, 0))); // NOI18N

        jLabel2.setText("Lozinka");

        lozinka.setText("d");
        lozinka.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                lozinkaKeyPressed(evt);
            }
        });

        korisnickoIme.setText("Demon24");
        korisnickoIme.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                korisnickoImeKeyPressed(evt);
            }
        });

        jLabel1.setText("Korisničko Ime");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lozinka, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                    .addComponent(korisnickoIme))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(korisnickoIme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lozinka, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(190, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(login, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(54, 54, 54)
                .addComponent(login)
                .addGap(38, 38, 38))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginActionPerformed

        if (korisnickoIme.getText().trim().length() == 0 || lozinka.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Niste pravilno unjeli korisničko ime ili lozinku ", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Zaposlenik z = obr.getZaposlenici(korisnickoIme.getText(), Pomocno.generirajMD5(lozinka.getPassword()));

        if (z == null) {
            JOptionPane.showMessageDialog(rootPane, "Nepostoji takav zaposlenik! ", "Greška", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Uspješan unos!! ", "Poruka", JOptionPane.PLAIN_MESSAGE);

            new GlavniProzor(z).setVisible(true);

            dispose();
        }


    }//GEN-LAST:event_loginActionPerformed

    private void korisnickoImeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_korisnickoImeKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (this.lozinka.getPassword().length > 0) {
                loginActionPerformed(null);
            } else {
                this.lozinka.requestFocus();
            }
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_korisnickoImeKeyPressed

    private void lozinkaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lozinkaKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (this.korisnickoIme.getText().length() > 0) {
                loginActionPerformed(null);
            } else {
                this.korisnickoIme.requestFocus();
            }
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_lozinkaKeyPressed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField korisnickoIme;
    private javax.swing.JButton login;
    private javax.swing.JPasswordField lozinka;
    // End of variables declaration//GEN-END:variables

}
