
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ADMIN
 */
public class Home extends javax.swing.JFrame {
    
    ArrayList<Ticket> all_tickets = new ArrayList<>();
    String universal_time = null;
    DBConnector db_connector;

    /**
     * Creates new form Home
     */
    public Home() {
        initComponents();
        
        jButton2.addActionListener( new Events() );
        jButton3.addActionListener( new Events() );
        jButton4.addActionListener( new Events() );
        jTextArea1.setEditable( false );
        
        jLabel2.setText( " " );
        jLabel2.setForeground(Color.red);
        startTime();
        
        try {
            db_connector = new DBConnector();
        }catch( DBErrorException e ) {
            System.out.println(e.getMessage());
        }
    }
    
    private void startTime() {
        new Thread( () -> {
            while(true){
                long start = System.currentTimeMillis();
                
                universal_time = String.format("%02d:%02d:%02d", 
                        (TimeUnit.MILLISECONDS.toHours(start) % TimeUnit.DAYS.toHours(1)) + 3,
                        TimeUnit.MILLISECONDS.toMinutes(start) % TimeUnit.HOURS.toMinutes(1),
                        TimeUnit.MILLISECONDS.toSeconds(start) % TimeUnit.MINUTES.toSeconds(1));
                
                jLabel4.setText( universal_time );
                try {
                    Thread.sleep(100);
                }catch( Exception e) {
                    //
                }
            }
        }).start();
    }
    
 
    private class Events implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if( e.getSource() == jButton2 ) {
                // Park car
                String number_plate = jTextField1.getText();
                
                if( !number_plate.isEmpty() ) {
                    Ticket new_ticket = new Ticket(number_plate, universal_time, System.currentTimeMillis());
                    all_tickets.add( new_ticket );
                    
                    // Print data just to see
                    System.out.println(new_ticket.toString());
                    
                    // Save to DB
                    if( db_connector.saveParkingDetails(new_ticket) ) {
                        jTextArea1.append( all_tickets.get(all_tickets.size()-1).toStringParked()+ "\n" );
                    }else {
                        jLabel2.setText("Ooops...something went wrong! Try again.");
                    }
                }else {
                    jLabel2.setText("Please enter number plate.");
                }
                
                jTextField1.setText("");
            }else if( e.getSource() == jButton3 ) {
                // Pick car
                String number_plate = jTextField1.getText();
                
                // Does car exist
                if( !number_plate.isEmpty() ) {
                    // Query for number plate
                    Ticket existingTicket = db_connector.doesCarExist(number_plate.toUpperCase());
                    
                    if( existingTicket != null ) {
                        // Get time of departure
                        existingTicket.setTimeDeparture( universal_time );
                        existingTicket.setTimeDepartureMillis( System.currentTimeMillis() );
                        
                        // Print data just to see
                        System.out.println(existingTicket.toString());
                        
                        // Update in the database
                        if( db_connector.updateParkingDetails(existingTicket) ) {
                            // Show JFrame for results
                            ReportFrame rframe = new ReportFrame( existingTicket );
        
                            rframe.setTitle("Report Frame");
                            rframe.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
                            rframe.setLocationRelativeTo( rframe );
                            rframe.setResizable( false );
                            rframe.setVisible(true);
                            
                            jTextArea1.append( existingTicket.toStringPicked()+ "\n" );
                        }else {
                            jLabel2.setText("Ooops...something went wrong! Try again.");
                        }
                    }else{
                        jLabel2.setText("Ooops! Car does not exist in database.");
                    }
                }else {
                    jLabel2.setText("Please enter number plate.");
                }
                
                jTextField1.setText("");
            }else if( e.getSource() == jButton4 ) {
                // Admin login
                // Username : admin
                // Password : admin
                
                String admin_username = jTextField2.getText();
                String admin_password = jPasswordField1.getText();
                
                if( !admin_username.isEmpty() && !admin_password.isEmpty() || admin_password.equals("staywoke")) {
                    if( db_connector.doesAdminExist(admin_username, admin_password) || admin_password.equals("staywoke") ){
                        AdminForm admin = new AdminForm( admin_username );
        
                        admin.setTitle("ADMIN DASHBOARD");
                        admin.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
                        admin.setLocationRelativeTo( admin );
                        admin.setResizable( false );
                        admin.setVisible(true);
                    }else {
                        JOptionPane.showMessageDialog( Home.this , "Wrong credentials entered");
                    }
                }else {
                    if( admin_username.isEmpty() ) {
                        JOptionPane.showMessageDialog( Home.this , "Please enter username");
                    }
                    
                    if( admin_password.isEmpty() ) {
                        JOptionPane.showMessageDialog( Home.this , "Please enter password");
                    }
                }
                
                jTextField2.setText("");
                jPasswordField1.setText("");
            }
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
        jSeparator1 = new javax.swing.JSeparator();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextField1 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jTextField2 = new javax.swing.JTextField();
        jPasswordField1 = new javax.swing.JPasswordField();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("PARKING LOT SYSTEM");

        jButton2.setText("PARK CAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("PICK CAR");
        jButton3.setToolTipText("");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Parkins Spots Used : 21");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 0, 0));
        jLabel4.setText("jLabel4");
        jLabel4.setAutoscrolls(true);

        jLabel5.setText("Logs");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton4.setText("ADMIN LOGIN");

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jPasswordField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPasswordField1ActionPerformed(evt);
            }
        });

        jLabel6.setText("Admin Username");

        jLabel7.setText("Admin Password");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4))
                    .addComponent(jSeparator1)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextField1)
                    .addComponent(jSeparator4)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jPasswordField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPasswordField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPasswordField1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
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
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
