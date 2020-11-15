/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pubmanagement;

import Exceptions.SelfInteractionException;
import java.time.*;
import java.util.ArrayList;

/**
 *
 * @author eugenie_dalmas
 */
public abstract class Human {
    
    /**
     * Possible introductions seperated by a coma.
     */
    public static final String INTRODUCTIONS 
            = "Hi,Hello,Let me introduce myself,Oh hi,Hi mate,Well hi";
    
    /**
     * The name of the human.
     */
    protected String name;
    /**
     * The surname of the human.
     */
    protected String surname;
    /**
     * The wallet balence of this human.
     */
    protected double wallet;
    /**
     * The popularity level of this human.
     */    
    protected int popularity;
    /**
     * THe souht of the human.
     */
    protected String shout;
    
    /**
     * Instance of the bar that the human is currently assigned to / is in.
     */
    protected Bar currentBar;
    
    // for time management
    //protected LocalTime dateLastAction = LocalTime.now();
   
    /**
     * Constructor.
     * @param bar
     * @param name
     * @param surname
     * @param wallet starting balance;
     * @param popularity level
     * @param shout 
     */
    public Human(Bar bar, String name, String surname, double wallet, int popularity, String shout) {
        this.name = name;
        this.surname = surname;
        this.wallet = wallet;
        this.popularity = popularity;
        this.shout = shout;
        this.currentBar = bar;
    }
    
    /**
     * Modifiy the alcoohol level.
     * @param drink Object
     */
    abstract void drink(Drink drink);
    
    /**
     * Announce the speaker for the user and write the wanted message.
     * @param message to be said
     * @param to ; is null when speaking to no one in particular
     */
    public void speak(String message, Human to) {
        System.out.print("< "+this.name+" > ");
        if (to!= null) {
            System.out.print(to.getName()+", ");
        }
        System.out.println(message);
    }
    
    /**
     * Pay the cost for any payment.
     * @param cost
     * @return the amount that was paid
     */
    public double pay(double cost) {
        this.wallet -= cost;
        return cost;
    }
    
    /**
     * Buy a drink for another fellow human and maybe change its popularity.
     * @param to the human that receive the drink
     * @throws Exceptions.SelfInteractionException
     */
    public abstract void offerDrink(Client to) throws SelfInteractionException;
    
    /**
     * Run an action given probabilities between all the ones the human can 
     * start alone.
     * @param clients that are present in the pub
     */
    public abstract void action(ArrayList<Client> clients);
    
    /**
     * Refuse politly the drink offer.
     * @param from 
     */
    public void refuseDrinkOffer(Human from) {
        speak("Thank you but I can't accept your drink", from);
    }
    
    /**
     * Print an introduction of the human with random beginning.
     */
    public void introduce() {
        // add a little variety in the beginning of the introduction
        String[] intro = Human.INTRODUCTIONS.split(",");
        int randomInt = (int)(Math.random() * (intro.length + 1));
        
        speak(intro[randomInt]+", I'm "+this.name+" "+this.surname, null);
    }
    
    /**
     * Tell if the human is occupied.
     * For now, every action takes the same amount of time to be done
     * @return true if he is still doing an action
     */
    /*public Boolean isOccupied() {
        Duration timeItTakes = Duration.ZERO.plusSeconds(6);
        Duration duration = Duration.between(LocalTime.now(), this.dateLastAction);
        if (duration.compareTo(timeItTakes) >= 0) {
            return false;
        }
        else {
            return false;
        }
    }*/
    
    // ----- Getters -----
    /**
     * Get the name.
     * @return a String
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Get the surname.
     * @return a String
     */
    public String getSurname() {
        return this.surname;
    }
    
    /**
     * Get the wallet balance.
     * @return a double
     */
    public double getWalletBalance() {
        return this.wallet;
    }
    
    /**
     * Get the popularity level (max 10)
     * @return an integer
     */
    public int getPopularity() {
        return this.popularity;
    }
    
    /**
     * Get the shout.
     * @return a String
     */
    public String getShout() {
        return this.shout;
    }
}
