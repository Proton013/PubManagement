/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package exceptions;

/**
 *
 * @author eugenie_dalmas
 */
public class MinCapacityReachedException extends Exception {

    /**
     * Constructor.
     * @param message 
     */
    public MinCapacityReachedException(String message) {
        super(message);
    }
    
}
