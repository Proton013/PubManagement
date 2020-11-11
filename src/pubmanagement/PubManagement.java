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
public class PubManagement {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Bar OldGoat = new Bar("Old Goat");
        
        ArrayList<String> acce = new ArrayList<>();
        acce.add("Gold Necklace");
        Client sandra = new FemaleClient(OldGoat,"Sandra", "Hooly", 250, 2, "Yaaaay", "Mojito", "Pale Ale", 1, acce); // drinks pb
        sandra.introduce();
    }
    
}
