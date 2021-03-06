/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package valentic.registracija.zaposlenici.view;



import valentic.registracija.controller.ObradaZaposlenika;
import valentic.registracija.model.Zaposlenik;
import valentic.registracija.utility.Pomocno;

/**
 *
 * @author Profesor
 */
public class PromjenaLozinke extends javax.swing.JFrame {
private Zaposlenik zaposlenik;
    /**
     * Creates new form PromjenaLozinke
     */
    public PromjenaLozinke(Zaposlenik zaposlenik) {
        initComponents();
        this.zaposlenik= zaposlenik;
        poruka.setText(poruka.getText() +zaposlenik.getImePrezime());
        setLocationRelativeTo(null);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        poruka = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        stara = new javax.swing.JPasswordField();
        nova = new javax.swing.JPasswordField();
        ponovoNova = new javax.swing.JPasswordField();
        promjeni = new javax.swing.JButton();
        porukaPromjena = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Promjena lozinke");
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);

        poruka.setText("Promjena lozinke za: ");

        jLabel1.setText("Stara lozinka");

        jLabel2.setText("Nova lozinka");

        jLabel3.setText("Ponovite novu lozinku");

        promjeni.setIcon(new javax.swing.ImageIcon(getClass().getResource("/valentic/icons/promjena.png"))); // NOI18N
        promjeni.setText("Promjeni");
        promjeni.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        promjeni.setIconTextGap(24);
        promjeni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                promjeniActionPerformed(evt);
            }
        });

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/valentic/icons/zatvori.png"))); // NOI18N
        jButton9.setText("Zatvori prozor");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/valentic/icons/promjena2.png"))); // NOI18N
        jLabel4.setText("Promjena lozinke");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(porukaPromjena)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(poruka)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(nova, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                            .addComponent(stara, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ponovoNova))
                        .addGap(18, 18, 18)
                        .addComponent(promjeni, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(189, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(171, 171, 171))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(poruka)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(stara, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(21, 21, 21)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(nova, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(promjeni, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(ponovoNova, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                        .addComponent(jButton9)
                        .addGap(24, 24, 24))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(233, 233, 233)
                        .addComponent(porukaPromjena)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void promjeniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_promjeniActionPerformed
       porukaPromjena.setText("");
        if(stara.getPassword().length==0 ||
                nova.getPassword().length==0 ||
                ponovoNova.getPassword().length==0 ){
            porukaPromjena.setText("Obavezan unos sve tri vrijednosti!");
            return;
        }
        
        if(!zaposlenik.getLozinka().
                equals(Pomocno.
                generirajMD5(stara.getPassword()))){
            porukaPromjena.setText("Stara lozinka ne odgovara!");
            return;
        }
        
        if(!java.util.Arrays.equals(nova.getPassword(), ponovoNova.getPassword())){
            porukaPromjena.setText("Nova lozinka ne odgovara ponovljenoj vrijednosti!");
            return;
        }
        
        if(java.util.Arrays.equals(nova.getPassword(), stara.getPassword())){
            porukaPromjena.setText("Nova lozinka ne može biti identična staroj!");
            return;
        }
        
        //ovdje još mogu doći kontrole
        //kvalitete lozinke
        //min 8 znakova, mala velika slova, brojevi i interpukcijski znakovi
        
        //sve ok za promjeniti u bazi
       zaposlenik.setLozinka(Pomocno.generirajMD5(nova.getPassword()));
        
        ObradaZaposlenika op = new ObradaZaposlenika();
        if(op.promjeni(zaposlenik)){
         porukaPromjena.setText("Lozinka uspješno promjenjena!");   
        }
        else{
            porukaPromjena.setText("Došlo je do pogreške, lozinka nije promjenjena!");
        }
        
    }//GEN-LAST:event_promjeniActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed

        dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton9ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPasswordField nova;
    private javax.swing.JPasswordField ponovoNova;
    private javax.swing.JLabel poruka;
    private javax.swing.JLabel porukaPromjena;
    private javax.swing.JButton promjeni;
    private javax.swing.JPasswordField stara;
    // End of variables declaration//GEN-END:variables

    
}
