/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pubmanagement;

import Exceptions.MaxCapacityReachedException;
import java.util.ArrayList;

/**
 *
 * @author eugenie_dalmas
 */
public class MaleClient extends Client {
    /**
     * Attribute that differenciate a male client from a female one.
     */
    String teeShirt; // color + " tee-shirt"
    
    /**
     * Constructor.
     * @param bar
     * @param name
     * @param surname
     * @param wallet
     * @param popularity
     * @param shout
     * @param favoriteDrink
     * @param favoriteDrink2nd
     * @param beloteLevel
     * @param teeShirt color
     */
    public MaleClient(Bar bar, String name, String surname, double wallet, int popularity, String shout,
            Drink favoriteDrink, Drink favoriteDrink2nd, int beloteLevel,
            String teeShirt) {
        
        super(bar, name, surname, wallet, popularity, shout, favoriteDrink, 
                favoriteDrink2nd, beloteLevel);
        this.teeShirt = teeShirt;
    }
    
    // Gender ------------
    /**
     * Tell of what gender is the client.
     * @return the string female or male
     */    
    @Override
    public String tellGender() {
            return "male";
    }
    
    /**
     * Change the gender of the client.
     */
    @Override
    public void changeGender() {
        ArrayList<String> accessories = new ArrayList<>(); // random to found
        Client newThis = new FemaleClient(this.currentBar, this.name, this.surname, this.wallet, 
                this.popularity, this.shout, this.favoriteDrink, 
                this.favoriteDrink2nd, this.beloteLevel, accessories);
        // then replace the obsolete one
        if(currentBar.removeClient(this, true)) {
            try { currentBar.addClient(newThis); }
            catch (MaxCapacityReachedException e) {}
        }
    }
    
    // ----- Getters -----
    /**
     * Get the tee-shirt color.
     * @return 
     */
    public String getTeeShirt() {
        return this.teeShirt;
    }
}
