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
public class MaleWaiter extends Waiter implements Gender {
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
        System.out.println();
        System.out.print("< Waiter ");
        super.speak(message, to);
    }
    
    // Gender -------------------
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
     * @return FemaleWaiter
     */
    @Override
    public Waiter changeGender() {
        // get current attributes and create a new FemaleWaiter
        return new FemaleWaiter(this.currentBar, this.name, this.surname, this.wallet, this.popularity, this.shout,
        this.biceps);
        // need to remove from list of employee the previous one
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
            return true;
        }
        else {
            client.speak("Noo ! I want another drink !", null);
            return false;
        }
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
