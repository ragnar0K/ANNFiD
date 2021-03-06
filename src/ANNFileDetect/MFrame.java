/*
 * Copyright 2013 ragnar0k@fabytes.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ANNFileDetect;

/**
 *
 * @author ragnar0k@fabytes.com
 */

import com.almworks.sqlite4java.SQLiteException;
import java.io.FileFilter;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class MFrame extends javax.swing.JFrame {
    static JTextField jt;
    static String sqlite;
    static String nnfolder;
    /**
     * Creates new form MFrame
     */
    public MFrame() {
        initComponents();
        SQLiteTxt.setText("ANNDB.sqlite");
        NNTxt.setText("nnets");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        SQliteBTN = new javax.swing.JButton();
        SQLiteTxt = new javax.swing.JTextField();
        NNBtn = new javax.swing.JButton();
        NNTxt = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        SQliteBTN.setText("Chose sqlite DB");
        SQliteBTN.setToolTipText("");
        SQliteBTN.setActionCommand("jbutton1");
        SQliteBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SQliteBTNActionPerformed(evt);
            }
        });

        SQLiteTxt.setEditable(false);
        SQLiteTxt.setToolTipText("");

        NNBtn.setText("Chose Network Dir");
        NNBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NNBtnActionPerformed(evt);
            }
        });

        NNTxt.setEditable(false);

        jLabel1.setText("Welcome to Manniac!");
        jLabel1.setToolTipText("");

        jButton1.setText("Start");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(282, 282, 282)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(SQliteBTN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(NNBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 159, Short.MAX_VALUE))
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(SQLiteTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
                            .addComponent(NNTxt))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 153, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(143, 143, 143))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SQliteBTN)
                    .addComponent(SQLiteTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NNBtn)
                    .addComponent(NNTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SQliteBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SQliteBTNActionPerformed
        //
        jt = SQLiteTxt;
        CountDownLatch ctd = new CountDownLatch(1);
            new Thread(new StartFileBrowser()).start();
       
    }//GEN-LAST:event_SQliteBTNActionPerformed

    private void NNBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NNBtnActionPerformed
        jt = NNTxt;
        CountDownLatch ctd = new CountDownLatch(1);
            new Thread(new StartDirBrowser()).start();
    }//GEN-LAST:event_NNBtnActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ANNFiDMain mm = new ANNFiDMain();
        mm.setVisible(true);
        try {
            mm.SetDBandNN(SQLiteTxt.getText(), NNTxt.getText());
        } catch (SQLiteException ex) {
            Logger.getLogger(MFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private static class StartDirBrowser implements Runnable
    {
        CountDownLatch ctd;
        public void run()
        {
            ctd = new CountDownLatch(1);
            SelectFiles sf = new SelectFiles(ctd);
            sf.setVisible(true);
            try {
                ctd.await();
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            String tmp = sf.returnSelected();
            sf.dispose();
            jt.setText(tmp);
        }

    }
    
       private static class StartFileBrowser implements Runnable
    {
        CountDownLatch ctd;
        public void run()
        {
            ctd = new CountDownLatch(1);
            SelectFiles sf = new SelectFiles("SQLite file (*.sqlite)", "sqlite", ctd);
            sf.setVisible(true);
            try {
                ctd.await();
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            String tmp = sf.returnSelected();
            sf.dispose();
            jt.setText(tmp);
        }

    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new MFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton NNBtn;
    private javax.swing.JTextField NNTxt;
    private javax.swing.JTextField SQLiteTxt;
    private javax.swing.JButton SQliteBTN;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
