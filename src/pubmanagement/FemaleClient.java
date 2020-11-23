/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pubmanagement;

import exceptions.MaxCapacityReachedException;
import java.util.ArrayList;

/**
 *
 * @author eugenie_dalmas
 */
public class FemaleClient extends Client {
    /**
     * Attribute that differenciate a male client from a female one.
     */
    ArrayList<String> accessories;
    
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
     * @param accessories 
     */
    public FemaleClient(Bar bar, String name, String surname, double wallet, int popularity, String shout,
            Drink favoriteDrink, Drink favoriteDrink2nd, int beloteLevel,
            ArrayList<String> accessories) {
        super(bar,name, surname, wallet, popularity, shout, favoriteDrink, 
                favoriteDrink2nd, beloteLevel);
        this.accessories = accessories;
    }
    
    // Gender ------------
    /**
     * Tell of what gender is the client.
     * @return the string female or male
     */    
    @Override
    public String tellGender() {
        return "female";
    }
    
    /**
     * Change the gender of the client.
     */
    @Override
    public void changeGender() {
        String color = Bar.COLORS.get((int) (Math.random()*Bar.COLORS.size()));
        String teeShirt = color + " tee-shirt";
        Client newThis = new MaleClient(this.currentBar,this.name, this.surname, this.wallet, 
                this.popularity, this.shout, this.favoriteDrink, 
                this.favoriteDrink2nd, this.beloteLevel, teeShirt);
        // then replace the obsolete one
        if(currentBar.removeClient(this, true)) {
            try { currentBar.addClient(newThis); }
            catch (MaxCapacityReachedException e) {}
        }
    }
    
    /**
     * Displays all the informations on the client for the user.
     */
    @Override
    public void displayInformation() {
        super.displayInformation();
        System.out.print("Accessories : ");
        accessories.forEach(acc -> {
            System.out.print(acc+ " ");
        });
        System.out.println();
    }
    
    // ----- Getters -----
    /**
     * Get the accessories list.
     * @return 
     */
    public ArrayList<String> getAccessories() {
        return this.accessories;
    }
}   
