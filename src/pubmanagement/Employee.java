/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pubmanagement;

/**
 *
 * @author eugen
 */
public interface Employee {
    /**
     * Try to kick a client out of the pub depending on the employee's ability 
     * and the client's resistance.
     * @param client to kick out
     * @return a Boolean for success/failure
     */
    Boolean kickOut(Client client);
    
    /**
     * Try to stop a client from drinking more.
     * @param client to say stop to
     * @return a Boolean for success/failure
     */
    Boolean sayStop(Client client);
    
    /**
     * Serve the drink from the stock given by a barman or thus the employee 
     * himself.
     * @param drink to serve
     * @param client that ordered
     * @returna Boolean for success/failure
     */
    Boolean serve(Drink drink, Client client);
}
