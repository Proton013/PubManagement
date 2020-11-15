/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pubmanagement;

import Exceptions.SelfInteractionException;

import java.time.LocalTime;
import java.util.ArrayList;

/**
 *
 * @author eugenie_dalmas
 */
abstract public class Client extends Human implements Gender {
    
    /**
     * Threshold of alcohol level where beyond is considered too drunk to be
     * acceptable.
     */
    public static final int ALCOHOL_THRESHOLD = 8; // too drunk to be acceptable
    
    /**
     * The favorite drink of the client.
     */
    protected final Drink favoriteDrink;
    /**
     * The second favorite drink of the client.
    */
    protected final Drink favoriteDrink2nd;
    /**
     * The alcohol level of this client.
     */
    protected int alcoholLevel = 0;
    /**
     * The belote level of the client.
     */
    protected final int beloteLevel;
    // private final double drinkingSpeed; = random ?
    
    /**
     * The cumulatited bill for unpaid drinks.
     */
    private double bill = 0;
    
    /**
     * Tell if the client is in the bar or out.
     */
    private Boolean isIn = false;
    
    /**
     * Constructor that extends Human constructor.
     * @param bar
     * @param name
     * @param surname
     * @param wallet starting balance
     * @param popularity level
     * @param shout
     * @param favoriteDrink name
     * @param favoriteDrink2nd name
     * @param beloteLevel
     */
    public Client(Bar bar, String name, String surname, double wallet, int popularity, String shout,
            Drink favoriteDrink, Drink favoriteDrink2nd, int beloteLevel) {
        
        super(bar, name, surname, wallet, popularity, shout);
        this.favoriteDrink = favoriteDrink;
        this.favoriteDrink2nd = favoriteDrink2nd;
        this.beloteLevel = beloteLevel;
    }
    
    /**
     * Announce the speaker for the user and write the wanted message.
     * @param message to be said
     * @param to ; is null when speaking to no one in particular
     */
    @Override
    public void speak(String message, Human to) {
        if (this.alcoholLevel >= ALCOHOL_THRESHOLD - 4) {
            speakTipsy(message, to);
        }
        else {
            super.speak(message, to);
        }
    }
    
    /**
     * Speak but add a "nickname" to the one he is speaking to.
     * @param message
     * @param to someone ; is null when speaking to no one in particular
     */
    public void speakTipsy(String message, Human to) {
        super.speak(message, null);
        if (to instanceof MaleClient || to instanceof MaleWaiter) {
            System.out.print(" handsome");
        }
        else if (to instanceof FemaleClient || to instanceof FemaleWaiter) {
            System.out.print(" sweet heart");
        }
        // not usually appreciated
        double rand = Math.random();
        if (rand <= 0.03) this.popularity -= 1;
    }
    
    /**
     * Modify the alcohol level.
     * @param drink 
     */
    @Override
    public void drink(Drink drink) {
        this.alcoholLevel += drink.alcohol;
        
        if (alcoholLevel >= ALCOHOL_THRESHOLD) {
            // is drunk, employee immediatly spot him
            currentBar.getBoss().orderStop(this);
        }
    }
    
    /**
     * Pay the cost for any payment, here a drink.
     * @param cost
     * @return the amount that was paid
     */
    @Override
    public double pay(double cost) {
        return super.pay(cost);
    }
    
    /**
     * Buy a drink for another fellow client and maybe change its popularity.
     * @param to the client that receive the drink
     */
    @Override
    public void offerDrink(Client to) throws SelfInteractionException {
        if (to == this) throw new SelfInteractionException("Tried to offer a drink to himself.");
        Drink drink = to.favoriteDrink;
        double rand1 = Math.random();
        double rand2 = Math.random();
        if (rand1 <= 0.1) {
            speak("was it ?", to);
        }
        speak("I'll offer you a drink. What's your favorite one ?", to);
        // if accept
        if (rand2 <= 0.5) {
            to.speak("I like "+drink, null);
            // pay the drink
            if (!orderDrink(drink)) {
                // try with 2nd drink
                speak("I'll ask for another drink", null);
                drink = to.favoriteDrink2nd;
                if (!orderDrink(drink)) {
                    speak("seems like I can't buy you this drink...", to);
                    to.speak("That's a shame", to);
                    if (rand2 <= 0.02 && popularity > 0) popularity -= 1;
                }
            }
            to.takeDrinkOffer(drink, this);
            if (rand2 <= 0.02 && popularity < 10) popularity += 1;
        }
        else {
            to.refuseDrinkOffer(this);
            if (rand2 <= 0.02 && popularity > 0) popularity -= 1;
        }
    }
    
    /**
     * Buy a drink for a waiter and maybe change its popularity.
     * This will offer his favorite drink and the waiter will refuse if not water
     * @param to the waiter that receive the drink
     */
    public void offerDrink(Waiter to) {
        Drink drink = this.favoriteDrink;
        double rand1 = Math.random();
        double rand2 = Math.random();
        if (rand1 <= 0.1) {
            speak("was it ?", to);
        }
        speak("I'll offer you a drink.", to);
        // if not water refuse and more or less well taken
        if (rand2 <= 0.6 || this.alcoholLevel>7) { // bad
            to.refuseDrinkOffer(this);
            speak("Too bad for you", null);
            if (rand2 <= 0.02 && popularity > 0) popularity -= 1;
        }
        else { // good -> accept if water
            if (drink.getName().equals("Water")) {
                to.speak("Oh thank you !", null);
                to.drink(drink);
            }
            else {
                to.refuseDrinkOffer(this);
                speak("That's a shame", null);
            }
            if (rand2 <= 0.02 && popularity > 0) popularity += 1;
        }
    }
    
    /**
     * Accept the drink offer.
     * @param drink
     * @param from 
     */
    public void takeDrinkOffer(Drink drink, Human from) {
        speak("thanks for the drink !", from);
        from.speak("Your welcome", null);
        drink(drink);
    }
    
    /**
     * Order a drink to a waiter or barman, if available, that he will pay later 
     * (waiter) or right away (barman).
     * @param drink
     * @return false if drink succefully oredered (drink is in stock)
     */
    public Boolean orderDrink(Drink drink) {
        if (this.getWalletBalance()+this.getBill() - drink.sellingPrice < 0) return false;
        Employee employee = currentBar.getEmployees().get((int) (Math.random()*currentBar.getEmployees().size()));
        speak("S'cuse me ! I'd like one "+drink.getName()+" please", null);
        if (employee instanceof Waiter waiter) {
            waiter.speak("No problem, getting that for you !", null);
            // waiter serve
            if (!waiter.serve(drink, this)) return false;
            // add to the bill
            this.bill += drink.getSellingPrice();
            return true;
        }
        else if (employee instanceof Barman barman) {
            barman.speak("And one "+drink.getName()+" in coming", null);
            // then barman serves the client
            if (!barman.serve(drink, this)) return false;
            // askToPay right away the drink and the bill
            barman.aksToPay(drink.getSellingPrice()+this.bill, this);
            return true;
        }
        return false;
    }
    
    /**
     * Offer a round to all present clients (including himself).
     * @param drink that is offered
     * @return true if the round was successfully offered
     */
    public Boolean offerRound(Drink drink) {
        // count how many client for total bill
        ArrayList<Client> present = currentBar.getPresentClients();
        double cost = present.size()*drink.getSellingPrice();
        if ((this.getWalletBalance()-cost < 0) || (currentBar.getStock(drink)<present.size())) return false;
        else {
            speak("It's my round !!", null);
            for (int i = 0; i<present.size(); i++) {
                // clients shout
                present.get(i).speak(present.get(i).getShout(), null);
            }
            currentBar.getBoss().speak("Bussiness is still on people", null);
            // employee serve everywhere and everybody drink
            Waiter.serveRound(currentBar, drink, present);
            this.popularity += 1;
            return true;
        }
    }
    
    /**
     * Enters the pub.
     */
    public void enterPub() {
        System.out.println("!!!! in enter pub method !!!!");
        // added to the tables if not already in the pub
        while (!isIn) {
            for(int i = 0; i<currentBar.getTables().size(); i++) {
                for (int j = 0; j<currentBar.getTables().get(i).length; j++) {
                    if (currentBar.getTables().get(i)[j] == null) {
                        currentBar.getTables().get(i)[j] = this;
                        this.isIn = true;
                    }
                }
            }
        }
        if (!isIn) {
            System.out.println("("+this.name+" "+this.surname
                    +" tried to join but the pub is full)");
        }
        else {
            System.out.println(this.name+" "+this.surname+" joined");
        }
    }
    
    /**
     * Leaves the pub.
     */
    public void leavePub() {
        int i = 0;
        while (i<currentBar.getEmployees().size()) {
            Employee employee = currentBar.getEmployees().get(i);
            if (employee instanceof Barman barman && bill != 0) {
                barman.aksToPay(bill, this);
            }
        }
        // removed from the tables
        for(i = 0; i<currentBar.getTables().size(); i++) {
            for (int j = 0; j<currentBar.getTables().get(i).length; j++) {
                if (currentBar.getTables().get(i)[j] == this) {
                    currentBar.getTables().get(i)[j] = null;
                    this.isIn = false;
                }
            }
        }
        System.out.println(this.name+" "+this.surname+" left");
        // alcohol level is reset
        this.alcoholLevel = 0;
    }
    
    /**
     * Client is kicked out of pub.
     * @param employee 
     */
    public void kickedOut(Employee employee) {
        if (employee.kickOut(this)) {
            // is removed from the pub, the table and pay his bill
            leavePub();
        }
    }
    
    // Gender ------------
    /**
     * Tell of what gender is the client.
     * @return the string female or male
     */    
    @Override
    public String tellGender() {
        if (this instanceof FemaleClient) {
            return "female";
        }
        else {
            return "male";
        }
    }
    
    /**
     * Change the gender of the client.
     */
    @Override
    public void changeGender() {
        Client newThis = this;
        // get infos and create a new Male/Female-Waiter 
        if (this instanceof FemaleClient) {
            String color = Bar.COLORS.get((int) (Math.random()*Bar.COLORS.size()));
            String teeShirt = color + " tee-shirt";
            newThis = new MaleClient(this.currentBar,this.name, this.surname, this.wallet, 
                    this.popularity, this.shout, this.favoriteDrink, 
                    this.favoriteDrink2nd, this.beloteLevel, teeShirt);
        }
        else if (this instanceof MaleClient) {
            ArrayList<String> accessories = new ArrayList<>(); // random to found
            newThis = new FemaleClient(this.currentBar, this.name, this.surname, this.wallet, 
                    this.popularity, this.shout, this.favoriteDrink, 
                    this.favoriteDrink2nd, this.beloteLevel, accessories);
        }
        // then replace the obsolete one
        if (currentBar.removeClient(this)) currentBar.addClient(newThis);
    }
    
    // Management -----------
    /**
     * Run an action given probabilities between all the ones the client can 
     * start alone.
     * @param clients that are present in the pub
     */
    @Override
    public void action(ArrayList<Client> clients) {
        ArrayList<Waiter> waiters = currentBar.getWaiters();
        double randAction = Math.random();
        if (!getIsIn()) {
            // - enters pub
            if (randAction > 0.5) enterPub();
            // - change gender
            else if (randAction <= 0.01) {
                changeGender();
            }
        }
        else { // do one of the following actions
            // - order a drink
            if (randAction >= 0.5) { 
                if (!orderDrink(favoriteDrink)) {
                    orderDrink(favoriteDrink2nd);
                }
            }
            // - offer a drink to someone
            else if (randAction >= 0.4 && randAction<0.5) {
                double randHuman = Math.random();
                if (randHuman > 0.3 && clients.size()>0){ // -- to client
                    try {
                        offerDrink(clients.get((int) (Math.random()*clients.size())));
                    }
                    catch (SelfInteractionException e) {
                        System.out.println(e.getMessage());
                    }
                }
                else { // -- to waiter
                    // probability of offer differs on the waiters charm/bicep
                    Waiter tmp = waiters.get((int) (Math.random()*clients.size()));
                    if (tmp instanceof FemaleWaiter to) {
                        // more likely to make an offer if charm is higher
                        if ((int) (Math.random()*10) <= to.getCharm()) offerDrink(to);
                    }
                    else if (tmp instanceof MaleWaiter to) {
                        // less likely to make an offer if biceps is higher
                        if ((int) (Math.random()*10) >= to.getBiceps()) offerDrink(to);
                    }
                }
            }
            // - offer his round
            else if (randAction >= 0.35 && randAction<0.4 && clients.size()>0) { 
                // try with his favorites drinks
                if (!offerRound(favoriteDrink)) { 
                    offerRound(favoriteDrink2nd);
                }
            }
            // - leaves the pub
            else if (randAction >= 0.3 && randAction <0.35) leavePub();
            
            // else do nothing
        }
    }
    
    
    // ----- Getters -----
    /**
     * Get the favorite drink.
     * @return 
     */
    public Drink getFavoriteDrink() {
        return this.favoriteDrink;
    }
    
    /**
     * Get the second favorite drink.
     * @return 
     */
    public Drink get2ndFavoriteDrink() {
        return this.favoriteDrink2nd;
    }
    
    /**
     * Get the alcoghol level.
     * @return 
     */
    public int getAlcoholLevel() {
        return this.alcoholLevel;
    }
    
    /**
     * Get the belote level.
     * @return 
     */
    public int getBeloteLevel() {
        return this.beloteLevel;
    }
    
    /**
     * Get the bill (total cost).
     * @return 
     */
    public double getBill() {
        return this.bill;
    }
    
    /**
     * Tell if the client is in the bar or out.
     * @return 
     */
    public Boolean getIsIn() {
        return this.isIn;
    }
}
