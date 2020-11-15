/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Exceptions;

/**
 * Exception to be thrown if an input is not supported.
 * @author eugenie_dalmas
 */
public class UnsupportedInputException extends Exception {
    
    /**
     * Constructor.
     * @param message 
     */
    public UnsupportedInputException(String message) {
        super(message);
    }
}
