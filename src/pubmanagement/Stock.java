/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pubmanagement;

import java.util.HashMap;
import java.util.Map;

/**
 * OBSOLETE
 * 
 * 
 * 
 * 
 * 
 * @author eugenie_dalmas
 */
public class Stock {
    private Map<String, Integer> quantities = new HashMap<>();
    
    /**
     * Constructor.
     * @param quantities 
     */
    public Stock(Map<String, Integer> quantities) {
        this.quantities = quantities;
    }
    
    /*
    ideas methods:
        getDrinkQuantity(Drink drink)
        getQuantites()
        setDrinkQuantity(Drink drink, quantity)
        setQuantity()
    */
    
    // ----- Getters -----
    
    public Map<String, Integer> getQuantities() {
        return this.quantities;
    }
}
