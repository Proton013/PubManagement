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
    
    // ----- Getters -----
    /**
     * Get the accessories list.
     * @return 
     */
    public ArrayList<String> getAccessories() {
        return this.accessories;
    }
}   
