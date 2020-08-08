
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
public class SpisakRadovaRecezenta extends javax.swing.JFrame {

    /**
     * Creates new form MainMenu
     */
    private Connection conn;
    
    String recezent_id;
    
    public SpisakRadovaRecezenta() {
        initComponents();
        DatabaseConnection dbc = DatabaseConnection.getDatabaseConnection();
            conn = dbc.getConnection();
//            setTableData();
            try {
                String queryString = "SELECT * from recezenti";
                Statement smt = conn.createStatement();
                ResultSet resultSet = smt.executeQuery(queryString);
                ResultSetMetaData metaData = resultSet.getMetaData();
                int colCount = metaData.getColumnCount();
                while(resultSet.next())
                {
                    cbRecezent.addItem(resultSet.getString(2).concat(" ").concat(resultSet.getString(3)));
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
            
            
            
    }
    
    String brojOdobrenih(int id)
    {
        String status = "Nije prihvacen";
        try {
            String queryString = "SELECT SUM(odluka = 1) FROM rad_recezent WHERE rad_id = "+id;
            Statement smt = conn.createStatement();
            ResultSet resultSet = smt.executeQuery(queryString);
            if(resultSet.next())
                id = resultSet.getInt(1);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
        if(id > 2)
            status = "Prihvacen";
        return status;
    }
    
    String prikaziCasopis(int id)
    {
        String naziv = "";
        try {
            String queryString = "SELECT casopisi.naziv from casopisi inner join radovi on casopisi.casopis_id = radovi.casopis_id where radovi.rad_id = "+id;
            Statement smt = conn.createStatement();
            ResultSet resultSet = smt.executeQuery(queryString);
            if(resultSet.next())
                naziv = resultSet.getString(1);
        } catch (Exception ex) {
            //JOptionPane.showMessageDialog(this, ex.getMessage());
            naziv = "Nije prihvacen";
        }
        return naziv;
    }

     int idRecezent(String naziv)
    {
        String[] splited = naziv.split("\\s+");

        int id = 0;
        try {
            String queryString = "SELECT recezent_id from recezenti where recezenti.ime = '"+splited[0]+"' AND recezenti.prezime = '"+splited[1]+"'";
            Statement smt = conn.createStatement();
            ResultSet resultSet = smt.executeQuery(queryString);
            if(resultSet.next())
                id = resultSet.getInt("recezent_id");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
        return id;
    }
     
    private void setTableData() {
        try {
            int rows = 0;
            int rowIndex = 0;
            
            Object objRecezent = cbRecezent.getSelectedItem();
            recezent_id = objRecezent.toString();
            
            Statement smt = conn.createStatement();
            /*ResultSet rs = smt.executeQuery("SELECT rad_recezent.rad_recezent_id, IF(SUM(rad_recezent.odluka = 1) > 2, radovi.rb_sveske, 'Nije prihvacen') , "
                    + "radovi.naziv, rad_recezent.datum_slanja_recenzije, rad_recezent.datum_dobijanja_recenzije, "
                    + "rad_recezent.odluka FROM radovi INNER JOIN rad_recezent ON "
                    + "radovi.rad_id = rad_recezent.rad_id WHERE rad_recezent.recezent_id ="+idRecezent(recezent_id));*/
            ResultSet rs = smt.executeQuery("SELECT rad_recezent.rad_recezent_id, rad_recezent.rad_id,"
                    + "radovi.naziv, rad_recezent.datum_slanja_recenzije, rad_recezent.datum_dobijanja_recenzije, "
                    + "rad_recezent.odluka FROM radovi INNER JOIN rad_recezent ON "
                    + "radovi.rad_id = rad_recezent.rad_id WHERE rad_recezent.recezent_id ="+idRecezent(recezent_id));
            if (rs.next()) {
                rs.last();
                rows = rs.getRow();
                rs.beforeFirst();
            }
            
            String[][] data = new String[rows][6];
            while(rs.next())
            {
                data[rowIndex][0] = rs.getInt(1)+"";
//                data[rowIndex][1] = prikaziCasopis(rs.getString(2));
                data[rowIndex][1] = rs.getString(3);
                data[rowIndex][2] = rs.getString(3);
                data[rowIndex][3] = rs.getString(4);
                data[rowIndex][4] = rs.getString(5);
//                data[rowIndex][5] = prikaziOdluku(rs.getInt(6));
                data[rowIndex][5] = String.valueOf(brojOdobrenih(rs.getInt(2)));
                rowIndex++;
            }
            String[] cols = {"ID", "Odgovor", "Rad", "Datum slanja", "Datum recenzije", "Casopis"};
            
            DefaultTableModel model = new DefaultTableModel(data, cols);
            spisakRadovaRecezenta.setModel(model);
            
            rs.close();
            smt.close();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ne mogu se popuniti podaci..."+ex.getMessage());
        }
    }
    
     String prikaziOdluku(int id)
    {
        String odluka = "";
        if(id == 0)   
            odluka = "Odbija se";
        else if(id == 1)
            odluka = "Prihvata se";
        return odluka;
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
        cbRecezent = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        spisakRadovaRecezenta = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Vrati se");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Izaberite recezenta:");

        cbRecezent.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbRecezentItemStateChanged(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbRecezent, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbRecezent, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(516, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(105, 105, 105)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(167, Short.MAX_VALUE)))
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

    private void cbRecezentItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbRecezentItemStateChanged
        setTableData();
    }//GEN-LAST:event_cbRecezentItemStateChanged

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
    private javax.swing.JComboBox<String> cbRecezent;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable spisakRadovaRecezenta;
    // End of variables declaration//GEN-END:variables
}
