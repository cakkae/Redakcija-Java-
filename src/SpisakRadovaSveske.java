
import includes.DatabaseConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Adnan
 */
public class SpisakRadovaSveske extends javax.swing.JFrame {

    /**
     * Creates new form MainMenu
     */
    private Connection conn;
    String rb_sveske, godina_izdanja, casopis_id;
     
    public SpisakRadovaSveske() {
        initComponents();
        DatabaseConnection dbc = DatabaseConnection.getDatabaseConnection();
            conn = dbc.getConnection();
            //setTableData();
            try {
                String queryString = "SELECT DISTINCT(naziv) from casopisi";
                Statement smt = conn.createStatement();
                ResultSet resultSet = smt.executeQuery(queryString);
                ResultSetMetaData metaData = resultSet.getMetaData();
                int colCount = metaData.getColumnCount();
                while(resultSet.next())
                {
                    cbCasopis.addItem(resultSet.getString(1));
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
            
             try {
                String queryString = "SELECT rb_godine, rb_sveske from radovi GROUP BY casopis_id";
                Statement smt = conn.createStatement();
                ResultSet resultSet = smt.executeQuery(queryString);
                ResultSetMetaData metaData = resultSet.getMetaData();
                int colCount = metaData.getColumnCount();
                while(resultSet.next())
                {
                    cbGodinaIzdanja.addItem(resultSet.getString("rb_godine"));
                    cbSveska.addItem(resultSet.getString("rb_sveske"));
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
           
    }
    
     int idCasopisa(String naziv)
    {
        int id = 0;
        try {
            String queryString = "SELECT casopis_id from casopisi where casopisi.naziv = '"+naziv+"'";
            Statement smt = conn.createStatement();
            ResultSet resultSet = smt.executeQuery(queryString);
            if(resultSet.next())
                id = resultSet.getInt("casopis_id");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
        return id;
    }
    
     private void setTableData() {
        try {
            int rows = 0;
            int rowIndex = 0;
            
            Object objRecezent = cbCasopis.getSelectedItem();
            casopis_id = objRecezent.toString();
            Object objGodinaIzdanja = cbGodinaIzdanja.getSelectedItem();
            godina_izdanja = objGodinaIzdanja.toString();
            Object objSveska = cbSveska.getSelectedItem();
            rb_sveske = objSveska.toString();
            System.out.println(idCasopisa(casopis_id)+" "+godina_izdanja+" "+rb_sveske);
            Statement smt = conn.createStatement();
            
            /*ResultSet rs = smt.executeQuery("SELECT radovi.rad_id, radovi.naziv, radovi.pocetna_strana FROM radovi"
                    + "INNER JOIN casopisi ON radovi.casopis_id = casopis.casopisi_id"
                    + "WHERE radovi.casopis_id ="+idCasopisa(casopis_id)+" AND radovi.rb_sveske ="+rb_sveske+" AND radovi.rb_godine ="+godina_izdanja+" ORDER BY radovi.pocetna_strana ASC");
            */
            ResultSet rs = smt.executeQuery("SELECT radovi.rad_id, radovi.naziv, radovi.pocetna_strana FROM radovi "
                    + "INNER JOIN casopisi ON radovi.casopis_id = casopisi.casopis_id "
                    + "WHERE radovi.casopis_id="+idCasopisa(casopis_id)
                    + " AND radovi.rb_sveske ="+rb_sveske
                    + " AND radovi.rb_godine ="+godina_izdanja
                    + " ORDER BY radovi.pocetna_strana ASC");
            if (rs.next()) {
                rs.last();
                rows = rs.getRow();
                rs.beforeFirst();
            }
            
            String[][] data = new String[rows][6];
            while(rs.next())
            {
                data[rowIndex][0] = rs.getInt(1)+"";
                data[rowIndex][1] = rs.getString(2);
                data[rowIndex][2] = rs.getString(3);
                rowIndex++;
            }
            String[] cols = {"ID", "Naziv", "Pocetna strana"};
            
            DefaultTableModel model = new DefaultTableModel(data, cols);
            spisakRadovaRecezenta.setModel(model);
            
            rs.close();
            smt.close();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ne mogu se popuniti podaci..."+ex.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        cbCasopis = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        spisakRadovaRecezenta = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        cbGodinaIzdanja = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        cbSveska = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Vrati se");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Izaberite casopis:");

        cbCasopis.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbCasopisItemStateChanged(evt);
            }
        });
        cbCasopis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCasopisActionPerformed(evt);
            }
        });

        spisakRadovaRecezenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spisakRadovaRecezenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                spisakRadovaRecezentaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(spisakRadovaRecezenta);

        jLabel2.setText("Izaberite godinu:");

        cbGodinaIzdanja.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbGodinaIzdanjaItemStateChanged(evt);
            }
        });

        jLabel3.setText("Izaberite svesku:");

        cbSveska.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbSveskaItemStateChanged(evt);
            }
        });

        jButton2.setText("PRETRAGA");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 635, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel1)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(cbCasopis, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(jLabel2)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(cbGodinaIzdanja, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cbSveska, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbCasopis)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbGodinaIzdanja)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbSveska)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
                .addGap(39, 39, 39))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
        MainMenu mainMenu = new MainMenu();
        mainMenu.setDefaultCloseOperation(MainMenu.DISPOSE_ON_CLOSE);
        mainMenu.setSize(800, 600);
        mainMenu.setLocationRelativeTo(null);
        mainMenu.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cbCasopisItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbCasopisItemStateChanged
//        setTableData();
    }//GEN-LAST:event_cbCasopisItemStateChanged

    private void spisakRadovaRecezentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_spisakRadovaRecezentaMouseClicked
        try {
            /*recezent_radovi_id = Integer.parseInt(spisakRadovaRecezenta.getValueAt(spisakRadovaRecezenta.getSelectedRow(), 0).toString());
            Statement smt = conn.createStatement();
            ResultSet rs = smt.executeQuery("select * from rad_recezent where rad_recezent_id ="+recezent_radovi_id);
            if(rs.next()) {
                cbRad.setSelectedIndex(rs.getInt(2)-1);
                cbRecezent.setSelectedIndex(rs.getInt(3)-1);
                cbOdluka.setSelectedIndex(rs.getInt(4));
                tfDatumSlanja.setText(rs.getString(5));
                tfDatumDobijanja.setText(rs.getString(6));
            }
            rs.close();
            smt.close();*/
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
    }//GEN-LAST:event_spisakRadovaRecezentaMouseClicked

    private void cbGodinaIzdanjaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbGodinaIzdanjaItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cbGodinaIzdanjaItemStateChanged

    private void cbSveskaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbSveskaItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cbSveskaItemStateChanged

    private void cbCasopisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCasopisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbCasopisActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        setTableData();
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbCasopis;
    private javax.swing.JComboBox<String> cbGodinaIzdanja;
    private javax.swing.JComboBox<String> cbSveska;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable spisakRadovaRecezenta;
    // End of variables declaration//GEN-END:variables
}
