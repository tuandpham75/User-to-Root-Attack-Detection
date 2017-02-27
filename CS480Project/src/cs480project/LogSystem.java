/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs480project;

/**
 *
 * @author Aileen
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.TableCellRenderer;
import net.proteanit.sql.DbUtils;


public class LogSystem extends javax.swing.JFrame {
    private JTable logTable;
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    /**
     * Creates new form LogSystem
     */
    /**
     * Creates new form LogSystem
     */
    public LogSystem() {
        super("Log Event Manager");
        conn = LogConnect.ConnectDB();
        readFile();
 
        List<Log> listLogs = createListLogs();
        TableModel tableModel = new LogTableModel(listLogs);
        logTable = new JTable(tableModel) {
        public Component prepareRenderer(TableCellRenderer renderer, int Index_row, int Index_col) {
            Component comp = super.prepareRenderer(renderer, Index_row, Index_col);
            if (!isRowSelected(Index_row)) {
                if(getValueAt(Index_row, Index_col).toString().contains("Failed password for root")) {  
                    comp.setBackground(Color.YELLOW);  
                }
                else if(getValueAt(Index_row, Index_col).toString().contains("Disconnecting")) {  
                    comp.setBackground(Color.RED);  
                }
                else {
                    comp.setBackground(Color.white);
                }
            }
            return comp;
        }};
        logTable.setAutoCreateRowSorter(true);
        logTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        logTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        logTable.getColumnModel().getColumn(1).setPreferredWidth(50);
        logTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        logTable.getColumnModel().getColumn(3).setPreferredWidth(50);
        logTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        logTable.getColumnModel().getColumn(5).setPreferredWidth(680);
        
        JScrollPane scrollPane = new JScrollPane(logTable);
        
        scrollPane.setPreferredSize(new Dimension(1031,600));
        add(scrollPane, BorderLayout.CENTER);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }
    
    private void update_Table(){
        try{
            String sql = "select * from LinuxEventLogs";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            logTable.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void readFile(){
        StringTokenizer st; 
        String delim = "]:";
        try{
        FileInputStream fstream = new FileInputStream("C:\\Users\\Tuan Pham\\Documents\\SOFTWARE DEV\\Projects480\\CS480Project\\src\\auth2.log");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        String strLine;

        while ((strLine = br.readLine()) != null)   {
            String[] tokens = strLine.split( delim );
            String[] firstHalfToken = tokens[0].split(" ");
            sendToDatabase( firstHalfToken, tokens[1] );          
        }
        System.out.println("done!");
        fstream.close();
        br.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    public void sendToDatabase( String[] array, String secondHalf ){
        try{
            String sql = "INSERT INTO LinuxEventLogs (Month,Date,Time,User,Drive,Event) VALUES(?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
                pst.setString( 1, array[0]);
                pst.setInt( 2, Integer.parseInt(array[1]));
                pst.setString( 3, array[2]);
                pst.setString( 4, array[3]);
                pst.setString( 5, array[4] + "]");
                pst.setString( 6, secondHalf);
                pst.executeUpdate();
                //update_Table();
               // System.out.println("added" + array[4]);
        } catch ( Exception e ){
            System.out.println("im the problem y'all" + e.toString());
        }
    }
    
    public List<Log> createListLogs() {
        List<Log> listLogs = new ArrayList<>();
        String query = "SELECT * FROM `LinuxEventLogs`";
        Statement st;
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Log log;
            while(rs.next()){
                log = new Log(rs.getString("Month"),rs.getInt("Date"),rs.getString("Time"),rs.getString("User"),rs.getString("Drive"),rs.getString("Event"));
                listLogs.add(log);
            }
        }catch(Exception e){
        }
        return listLogs;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        clearButton = new javax.swing.JButton();
        attackButton = new javax.swing.JButton();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(800, 800));
        setMinimumSize(new java.awt.Dimension(800, 800));

        clearButton.setText("Clear Logs");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        attackButton.setText("Check For Attacks");
        attackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                attackButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(407, Short.MAX_VALUE)
                .addComponent(attackButton)
                .addGap(50, 50, 50)
                .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(123, 123, 123))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(263, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clearButton)
                    .addComponent(attackButton))
                .addGap(21, 21, 21))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        try {
            // TODO add your handling code here:
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM LinuxEventLogs";
            stmt.executeUpdate(sql);
            update_Table();
        } catch (SQLException ex) {
            Logger.getLogger(LogSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_clearButtonActionPerformed

    private void attackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_attackButtonActionPerformed
        // TODO add your handling code here:
        int warnings = 0;
        int fatal = 0;
        ArrayList<Object> logArray = new ArrayList<Object>();
        for (int i = 0; i < logTable.getRowCount(); i++) {
            
            logArray.add(logTable.getModel().getValueAt(i, 5)); 
        }
        for (int i = 0; i < logArray.size(); i++) {
            if (logArray.get(i).toString().contains("Failed password for root")) {
                    warnings++;     
            }
            if (logArray.get(i).toString().contains("Disconnecting")) {
                    fatal++;
            }
        }
       JOptionPane.showMessageDialog(null,
        "Security warnings: " + warnings + "\nFatal Attacks: " + fatal, "Security Concerns", JOptionPane.PLAIN_MESSAGE);
    }//GEN-LAST:event_attackButtonActionPerformed

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
            java.util.logging.Logger.getLogger(LogSystem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LogSystem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LogSystem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LogSystem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LogSystem().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton attackButton;
    private javax.swing.JButton clearButton;
    private javax.swing.JMenuItem jMenuItem1;
    // End of variables declaration//GEN-END:variables
}
