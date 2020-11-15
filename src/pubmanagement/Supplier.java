/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pubmanagement;

import java.util.ArrayList;
import java.util.Map;

/**
 * Should not be able to be in the bar, not considered as a possible client.
 * 
 * @author eugenie_dalmas
 */
public class Supplier extends Human {
    
    /**
     * Constructor.
     * @param bar
     * @param name
     * @param surname
     * @param wallet unused
     * @param shout unused
     * @param popularity unused
     */
    public Supplier(Bar bar, String name, String surname, double wallet, int popularity, String shout) {
        super(bar, name, surname, wallet, popularity, shout);
    }
    
 // throw error ? or "// does nothing"
    @Override
    void drink(Drink drink) {
        throw new UnsupportedOperationException("Can't drink.");
    }

    @Override
    public void offerDrink(Client to) {
        throw new UnsupportedOperationException("Can't offer drink.");
    }
    
    @Override
    public void action(ArrayList<Client> clients) {
        throw new UnsupportedOperationException("Does not have actions he can start alone");
    }

 // --------------
    
    /**
     * Deliver the goods so the barman refill the bar's stocks.
     * @param order
     * @param barman 
     */
    public void deliver(Map<String, Integer> order, Barman barman) {
        speak("Order received, getting you that", null);
        speak("Here are your goods", null);
        barman.refillStocks(order);
    }
    
    /**
     * Is paid for the delivery.
     * @param barman
     * @param totalCost 
     */
    public void getPaid(Barman barman, double totalCost) {
        // barman give money right from till
        barman.pay(totalCost);
    }

}
