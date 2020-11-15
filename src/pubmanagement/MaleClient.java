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
    
    // ----- Getters -----
    /**
     * Get the tee-shirt color.
     * @return 
     */
    public String getTeeShirt() {
        return this.teeShirt;
    }
}
