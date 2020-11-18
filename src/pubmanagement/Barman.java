/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pubmanagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
//import java.util.LinkedList;
//import java.time.LocalTime;

/**
 *
 * @author eugenie_dalmas
 */
public class Barman extends Human implements Employee {
    
    /**
     * Constructor.
     * @param bar to access to the bar's attributes (till and stock)
     * @param name
     * @param surname
     * @param wallet
     * @param popularity
     * @param shout 
     */
    public Barman(Bar bar, String name, String surname, double wallet, int popularity, String shout) {
        super(bar, name, surname, wallet, popularity, shout);
    }
    
    /**
     * Announce the speaker for the user and write the wanted message.
     * @param message to be said
     * @param to ; is null when speaking to no one in particular
     */
    @Override
    public void speak(String message, Human to) {
        System.out.print("< Barman "+this.name+" > ");
        if (to!= null) {
            System.out.print(to.getName()+", ");
        }
        System.out.println(message);
    }
    
    /**
     * Drink but only if it is water.
     * @param drink of any kind, can refuse
     */
    @Override
    public void drink(Drink drink) {
        if (drink.getAlcohol() != 0) {
            speak("I can't drink "+drink.getName()+", there is alcohol.", null);
        }
        else {
            if (takeFromStock(drink)) { // remove from stocks
                speak("A "+ drink.getName()+", how refreshing", null);
            }
        }
    }
    
    /**
     * Pay the cost for any payment.
     * @param cost
     * @return the amount that was paid
     */
    @Override
    public double pay(double cost) {
        currentBar.setTillBalance(currentBar.getTillBalance() - cost);
        System.out.println("    # Bar "+currentBar.getName()+" paid: "+ cost
                +" -> Remaining: " + currentBar.getTillBalance());
        return cost;
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
                if (!serve(drink, to) || (currentBar.getTillBalance() - drink.getSellingPrice() > 0)) {
                    speak("seems like I can't buy you this drink...", to);
                    if (rand2 <= 0.02 && popularity > 0) popularity -= 1;
                }
            }
            pay(drink.getSellingPrice());
            speak("Your welcome", null);
            if (rand2 <= 0.02 && popularity < 10) popularity += 1;
        }
        else {
            to.refuseDrinkOffer(this);
            if (rand2 <= 0.02 && popularity > 0) popularity -= 1;
        }
    }
    
    /**
     * Gives the overflow of money from the till depending on the limit hardcoded.
     * @param boss to give to ( may be supressed and directly getBoss() )
     */
    public void giveOverflow(Boss boss) {
        // calculate overflow
        double overflow = currentBar.getTillBalance() - Bar.TILL_LIMIT;
        // remove overflow from till
        currentBar.setTillBalance(currentBar.getTillBalance() - overflow);
        System.out.println("    # Till - "+overflow+" -> "+currentBar.getTillBalance());
        // give to boss
        speak("Boss, that's the overflow", null);
        boss.takeOverflow(overflow);
        boss.speak("Thanks", null);
    }
    
    /**
     * Take the money from a client for his drink.
     * @param cost to pay
     * @param client 
     */
    public void aksToPay(double cost, Client client) {
        // pay
        double paid = client.pay((double) Math.round(cost)+1); // pays only superior long values
        // add to till
        currentBar.setTillBalance(currentBar.getTillBalance() + paid);
        giveChange(cost, paid, client);
        System.out.println("    # Till + "+cost+" -> "+currentBar.getTillBalance());
        speak("Here is your receipt", null);
        client.speak("Thank you", null);
    }
    
    /**
     * Give the change;
     * @param cost
     * @param give
     * @param client 
     */
    public void giveChange(double cost, double give, Client client) {
        double change = give - cost;
        client.wallet += change; // can access whitout getWallet => pb
        System.out.println("    # "+client.name+" "+client.surname+" paid: "+ cost 
                +" -> Remaining: " + wallet);
    }
    
    /**
     * Order the wanted quantities for low amount of drinks and takes the delivery.
     * from the supplier.
     * @param supplier 
     */
    public void orderSupply(Supplier supplier) {        
        Map<String, Integer> order = toOrder();
        supplier.deliver(order, this); // stock refilled
        System.out.println("Right on time !");
        System.out.println("Here's your payment");
        supplier.getPaid(this, ((double) order.get("total_cost"))/100);
    }
    
    /**
     * Calculate the quantities to order for each drink that has a low quantity.
     * @return in an HashTable (drinkName, quantity) to order and the total cost
     */
    public Map<String, Integer> toOrder() {
        Map<String, Integer> toOrder = new HashMap<>();
        double totalCost = 0;
        
        while (totalCost <= currentBar.getTillBalance() - 100) {
            // add wanted quantities and drinks' name to oder map        
            for (int i = 0; i<Bar.getDrinksMenu().size(); i++) {
                if (currentBar.getStock(Bar.getDrinksMenu().get(i)) <= Bar.LOW_QUANTITY_LIMIT) {
                    totalCost += Bar.getDrinksMenu().get(i).getPurchasingPrice();
                    toOrder.put(Bar.getDrinksMenu().get(i).getName(),
                            Bar.HIGH_QUANTITY_LIMIT - currentBar.getStock(Bar.getDrinksMenu().get(i)));
                }
            }
        }
        toOrder.put("total_cost*100", (int) totalCost*100); // 
        
        return toOrder;
    }
    
    /**
     * Take a drink form the stocks, thus reduce its quantity.
     * @param drink
     * @return false if there is no more of the given drink
     */
    public Boolean takeFromStock(Drink drink) {
        int oldValue = currentBar.getStocks().get(drink.getName());
        if (oldValue == 0) {
            speak("There's no more " + drink.getName() + "... We'll have to order soon", null);
            currentBar.setRunOutDrinks(currentBar.getRunOutDrinks() + 1);
            if (currentBar.getRunOutDrinks() == 3) orderSupply(Bar.getSupplier());
            return false;
        }
        else {
            currentBar.getStocks().put(drink.getName(), oldValue - 1);
            return true;
        }
    }
    
    /**
     * Update the quantities of delivered drinks in the stocks.
     * @param delivery Map of drink delivered and their ordered quantities
     */
    public void refillStocks(Map<String, Integer> delivery) {
        ArrayList<String> deliveryDrinks = new ArrayList<>(delivery.keySet());
        ArrayList<String> stockDrinks = new ArrayList<>(currentBar.getStocks().keySet());
        
        for (int i = 0; i < deliveryDrinks.size(); i ++) {
            for (int j = 0; j < stockDrinks.size(); j ++) {
                if (deliveryDrinks.contains(stockDrinks.get(j))) {
                // add quantities until the limit to drinks that were delivered
                    currentBar.getStocks().replace(stockDrinks.get(j), Bar.HIGH_QUANTITY_LIMIT);
                }
            }
        }
    }
    
    // Employee ----------
    /**
     * Serve the drink to the client.
     * @param drink
     * @param to 
     * @return a Boolean
     */
    @Override
    public Boolean serve(Drink drink, Client to) {
        // barman remove from stock
        if (takeFromStock(drink)) {
            speak("The " + drink.getName() +" !", null);
            // serve the drink
            speak("Here is your order", null);
            to.speak("Thanks.", null);
            return true;
        }
        else {
            speak("There is no more "+drink.getName()+". Another drink perhaps ?", null);
            return false;
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
        // no conditions, is called after waiters
        speak("Sorry paw but you need to leave now.", null);
        client.speak("Alright, alright, I'll leave...", null);
        return true;
    }
    
    /**
     * Try to stop a client from drinking more.
     * @param client to say stop to
     * @return a Boolean for success/failure
     */
    @Override
    public Boolean sayStop(Client client) {
        speak("You should stop drinking for now paw.", null);
        // no condition, is called after waiters
        client.speak("Alright, alright, I'll stop...", null);
        client.setRedFlag(true);
        return true;
    }
    
    // Management ------------
    /**
     * Run an action given probabilities between all this ones the barman can
     * start alone.
     * @param clients that are present in the pub
     */
    @Override
    public void action(ArrayList<Client> clients) {
        double randAction = Math.random();
        // - drink non alcohol drinks
        if (randAction >= 0.7) {
            Drink drink = Bar.getDrinksMenu().get((int) (Math.random()*Bar.getDrinksMenu().size()));
            if (drink.getAlcohol() == 0) drink(drink);
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
     * Displays all the informations on the barman for the user.
     * Used in the management class for Information menu
     */
    @Override
    public void displayInformation() {
        System.out.println("[Barman]    "+name+" "+surname);
        System.out.println("    Wallet balance: "+wallet);
        System.out.println("    Popularity: "+popularity);
        System.out.println("    Work in "+currentBar.getName());
        System.out.println("    Allowed drinks: Non alcoholic drinks");
    }
    
}           
