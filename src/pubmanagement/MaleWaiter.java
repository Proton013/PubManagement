/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pubmanagement;

import exceptions.MaxCapacityReachedException;

/**
 *
 * @author eugenie_dalmas
 */
public class MaleWaiter extends Waiter {
    /**
     * Attribute that differenciate a male client from a female one.
     */
    int biceps; // is like a level (<10)
    
    /**
     * Constructor extending Human.
     * @param bar
     * @param name
     * @param surname
     * @param wallet
     * @param popularity
     * @param shout
     * @param biceps 
     */
    public MaleWaiter(Bar bar, String name, String surname, double wallet, int popularity, String shout,
            int biceps) {
        super(bar, name, surname, wallet, popularity, shout);
        this.biceps = biceps;
    }
    
    /**
     * Announce the speaker and its job and write the wanted message.
     * @param message to be said
     */
    @Override
    public void speak(String message, Human to) {
        System.out.print("< Waiter ");
        super.speak(message, to);
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
     * Change the gender of the waiter.
     */
    @Override
    public void changeGender() {
        Waiter newThis = new FemaleWaiter(this.currentBar, this.name, this.surname,
                this.wallet, this.popularity, this.shout, getBiceps());
        // then replace the obsolete one
        if(currentBar.removeWaiter(this, true)) {
            try { currentBar.addWaiter(newThis); }
            catch (MaxCapacityReachedException e) {}
        }
    }
    
    // Employee ----------------
    /**
     * Try to kick a client out of the pub depending on the employee's ability 
     * and the client's resistance.
     * @param client to kick out
     * @return a Boolean for success/failure
     */
    @Override
    public Boolean kickOut(Client client) {
        speak("Sorry paw but you need to leave now.", null);
        if (this.biceps > client.getPopularity()) {
            client.speak("Alright, alright, I'll leave...", null);
            return true;
        }
        else {
            client.speak("Noo-o, I don't want to leave right now...", null);
            return false;
        }
    }
    
    /**
     * Try to stop a client from drinking more.
     * @param client to say stop to
     * @return a Boolean for success/failure
     */
    @Override
    public Boolean sayStop(Client client) {
        super.sayStop(client);
        if (this.biceps > client.getPopularity()) {
            client.speak("Alright, alright, I'll stop...", null);
            client.setRedFlag(true);
            return true;
        }
        else {
            client.speak("Noo ! I want another drink !", null);
            return false;
        }
    }
    
    /**
     * Displays all the informations on the client for the user.
     */
    @Override
    public void displayInformation() {
        super.displayInformation();
        System.out.println("Biceps : "+ biceps);
    }
    
    // ----- Getters -----
    /**
     * Get the biceps.
     * @return 
     */
    public int getBiceps() {
        return biceps;
    }
}
