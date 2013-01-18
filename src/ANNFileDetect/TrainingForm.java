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
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class TrainingForm extends javax.swing.JFrame {

    /**
     * Creates new form TrainingForm
     */
    private String ndir = "";
    private SQLiteLib sqlfile = null;

    public TrainingForm() {
        initComponents();

    }

    public void SetNNDir(String nnDir) {
        ndir = nnDir;
    }

    public void SetSQL(SQLiteLib sql) {
        sqlfile = sql;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        NetworkName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        input = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        TrainingDir = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        DescriptionTxt = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("New network name:");

        NetworkName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NetworkNameActionPerformed(evt);
            }
        });

        jLabel2.setText("Input neurons:");

        jLabel3.setText("Training files:");

        TrainingDir.setEditable(false);
        TrainingDir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TrainingDirActionPerformed(evt);
            }
        });

        jButton1.setText("Get file dir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Train!");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel4.setText("(Check console for info)");

        jLabel5.setText("Network description (Optional):");

        DescriptionTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DescriptionTxtActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(229, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(251, 251, 251))
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5))
                .addGap(71, 71, 71)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(NetworkName)
                    .addComponent(input)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(TrainingDir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(DescriptionTxt))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(NetworkName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(TrainingDir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(DescriptionTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jLabel4))
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void NetworkNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NetworkNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NetworkNameActionPerformed

    private void TrainingDirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TrainingDirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TrainingDirActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int parsed = 0;
        String name = NetworkName.getText();
        String path = TrainingDir.getText();
        String description = DescriptionTxt.getText();
        try {
            parsed = Integer.parseInt(input.getText());
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Please enter an integer value as input!", "Integer", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (parsed > 0 && name.length() > 0 && path.length() > 0) {
            this.setEnabled(false);
            NNCreate nnc = new NNCreate(parsed, name, path, ndir, description, sqlfile);
            try {
                nnc.StartTraining();
            } catch (IOException ex) {
                Logger.getLogger(TrainingForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            ANNFiDMain mm = new ANNFiDMain();
            try {
                mm.SetDBandNN(sqlfile.getDBFile(), ndir);
                mm.setVisible(true);
                JOptionPane.showMessageDialog(null, "Network successfully created and tested", "Information", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLiteException ex) {
                Logger.getLogger(TrainingForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Please check the training dir or the file name", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        CountDownLatch ctd = new CountDownLatch(1);
        new Thread(new IntDirBrowser()).start();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void DescriptionTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DescriptionTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DescriptionTxtActionPerformed

    private class IntDirBrowser implements Runnable {

        CountDownLatch ctd;

        public void run() {
            ctd = new CountDownLatch(1);
            SelectFiles sf = new SelectFiles(ctd);
            sf.setVisible(true);
            try {
                ctd.await();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            String tmp = sf.returnSelected();
            sf.dispose();
            TrainingDir.setText(tmp);
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
            java.util.logging.Logger.getLogger(TrainingForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TrainingForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TrainingForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TrainingForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new TrainingForm().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField DescriptionTxt;
    private javax.swing.JTextField NetworkName;
    private javax.swing.JTextField TrainingDir;
    private javax.swing.JTextField input;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    // End of variables declaration//GEN-END:variables
}
