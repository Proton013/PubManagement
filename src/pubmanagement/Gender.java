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
public interface Gender {
    /**
     * Tell of what gender is the human.
     * @return the gender (mal or female)
     */
    String tellGender();
    
    /**
     * Change the gender of the human.
     * @return 
     */
    Human changeGender();
}
