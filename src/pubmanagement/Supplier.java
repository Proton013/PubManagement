/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pubmanagement;

import java.util.ArrayList;
import java.util.Map;

/**
 * Should not be able to be in the bar, not considered as a possible client.
 * 
 * @author eugenie_dalmas
 */
public class Supplier extends Human {
    
    /**
     * Constructor.
     * @param bar
     * @param name
     * @param surname
     * @param wallet unused
     * @param shout unused
     * @param popularity unused
     */
    public Supplier(Bar bar, String name, String surname, double wallet, int popularity, String shout) {
        super(bar, name, surname, wallet, popularity, shout);
    }
    
    /**
     * Announce the speaker for the user and write the wanted message.
     * @param message to be said
     * @param to ; is null when speaking to no one in particular
     */
    @Override
    public void speak(String message, Human to) {
        System.out.print("< Supplier "+this.name+" > ");
        if (to!= null) {
            System.out.print(to.getName()+", ");
        }
        System.out.println(message);
    }
    
    /**
     * Mandatory Override but never used method.
     * @param drink 
     */
    @Override
    void drink(Drink drink) {
        throw new UnsupportedOperationException("Suuplier cannot drink.");
    }

    /**
     * Mandatory Override but never used method.
     * @param to 
     */
    @Override
    public void offerDrink(Client to) {
        throw new UnsupportedOperationException("Supplier cannot offer drink.");
    }
    
    /**
     * Mandatory Override but never used method.
     * @param clients 
     */
    @Override
    public void action(ArrayList<Client> clients) {
        throw new UnsupportedOperationException("Supplier does not have actions he can start alone");
    }
    
    /**
     * Deliver the goods so the barman refill the bar's stocks.
     * @param order made by the barman
     * @param barman that order the drinks
     */
    public void deliver(Map<String, Integer> order, Barman barman) {
        speak("Order received, getting you that", null);
        speak("Here are your goods", null);
        barman.refillStocks(order);
    }
    
    /**
     * Is paid for the delivery.
     * @param barman that ordered the drinks
     * @param totalCost 
     */
    public void getPaid(Barman barman, double totalCost) {
        // barman give money right from till
        barman.pay(totalCost);
    }
    
    // Management -------------
    /**
     * Displays all the informations on the barman for the user.
     * Used in the management class for Information menu
     */
    @Override
    public void displayInformation() {
        System.out.println("[Supplier]  "+name+" "+surname);
        System.out.println("    Wallet balance: "+wallet);
        System.out.println("    Popularity: "+popularity);
        System.out.println("    Allowed drinks: None");
    }
    
}
