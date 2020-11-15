/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Exceptions;

/**
 * Exception to be thrown if try to add more to a container (list or so) that
 * has reached its maximum capacity.
 * @author eugenie_dalmas
 */
public class MaxCapacityReachedException extends Exception {
    
    /**
     * Constructor.
     * @param message 
     */
    public MaxCapacityReachedException(String message) {
        super(message);
    }
}
