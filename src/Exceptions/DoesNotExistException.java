/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package exceptions;

/**
 * Exception to be thrown if a parameter in a method is not existant.
 * @author eugenie_dalmas
 */
public class DoesNotExistException extends Exception {
    
    /**
     * Constructor.
     * @param message 
     */
    public DoesNotExistException(String message) {
        super(message);
    }
}
