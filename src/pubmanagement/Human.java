/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pubmanagement;

import java.time.*;

/**
 *
 * @author eugenie_dalmas
 */
public abstract class Human {
    
    public static final String INTRODUCTIONS 
            = "Hi,Hello,Let me introduce myself,Oh hi,Hi mate,Well hi";
    
    protected String name;
    protected String surname;
    protected double wallet;    // balance of wallet
    protected int popularity;   // level
    protected String shout;
    
    protected Bar currentBar;
    
    // for time management
    protected LocalTime dateLastAction = LocalTime.now();
    // protected String lastAction = "initiated"; // unused for now
   
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
        System.out.println();
        System.out.print("< "+this.name+" > ");
        if (to!= null) {
            System.out.print(to.getName()+", ");
        }
        System.out.print(message);
        System.out.println();
    }
    
    /**
     * Pay the given drink.
     * @param cost
     * @return the amount that was paid
     */
    public double pay(double cost) {
        this.wallet -= cost;
        System.out.println(this.name+" "+this.surname+" paid: "+ cost +" -> Remaining: " + this.wallet);
        // pay not exactly the cost but a bit more as int 
        // return paid;
        return cost;
    }
    
    /**
     * Buy a drink for another fellow human and maybe change its popularity.
     * @param to the human that receive the drink
     */
    public abstract void offerDrink(Client to);
    
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
    public Boolean isOccupied() {
        Duration timeItTakes = Duration.ZERO.plusSeconds(6);
        Duration duration = Duration.between(LocalTime.now(), this.dateLastAction);
        if (duration.compareTo(timeItTakes) >= 0) {
            return false;
        }
        else {
            return false;
        }
    }
    
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
    /**
     * Get the date of the last action.
     * @return a LocalTime
     */
    public LocalTime getDateLastAction() {
        return this.dateLastAction;
    }
    /*
    public String getLastAction() {
        return this.lastAction;
    }
    */
    
    // ----- Setters -----
    /**
     * Set the date of the last action.
     * @param date 
     */
    public void setDateLastAction(LocalTime date) {
        this.dateLastAction = date;
    }
    /*
    public void setLastAction(String action) {
        this.lastAction = action;
    }
    */
}
