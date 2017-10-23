
import javax.swing.JFrame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ADMIN
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Home home = new Home();
        
        home.setTitle("Parking Lot System v2.1.1");
        home.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        home.setLocationRelativeTo( home );
        home.setResizable( false );
        home.setVisible(true);
    }
    
}
