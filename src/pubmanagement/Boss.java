/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pubmanagement;

import Exceptions.SelfInteractionException;
import java.util.ArrayList;

/**
 *
 * @author eugenie_dalmas
 */
public class Boss extends Client {
    
    /**
     * Constructor.
     * @param bar
     * @param name
     * @param surname
     * @param wallet
     * @param popularity
     * @param shout
     * @param favoriteDrink
     * @param favoriteDrink2nd
     * @param beloteLevel 
     */
    public Boss(Bar bar, String name, String surname, double wallet, int popularity, String shout,
            Drink favoriteDrink, Drink favoriteDrink2nd, int beloteLevel) {
        super(bar, name, surname, wallet, popularity, shout, favoriteDrink, favoriteDrink2nd, beloteLevel);
    }
    
    /**
     * Order an employee to stop a client from drinking.
     * @param client
     */
    public void orderStop(Client client) {
        Boolean outcome = false;
        while (!outcome) {
            // first ask waiters
            for(int i = 0; i<currentBar.getWaiters().size(); i++) {
                Waiter waiter = currentBar.getWaiters().get(i);
                speak("Seems like "+client.getName()+" needs to slow down a little...", waiter);
                waiter.speak("I'll take care of it boss", null);
                outcome = waiter.sayStop(client); // result (success or failure)
            }   
            // then ask barmans
            for(int i = 0; i<currentBar.getBarmans().size(); i++) {
                Barman barman = currentBar.getBarmans().get(i);
                speak("Seems like "+client.getName()+" needs to slow down a little...", barman);
                barman.speak("I'll take care of it boss", null);
                outcome = barman.sayStop(client); // result (success or failure)
            }  
        }
    }
    
    /**
     * Order an employee to kick out from the pub a client.
     * @param client
     */
    public void orderKickOut(Client client) {
        Boolean outcome = false;
        while (!outcome) {
            // first ask waiters
            for(int i = 0; i<currentBar.getWaiters().size(); i++) {
                Waiter waiter = currentBar.getWaiters().get(i);
                speak("Seems like "+client.getName()+" should cool down outside...", waiter);
                waiter.speak("I'll take care of it boss", null);
                outcome = waiter.kickOut(client); // result (success or failure)
            }   
            // then ask barmans
            for(int i = 0; i<currentBar.getBarmans().size(); i++) {
                Barman barman = currentBar.getBarmans().get(i);
                speak("Seems like "+client.getName()+" should cool down outside...", barman);
                barman.speak("I'll take care of it boss", null);
                outcome = barman.kickOut(client);
            }  
        }
    }
    
    /**
     * Add to her wallet the overflow from the till given by a barman.
     * @param overflow a double
     */
    public void takeOverflow(double overflow) {
        this.wallet += overflow;
    }
    
    /**
     * Run an action given probabilities between all the ones the boss can 
     * start alone.
     * @param clients that are present in the pub
     */
    @Override
    public void action(ArrayList<Client> clients) {
        double randAction = Math.random();
        // - order a drink
        if (randAction >= 0.5) { 
            if (!orderDrink(favoriteDrink)) {
                orderDrink(favoriteDrink2nd);
            }
        }
        // - offer a drink to a client
        else if (randAction >= 0.4 && randAction<0.5) {
            if (clients.size()>0){ // -- to client
                try {
                    offerDrink(clients.get((int) (Math.random()*clients.size())));
                }
                catch (SelfInteractionException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        // - offer her round
        else if (randAction >= 0.35 && randAction<0.4 && clients.size()>0) { 
            // try with his favorites drinks
            if (!offerRound(favoriteDrink)) { 
                offerRound(favoriteDrink2nd);
            }
        }
        // else do nothing
    }
    
}
