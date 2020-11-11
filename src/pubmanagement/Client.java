/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pubmanagement;

import java.time.LocalTime;

/**
 *
 * @author eugenie_dalmas
 */
abstract public class Client extends Human {
    
    protected final Drink favoriteDrink;
    protected final Drink favoriteDrink2nd;
    protected int alcoholLevel = 0;
    protected final int beloteLevel;
    // private final double drinkingSpeed; = random ?
    private double bill = 0;
    
    private Boolean isIn = false;
    
    public static final int ALCOHOL_THRESHOLD = 8; // too drunk to be acceptable
    
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
            // is tipsy
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
        setDateLastAction(LocalTime.now());
        this.alcoholLevel += drink.alcohol;
        // => drinkingSpeed*timeOffset
        // if (now - dt_drink < drinkingSpeed*timeOffset) -> still drinking
        // else -> can have another drink
        
        if (alcoholLevel >= ALCOHOL_THRESHOLD) {
            // is drunk, employee immediatly spot him
            currentBar.getBoss().orderStop(this);
        }
    }
    
    /**
     * Pay the given drink.
     * @param cost
     * @return the amount that was paid
     */
    @Override
    public double pay(double cost) {
        return super.pay(cost);
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
            to.speak("I like "+drink, null);
            // pay the drink
            if (!orderDrink(drink)) {
                // try with 2nd drink
                speak("I'll ask for another drink", null);
                drink = to.favoriteDrink2nd;
                if (!orderDrink(drink)) {
                    speak("there is no more drink you like...", to);
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
        int i = 0;
        while(i<currentBar.getEmployees().size()) { // && !ordered
            Human employee = (Human) currentBar.getEmployees().get(i);
            if (!employee.isOccupied()) {
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
            }
            i++;
        }
        return false;
    }
    
    public void offerRound(Drink drink) {
        // count how many client for total bill
        // offerDrink to everyone that can drink the drink
        // barman shout "general round" or smth 
        // boss "seems like business is still on"
        // employee serve everywhere 
        // clients shout 
        // everybody drink
        // popularity += 1
    }
    
    /**
     * Enter the pub.
     */
    public void enterPub() {
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
     * Leave the pub.
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
    
    // ----- Getters -----
    public Drink getFavoriteDrink() {
        return this.favoriteDrink;
    }
    public Drink get2ndFavoriteDrink() {
        return this.favoriteDrink2nd;
    }
    public int getAlcoholLevel() {
        return this.alcoholLevel;
    }
    public int getBeloteLevel() {
        return this.beloteLevel;
    }
    public Boolean getIsIn() {
        return this.isIn;
    }
}
