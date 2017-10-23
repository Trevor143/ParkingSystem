/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ADMIN
 */
import java.sql.* ;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class DBConnector {
    Connection conn;
    final String USER = "root";
    final String PASS = "";
    final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    final String DB_URL = "jdbc:mysql://localhost/parking_system";
    
    public DBConnector() throws DBErrorException {
        try {
            Class.forName( JDBC_DRIVER );
            this.conn = DriverManager.getConnection( DB_URL , USER, PASS );
            
            System.out.println("--DATABASE CONNECTION SUCCESSFUL--");
        }catch( Exception e ) {
            throw new DBErrorException( e.getMessage());
        }
    }
    
    public void closeConnection() {
        try {
            this.conn.close();
            
            System.out.println("--DATABASE CONNECTION CLOSED SUCCESSFULLY--");
        }catch( Exception e ) {
            System.out.println( e.getMessage() );
        }
    }
    
    public boolean saveParkingDetails( Ticket ticket ) {
        Statement stmt;
        
        try {
            stmt = conn.createStatement();
            
            String sql_query = "INSERT INTO `cardetails`(`carID`, `carRegistration`, `carArrivalTimeMillis`, `carArrivalTime`, `carDepartureTimeMillis`, `carDepartureTime`, `cost`)"
                    + "VALUES (?,?,?,?,'','','0')";
            
            // Prepare statements
            PreparedStatement pre = conn.prepareStatement( sql_query );
            pre.setInt(1, ticket.getRandomID());
            pre.setString(2, ticket.getNumberPlate());
            pre.setLong(3, ticket.getTimeCreatedMillis());
            pre.setString(4, ticket.getTimeCreated());
            
            // Execute
            pre.executeUpdate();
        }catch( Exception e ) {
            System.out.println( e.getMessage() );
            return false;
        }
        
        return true;
    }
    
    public boolean updateParkingDetails( Ticket ticket ) {
        Statement stmt;
        
        try {
            stmt = conn.createStatement();
            
            String sql_query = "UPDATE `cardetails` SET `carDepartureTimeMillis`=?,`carDepartureTime`=?,`cost`=? WHERE `carID`= ? AND `cost` = '0'";
            
            // Prepare statements
            PreparedStatement pre = conn.prepareStatement( sql_query );
            pre.setLong(1, ticket.getTimeDepartedMillis());
            pre.setString(2, ticket.getTimeDeparted());
            pre.setLong(3, ticket.getCost());
            pre.setInt(4, ticket.getRandomID());
            
            // Execute
            pre.executeUpdate();
        }catch( Exception e ) {
            System.out.println( e.getMessage() );
            return false;
        }
        
        return true;
    }
    
    public Ticket doesCarExist( String number_plate ) {
        Ticket existingTicket = null;
        Statement stmt;
        int counter = 0;
        
        try {
            stmt = conn.createStatement();
            
            String sql_query = "SELECT * FROM `cardetails` WHERE `carRegistration` = '" + number_plate + "' AND `cost` = '0'";
            
            // Get results
            ResultSet rs = stmt.executeQuery( sql_query );
            while( rs.next() ) {
                ++counter;
                existingTicket = new Ticket( rs.getString("carRegistration"), 
                        rs.getString("carArrivalTime"), rs.getLong("carArrivalTimeMillis"), rs.getInt("carID") );
            }
            
            if(counter > 0) {
                return existingTicket;
            }else if( counter == 0 ) {
                return null;
            }
        }catch( Exception e ) {
            System.out.println( e.getMessage() );
            return null;
        }
        
        return null;
    }
    
    public boolean doesAdminExist( String an, String ap ) {
        Statement stmt;
        int counter = 0;
        
        try {
            stmt = conn.createStatement();
            
            String sql_query = "SELECT * FROM `admindetails` WHERE `adminUsername` = '"+an+"' AND `adminPassword` = '"+ap+"'";
            
            // Get results
            ResultSet rs = stmt.executeQuery( sql_query );
            while( rs.next() ) {
                ++counter;
            }
            
            if(counter > 0) {
                return true;
            }else if( counter == 0 ) {
                return false;
            }
        }catch( Exception e ) {
            System.out.println( e.getMessage() );
            return false;
        }
        
        return true;
    }
    
    public DefaultTableModel buildParkingTableModel() {
        Statement stmt;
        
        try {
            stmt = conn.createStatement();
            String sql_query = "SELECT * FROM `cardetails` ORDER BY `carArrivalTimeMillis` DESC";
            
            // Get results
            ResultSet rs = stmt.executeQuery( sql_query );
            ResultSetMetaData metaData = rs.getMetaData();

            // names of columns
            Vector<String> columnNames = new Vector<String>();
            int columnCount = metaData.getColumnCount();
            for (int column = 1; column <= columnCount; column++) {
                if( column != 3 && column != 5  ) {
                    columnNames.add(metaData.getColumnName(column));
                }
            }

            // data of the table
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (rs.next()) {
                Vector<Object> vector = new Vector<Object>();
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    if( columnIndex != 3 && columnIndex != 5  ) {
                        vector.add(rs.getObject(columnIndex));
                    }
                }
                data.add(vector);
            }

            return new DefaultTableModel(data, columnNames);
        }catch( Exception e ) {
            System.out.println( e.getMessage() );
            return null;
        }
        
    }
    
    public DefaultTableModel buildAdminsTableModel() {
        Statement stmt;
        
        try {
            stmt = conn.createStatement();
            String sql_query = "SELECT * FROM `admindetails`";
            
            // Get results
            ResultSet rs = stmt.executeQuery( sql_query );
            ResultSetMetaData metaData = rs.getMetaData();

            // names of columns
            Vector<String> columnNames = new Vector<String>();
            int columnCount = metaData.getColumnCount() - 1;
            for (int column = 1; column <= columnCount; column++) {
                columnNames.add(metaData.getColumnName(column));
            }

            // data of the table
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (rs.next()) {
                Vector<Object> vector = new Vector<Object>();
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    vector.add(rs.getObject(columnIndex));
                }
                data.add(vector);
            }

            return new DefaultTableModel(data, columnNames);
        }catch( Exception e ) {
            System.out.println( e.getMessage() );
            return null;
        }
        
    }
    
    public boolean addAdmin( String un, String up ) {
        Statement stmt;
        
        try {
            stmt = conn.createStatement();
            
            String sql_query = "INSERT INTO `admindetails`(`adminID`,`adminUsername`, `adminPassword`) "
                    + "VALUES (?, ?, ?)";
            
            // Prepare statements
            PreparedStatement pre = conn.prepareStatement( sql_query );
            pre.setInt(1, (un.hashCode() + up.hashCode()));
            pre.setString(2, un);
            pre.setString(3, up);
            
            // Execute
            pre.executeUpdate();
        }catch( Exception e ) {
            System.out.println( e.getMessage() );
            return false;
        }
        
        return true;
    }
    
    public boolean deleteAdmin( String dn ) {
        Statement stmt;
        
        try {
            stmt = conn.createStatement();
            
            String sql_query = "DELETE FROM `admindetails` WHERE `adminUsername` = ?";
            
            // Prepare statements
            PreparedStatement pre = conn.prepareStatement( sql_query );
            pre.setString(1, dn);
            
            // Execute
            pre.executeUpdate();
        }catch( Exception e ) {
            System.out.println( e.getMessage() );
            return false;
        }
        
        return true;
    }
}

