/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ADMIN
 */
public class DBErrorException extends Exception {
    
    private String ErrorMessage = null;
    
    public DBErrorException( String message ) {
        super( message );
        this.ErrorMessage = message;
    }
    
    @Override
    public String getMessage() {
        return this.ErrorMessage;
    }
}
