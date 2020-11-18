/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pubmanagement;

import Exceptions.DoesNotExistException;
import java.util.ArrayList;
//import java.time.LocalTime;

/**
 *
 * @author eugenie_dalmas
 */
abstract public class Waiter extends Human implements Employee, Gender {
    
    /**
     * Constructor.
     * @param bar
     * @param name
     * @param surname
     * @param wallet
     * @param popularity
     * @param shout 
     */
    public Waiter(Bar bar, String name, String surname, double wallet, int popularity, String shout) {
        super(bar, name, surname, wallet, popularity, shout);
    }
    
    /**
     * Drink but only if it is water.
     * @param drink of any kind, can refuse
     */
    @Override
    void drink(Drink drink) {
        if (!drink.getName().equals("Water")) {
            speak("I can't drink "+drink.getName()+", this is not water.", null);
        }
        else {
            speak("A little water sure helps", null);
        }
    }
    
    /**
     * Announce the speaker for the user and write the wanted message.
     * @param message to be said
     * @param to ; is null when speaking to no one in particular
     */
    @Override
    public void speak(String message, Human to) {
        System.out.print(this.name+" > ");
        if (to!= null) {
            System.out.print(to.getName()+", ");
        }
        System.out.println(message);
    }
    
    /**
     * Buy a drink for another fellow human and maybe change its popularity.
     * @param to the human that receive the drink
     */
    @Override
    public void offerDrink(Client to) {        
        Drink drink = to.favoriteDrink;
        double rand1 = Math.random();
        double rand2 = Math.random();
        if (rand1 <= 0.1) {
            speak("was it ?", to);
        }
        speak("I'll offer you a drink. What's your favorite one ?", to);
        // if accept
        if (rand2 <= 0.5) {
            to.speak("I like "+drink.getName(), null);
            to.takeDrinkOffer(drink, this);
            // pay the drink
            if (!serve(drink, to)) {
                // try with 2nd drink
                speak("I'll ask for another drink", null);
                drink = to.favoriteDrink2nd;
                if (!serve(drink, to)) {
                    speak("there is no more drink you like...", to);
                    if (rand2 <= 0.02 && popularity > 0) popularity -= 1;
                }
            }
            speak("Your welcome", null);
            if (rand2 <= 0.02 && popularity < 10) popularity += 1;
        }
        else {
            to.refuseDrinkOffer(this);
            if (rand2 <= 0.02 && popularity > 0) popularity -= 1;
        }
    }
    
    // Employee -----------
    /**
     * Ask the barman for the drink and serve it to the client.
     * @param drink
     * @param to 
     * @return a Boolean 
     */
    @Override
    public Boolean serve(Drink drink, Client to) {
        Barman barman = currentBar.getBarmans().get((int) (Math.random() * currentBar.getBarmans().size()));
        speak("One "+drink.name+" for "+to.name+" !", barman);
        // barman remove from stock
        Boolean res = barman.takeFromStock(drink);
        if (res) {
            barman.speak("the " + drink.getName(), this);
            barman.aksToPay(drink.getSellingPrice(), to);
              // serve the drink
            speak("Here is your order !", null);
            to.speak("Thanks", null);
            return true;
        }
        else {
            speak("There is no more "+drink.getName()+". Another drink perhaps ?", null);
            return false;
        }
    }
    
    /**
     * Serve the round, all employees are used.
     * Clients drink in this method for coherent linear dialogs output
     * @param bar where the round happens
     * @param drink that is served
     * @param clients that are present in the bar
     */
    public static void serveRound(Bar bar, Drink drink, ArrayList<Client> clients) {
        for (int i = 0; i<clients.size(); i++) {
            Waiter waiter = bar.getWaiters().get(i %bar.getWaiters().size());
            Barman barman = bar.getBarmans().get(i % bar.getBarmans().size());
            barman.takeFromStock(drink);
            waiter.speak("Here is the drink for the round !", null);
            clients.get(i).speak(clients.get(i).getShout()+" Thanks !", null);
            clients.get(i).drink(drink);
        }
    }
    
     /**
     * Try to kick a client out of the pub depending on the employee's ability 
     * and the client's resistance.
     * @param client to kick out
     * @return a Boolean for success/failure
     */
    @Override
    public Boolean kickOut(Client client) {
        speak("Sorry paw but you need to leave now.", null);
        return false;
    }
    
    /**
     * Try to stop a client from drinking more.
     * @param client to say stop to
     * @return a Boolean for success/failure
     */
    @Override
    public Boolean sayStop(Client client) {
        speak("You should stop drinking for now paw.", null);
        return false;
    }
    
    // Gender ------------
    /**
     * Tell of what gender is the waiter.
     * @return the string female or male
     */    
    @Override
    public abstract String tellGender();
    
    /**
     * Change the gender of the waiter.
     */
    @Override
    public abstract void changeGender();
    
    // Management ----------
    /**
     * Run an action given probabilities between all the ones the waiter can 
     * start alone.
     * @param clients that are present in the pub.
     */
    @Override
    public void action(ArrayList<Client> clients) {
        double randAction = Math.random();
        // - drink water
        if (randAction >= 0.7) {
            try {
                drink(Bar.getDrink("Water"));
            }
            catch (DoesNotExistException e) {
                System.out.println(e.getMessage());
            }
        }
        // - offer a drink to a client
        else if (randAction < 0.1) {
            if (clients.size()>0){
                offerDrink(clients.get((int) (Math.random()*clients.size())));
            }
        }
        // else do nothing
    }
    
    /**
     * Displays all the informations on the waiter for the user.
     * Used in the management class for Information menu
     */
    @Override
    public void displayInformation() {
        System.out.println("[Waiter]    "+name+" "+surname);
        System.out.println("   "+tellGender());
        System.out.println("    Wallet balance: "+wallet);
        System.out.println("    Popularity: "+popularity);
        System.out.println("    Work in "+currentBar.getName());
        System.out.println("    Allowed drinks: Water");
    }
    
    
}
