/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Exceptions;

/**
 * Exception that is thrown when a method used by an instance has itself in his
 * parameters.
 * @author eugenie_dalmas
 */
public class SelfInteractionException extends Exception {
    /**
     * Constructor.
     * @param message 
     */
    public SelfInteractionException(String message) {
        super(message);
    }
}
