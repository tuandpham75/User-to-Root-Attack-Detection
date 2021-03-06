/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs480project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import net.proteanit.sql.DbUtils;

public class LogSystem extends javax.swing.JFrame {
    private JTable logTable;
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    PopulateTableWorker worker = null;
    /**
     * Creates new form LogSystem
     */
    public LogSystem() {
        super("Log Event Manager");
        conn = LogConnect.ConnectDB();
        readFile();
        
        //executes in the Event Dispatching Thread
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                DefaultTableModel model = new DefaultTableModel();
                String header[] = new String[]{"Month","Date","Time","User","Drive","Event"};
                model.setColumnIdentifiers(header);
                
                //custom Renderer that highlights events in case of security issues
                logTable = new JTable(model) {
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
                
                //set up jtable display
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

                worker = new PopulateTableWorker (model);
                worker.execute();
            }
        });
    }
    
    private void update_Table(){
        try{
            String sql = "select * from LinuxEventLogs";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            logTable.setModel(DbUtils.resultSetToTableModel(rs));
            conn.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //parses through log file
    public void readFile(){
        StringTokenizer st; 
        String delim = "]:";
        try{
            FileInputStream fstream = new FileInputStream("C:\\Users\\Aileen\\Documents\\CS480\\User-to-Root-Attack-Detection-dynamically_update_table\\CS480Project\\src\\auth2.log");
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
            //conn.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    //inputes array of values into database
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
        } catch ( Exception e ){
            System.out.println("im the problem y'all" + e.toString());
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

        jMenuItem1 = new javax.swing.JMenuItem();
        clearButton = new javax.swing.JButton();
        attackButton = new javax.swing.JButton();
        stopButton = new javax.swing.JButton();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
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

        stopButton.setText("Stop");
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(622, Short.MAX_VALUE)
                .addComponent(attackButton)
                .addGap(29, 29, 29)
                .addComponent(stopButton, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(556, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clearButton)
                    .addComponent(attackButton)
                    .addComponent(stopButton))
                .addGap(21, 21, 21))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        try {
            // TODO add your handling code here
            worker.close();
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

    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButtonActionPerformed
        try {
            worker.close();
        } catch (SQLException ex) {
            Logger.getLogger(LogSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_stopButtonActionPerformed

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
    private javax.swing.JButton stopButton;
    // End of variables declaration//GEN-END:variables
}
    
//Background thread used to execute a long-running task
//dynamically adds rows from database to jtable
class PopulateTableWorker extends SwingWorker<DefaultTableModel, Object[]> {
    Connection conn = LogConnect.ConnectDB();
    PreparedStatement pst = null;
    ResultSet rs = null;
    private final DefaultTableModel model;
    String query = "SELECT * FROM 'LinuxEventLogs'";
    Statement st;

    public PopulateTableWorker(DefaultTableModel model){
      this.model = model;
    }

    //function that runs in the background to update the table model 
    //with rows from the database every 500 milliseconds
    @Override
    protected DefaultTableModel doInBackground() throws Exception {

        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            // While there are more rows
            while(rs.next()){
              // Get the row from the slow source
              Object[] row = {rs.getString("Month"),rs.getInt("Date"),rs.getString("Time"),rs.getString("User"),rs.getString("Drive"),rs.getString("Event")};
              Thread.sleep(100);

              // Update the model with the new row
              publish(row);
            }
        } catch(Exception e){

        }

      return model;
    }

    //grabs information published from doInBackground() method 
    //and adds the rows to the JTable
    @Override
    protected void process(List<Object[]> chunks){
        for(Object[] row : chunks){
            model.addRow(row);
        }
    }
    
    //close connection to database
    protected void close() throws SQLException{
        conn.close();
    }
}
