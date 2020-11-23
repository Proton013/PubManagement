/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pubmanagement;

import exceptions.SelfInteractionException;
import java.util.ArrayList;

/**
 * Entity that interact with the bar.
 * @author eugenie_dalmas
 */
public abstract class Human implements InformationDisplayer {
    
    /**
     * Possible introductions seperated by a coma.
     */
    public static final String INTRODUCTIONS = "Hi,Hello,Let me introduce myself,Oh hi,Hi mate,Well hi";
    
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
   
    /**
     * Constructor.
     * @param bar
     * @param name
     * @param surname
     * @param wallet starting balance
     * @param popularity level
     * @param shout 
     */
    public Human(Bar bar, String name, String surname, double wallet, int popularity, String shout) {
        this.name = name;
        this.surname = surname;
        this.wallet = (double)((int)(wallet*100)/100);
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
        wallet -= cost;
        System.out.println("    # "+name+" "+surname+" paid: "+cost
        +" --> Remaining: "+wallet);
        return cost;
    }
    
    /**
     * Buy a drink for another fellow human and maybe change its popularity.
     * @param to the human that receive the drink
     * @throws Exceptions.SelfInteractionException
     */
    public abstract void offerDrink(Client to) throws SelfInteractionException;
    
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
        int randomInt = (int)(Math.random() * intro.length);
        
        speak(intro[randomInt]+", I'm "+this.name+" "+this.surname, null);
    }
    
    // Management ----------
    /**
     * Run an action given probabilities between all the ones the human can 
     * start alone.
     * @param clients that are present in the pub
     */
    public abstract void action(ArrayList<Client> clients);
    
    /**
     * Displays all the informations on the human for the user.
     * Used in the management class for Information menu
     */
    @Override
    public abstract void displayInformation();
    
    // ----- Setters -----
    /**
     * Set the wallet balance. 
     * @param balance to be setted
     */
    public void setWalletBalance(double balance) {
        wallet = balance;
    }
    
    /**
     * Set the popularity
     * @param level to be setted
     */
    public void setPopularity(int level) {
        this.popularity = level;
    }
    
    /**
     * Set the user for a belote game only.
     */
    public void setUser() {
        this.name = "user";
        this.surname = "";
        
    }
    
    // ----- Getters -----
    /**
     * Get the name.
     * @return the string name
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Get the surname.
     * @return the string surname
     */
    public String getSurname() {
        return this.surname;
    }
    
    /**
     * Get the wallet balance.
     * @return the double balance
     */
    public double getWalletBalance() {
        return this.wallet;
    }
    
    /**
     * Get the popularity level (max 10)
     * @return the level as an integer
     */
    public int getPopularity() {
        return this.popularity;
    }
    
    /**
     * Get the shout.
     * @return the string shout
     */
    public String getShout() {
        return this.shout;
    }
}
