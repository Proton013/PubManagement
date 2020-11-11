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
public class FemaleWaiter extends Waiter implements Gender {
    /**
     * Attribute that differenciate a male client from a female one.
     */
    int charm;
    
    /**
     * Constructor extending Human.
     * @param bar
     * @param name
     * @param surname
     * @param wallet
     * @param popularity
     * @param shout
     * @param charm 
     */
    public FemaleWaiter(Bar bar, String name, String surname, double wallet, int popularity, String shout,
            int charm) {
        super(bar, name, surname, wallet, popularity, shout);
        this.charm = charm;
    }
    
    /**
     * Announce the speaker and its job and write the wanted message.
     * @param message to be said
     */
    @Override
    public void speak(String message, Human to) {
        System.out.println();
        System.out.println("< Waitress ");
        super.speak(message, to);
    }
    
    // Gender ------------
    /**
     * Tell of what gender is the waiter.
     * @return female
     */
    @Override
    public String tellGender() {
        return "female";
    }
    
    /**
     * Change the gender of the waiter.
     * @return MaleWaiter
     */
    @Override
    public Waiter changeGender() {
        // get infos and create a new Male/Female-Waiter 
        // then remove the obsolete one
        return new MaleWaiter(this.name, this.surname, this.wallet, this.popularity, this.shout,
        this.charm);
    }
    
    // Employee -----------
    /**
     * Try to kick a client out of the pub depending on the employee's ability 
     * and the client's resistance.
     * @param client to kick out
     * @return a Boolean for success/failure
     */
    @Override
    public Boolean kickOut(Client client) {
        super.kickOut(client);
        if (this.charm > client.getPopularity()) {
            client.speak("Alright, alright, I'll leave...", null);
            return true;
        }
        else {
            client.speak("Noo-o, I don't want to leave right now", null);
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
        if (this.charm > client.getPopularity()) {
            client.speak("Alright, alright, I'll stop...", null);
            return true;
        }
        else {
            client.speak("Noo ! I want another drink !", null);
            return false;
        }
    }
    
    // ----- Getters -----
    public int getCharm() {
        return this.charm;
    }
}
