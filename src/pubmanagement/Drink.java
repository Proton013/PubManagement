/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pubmanagement;

/**
 *
 * @author eugenie_dalmas
 */
public class Drink {
    
    /**
     * The name of the drink.
     */
    String name;
    /**
     * The alcohol level of the drink.
     */
    int alcohol;
    /**
     * The purchasing price (bar to supplier).
     */
    double purchasingPrice;
    /**
     * The selling price (client to bar).
     */
    double sellingPrice;
    
    /**
     * Constructor.
     * @param name
     * @param alcohol
     * @param purchasingPrice
     * @param sellingPrice 
     */
    public Drink(String name, int alcohol, double purchasingPrice, double sellingPrice) {
        this.name = name;
        this.alcohol = alcohol;
        this.purchasingPrice = purchasingPrice;
        this.sellingPrice = sellingPrice;
    }
    
    // ----- Getters ------
    public String getName() {
        return this.name;
    }
    public int getAlcohol() {
        return this.alcohol;
    }
    public double getPurchasingPrice() {
        return this.purchasingPrice;
    }
    public double getSellingPrice() {
        return this.sellingPrice;
    }
}
