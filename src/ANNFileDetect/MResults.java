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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MResults extends javax.swing.JFrame {

    SQLiteLib sql;
    String currSelected;
    HashMap filesVals;
    ActionListener al;

    /**
     * Creates new form MResults
     */
    public MResults(SQLiteLib sl) {
        this.setVisible(true);
        sql = sl;
        initComponents();
        String[] ftypes = sql.GetUniqueFTypeResults();
        for (String str : ftypes) {
            FTypesCombo.addItem(str);
        }
        runStuff();
    }
    
    public void runStuff() {
                DefaultListModel mdl = new DefaultListModel();
        String[] tmp = sql.GetAllFinalResults();
        for (int i = 0; i < tmp.length; i++) {
            mdl.add(i, tmp[i].toString());
        }
        EList.setModel(mdl);

        EList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                if (evt.getValueIsAdjusting()) {
                    return;
                } else {

                    FTypeCB.removeActionListener(al);
                    String tmp = EList.getSelectedValue().toString();
                    String[] fp = tmp.split(" =   ");
                    
                    typeLBL.setText("Modifying type: " + fp[0]);
                    if (fp.length > 1) {
                    String[] vals = fp[1].split(",");
                    ArrayList tal = new ArrayList();
                    try {
                        FTypeCB.removeAllItems();
                    } catch (Exception ex) {
                        System.out.println(ex.toString());
                    }
                    filesVals = new HashMap();
                    for (int i = 0; i < vals.length; i++) {
                        String[] tvals = vals[i].split("( > | < )");
                        //tal.add(i, tvals[0]);
                        FTypeCB.addItem(vals[i]);
                        double tmpdbl = Double.parseDouble(tvals[1]);
                        if (i == 0) {
                            Slider.setValue((int) (tmpdbl * 10000));
                            if (vals[i].contains("<")) {
                                mmCB.setSelectedIndex(1);
                            } else {
                                mmCB.setSelectedIndex(0);

                            }
                        }
                        String toPut = vals[i].split(" ")[1] + vals[i].split(" ")[2];
                        if (!filesVals.containsKey(tvals[0])) {
                            filesVals.put(tvals[0], toPut);
                        }
                        else {
                            filesVals.put(tvals[0], filesVals.get(tvals[0]) + "::" + toPut);
                        }
                    }
                    }
                    else {
                        FTypeCB.removeAllItems();
                        FTypeCB.addItem("No items for this type");
                    }
                    FTypeCB.repaint();
                    al = new ActionListener() {
                        public void actionPerformed(ActionEvent event) {
                            String toSearch = FTypeCB.getSelectedItem().toString();
                            String[] vals = toSearch.split(" ");
                            String mm = vals[1];
                            String val = vals[2];
                            mmCB.setSelectedItem(mm);
                            Slider.setValue((int) (Double.parseDouble(val) * 10000));
                        }
                    };

                    FTypeCB.addActionListener(al);

                }
            }
        });

        Slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                double val = ((double) Slider.getValue()) / 10000;
                Vals.setText(String.valueOf(val));
                repaint();
            }
        });


    }
    
    

    public void run() {
        this.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        EList = new javax.swing.JList();
        Slider = new javax.swing.JSlider();
        FTypeCB = new javax.swing.JComboBox();
        SetBTN = new javax.swing.JButton();
        Vals = new javax.swing.JTextField();
        mmCB = new javax.swing.JComboBox();
        AddBTN = new javax.swing.JButton();
        NewTshBTN = new javax.swing.JButton();
        RemoveBTN = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        typeLBL = new javax.swing.JLabel();
        FTypesCombo = new javax.swing.JComboBox();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(3, 0), new java.awt.Dimension(3, 0), new java.awt.Dimension(3, 32767));
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setViewportView(EList);

        Slider.setMaximum(30000);

        FTypeCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select a file type on top" }));

        SetBTN.setText("Set");
        SetBTN.setToolTipText("");
        SetBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SetBTNActionPerformed(evt);
            }
        });

        Vals.setText("0");

        mmCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { ">", "<" }));

        AddBTN.setText("New file type");
        AddBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddBTNActionPerformed(evt);
            }
        });

        NewTshBTN.setText("New threshold");
        NewTshBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewTshBTNActionPerformed(evt);
            }
        });

        RemoveBTN.setText("Remove file type");
        RemoveBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveBTNActionPerformed(evt);
            }
        });

        jButton1.setText("Remove threshold");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        typeLBL.setText("Modifying type:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(124, 124, 124))
                                    .addComponent(FTypeCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addComponent(mmCB, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(32, 32, 32)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(FTypesCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(NewTshBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Slider, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(Vals, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(SetBTN))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(AddBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(RemoveBTN))
                                    .addComponent(typeLBL)))
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 692, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AddBTN)
                    .addComponent(RemoveBTN))
                .addGap(15, 15, 15)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(typeLBL)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(FTypeCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mmCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Vals, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SetBTN))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Slider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(NewTshBTN))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FTypesCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void NewTshBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewTshBTNActionPerformed
        FTypeCB.addItem(FTypesCombo.getSelectedItem().toString() + " > 0");
        FTypeCB.setSelectedIndex(FTypeCB.getItemCount() - 1);
    }//GEN-LAST:event_NewTshBTNActionPerformed

    private void AddBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddBTNActionPerformed
        String fType = JOptionPane.showInputDialog(null, "Enter the file type result", "File type", 1);
        boolean outcome = sql.InsertNewFinalResultsType(fType);
        if (outcome) {
            DefaultListModel mdl = (DefaultListModel) EList.getModel();
            mdl.add(mdl.getSize(), fType.toString());
            EList.setModel(mdl);
        }
    }//GEN-LAST:event_AddBTNActionPerformed

    private void RemoveBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoveBTNActionPerformed
        String[] fp = EList.getSelectedValue().toString().split(" =   ");
        boolean outcome = false;
        if (fp.length > 1) {
        outcome = sql.DeleteFinalResultsType(fp[0], fp[1]);
        }
        else {
            outcome = sql.DeleteFinalResultsType(fp[0], "");
        }
        if (outcome) {
            runStuff();
        }
        FTypeCB.removeAllItems();
    }//GEN-LAST:event_RemoveBTNActionPerformed

    private void SetBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SetBTNActionPerformed
        String selected = FTypeCB.getSelectedItem().toString();
        String mm = mmCB.getSelectedItem().toString();
        String val = Vals.getText();
        FTypeCB.addItem(selected.split(" ")[0] + " " + mm + " " + val);
        FTypeCB.removeItem(selected);
        saveItems();
    }//GEN-LAST:event_SetBTNActionPerformed

    public void saveItems() {
        String[] oldStuff = EList.getSelectedValue().toString().split(" =   ");
        String tmpFill = "";
        for (int i = 0; i < FTypeCB.getItemCount(); i++) {
            if (tmpFill.length() == 0) {
                tmpFill = FTypeCB.getItemAt(i).toString();
            }
            else {
                tmpFill = tmpFill + "," + FTypeCB.getItemAt(i).toString();
            }
        }
        sql.DeleteFinalResultsType(oldStuff[0], oldStuff[1]);
        sql.InsertNewFinalResultsType(oldStuff[0], tmpFill);
        runStuff();
        
    }
    
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String selected = FTypeCB.getSelectedItem().toString();
        FTypeCB.removeItem(selected);
    }//GEN-LAST:event_jButton1ActionPerformed
    /*
     private class MListGet implements Runnable {
     CountDownLatch ctd;

     public void run() {
     Object tmp = EList.getSelectedValue();
     String out = tmp.toString();
     String[] tmpar = out.split(" =   ");
     String str = JOptionPane.showInputDialog(null, "Enter values for " + tmpar[0], "File modification", JOptionPane.QUESTION_MESSAGE, null, null, tmpar[1]).toString();
     }
     }
     */

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
            java.util.logging.Logger.getLogger(MResults.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MResults.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MResults.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MResults.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new MResults().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddBTN;
    private javax.swing.JList EList;
    private javax.swing.JComboBox FTypeCB;
    private javax.swing.JComboBox FTypesCombo;
    private javax.swing.JButton NewTshBTN;
    private javax.swing.JButton RemoveBTN;
    private javax.swing.JButton SetBTN;
    private javax.swing.JSlider Slider;
    private javax.swing.JTextField Vals;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JComboBox mmCB;
    private javax.swing.JLabel typeLBL;
    // End of variables declaration//GEN-END:variables
}
