/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pubmanagement;

import java.util.ArrayList;

/**
 *
 * @author eugenie_dalmas
 */
public class MaleClient extends Client implements Gender {
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
    
    // Gender -------------
    /**
     * Tell of what gender is the waiter.
     * @return male
     */
    @Override
    public String tellGender() {
        return "male";
    }
    
    /**
     * Change the gender of the waiter.
     * @return MaleWaiter
     */
    @Override
    public Human changeGender() {
        // get infos and create a new Male/Female-Client 
        // and change the obsolete one
        ArrayList<String> accessories = new ArrayList<>(); // random to found
        return new FemaleClient(this.currentBar, this.name, this.surname, this.wallet, 
                this.popularity, this.shout, this.favoriteDrink, 
                this.favoriteDrink2nd, this.beloteLevel, accessories);
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
