/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pubmanagement;

import java.util.Map;

/**
 * Should not be able to be in the bar, not considered as a possible client.
 * 
 * @author eugenie_dalmas
 */
public class Supplier extends Human {
    
    /**
     * Constructor.
     * @param name
     * @param surname
     */
    public Supplier(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }
    
 // throw error ?
    @Override
    void drink(Drink drink) {
        throw new UnsupportedOperationException("Can't drink.");
    }

    @Override
    public void offerDrink(Client to) {
        throw new UnsupportedOperationException("Can't offer drink.");
    }
 // --------------
    
    public void deliver(Map<String, Integer> order, Barman barman) {
        speak("Order received, getting you that", null);
        speak("Here are your goods", null);
        barman.refillStocks(order);
    }
    
    public void getPaid(Barman barman, double totalCost) {
        // barman give money right from till
        barman.pay(totalCost);
    }

}
