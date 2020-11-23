/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package belote;

import exceptions.SelfInteractionException;
import java.util.ArrayList;
import pubmanagement.Client;
import pubmanagement.Human;

/**
 *
 * @author ploui
 */
public class Player {   
    
    /**
     * Player Name.
     */
    private String name;
    
    /**
     * Number of the player.
     */
    private int number;
    
    /**
     * Associated Human to the player.
     */
    private Client human;
    
    /**
     * Cards in the hand of the player.
     */
    private ArrayList<Card> hand = new ArrayList<Card>();
    
    /**
     * Cards won by the player.
     */
    private ArrayList<Card> wonCards = new ArrayList<Card>();
    
    /**
     * Boolean to know if the user is playing or not.
     */
    private Boolean isAI;
    
    /**
     * Constructor of Player.
     * @param human
     * @param isAI 
     */
    public Player(Client human, boolean isAI) {
        this.human = human;
        this.name = human.getName();
        this.isAI = isAI;
    }   
    
    /**
     * Actions that are done when the player loses a tournament.
     * @param winners 
     */
    public void loserAction(Player[] winners) {
        if (Math.random() < 0.3 && human.getPopularity() > 0) {
            human.setPopularity(human.getPopularity() - 1);
        }
        // offerDrink to winners
        try {
            for (int i = 0; i<winners.length; i++) human.offerDrink((Client) winners[i].human);
        }
        catch(SelfInteractionException e) {
            System.err.println(e.getMessage());
        }
    }
    
    /**
     * Actions that are done when the player wins a tournament.
     */
    public void winnerAction() {
        if (Math.random() < 0.3 && human.getPopularity() < 10) {
            human.setPopularity(human.getPopularity() + 1);
        }
    }
    
    /**
     *
     * @return true if the player is not a user
     */
    public Boolean getIsAI() {
        return isAI;
    }
    /**
     * 
     * @return the number of the player
     */
    public int getNumber() {
        return number;
    }
    /**
     * set the number of the player
     * @param number 
     */
    public void setNumber(int number) {
        this.number = number;
    }
    
    /**
     * 
     * @return the name of the player
     */
    public String getName() {
        return name;
    }
    
    /**
     * set the name of the player
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 
     * @return cards in the hand of the player
     */
    public ArrayList<Card> getHand() {
        return hand;
    }

    /**
     * return cards won by the player
     * @return 
     */
    public ArrayList<Card> getWonCards() {
        return wonCards;
    }
    
    /**
     * 
     * @param color
     * @return true if the player has at least 1 card of the asked color
     * else return false
     */
    public boolean hasAColor(Color color) {
        for (Card c : hand) {
            if (c.getColor() == color)
                return true;
        }
        return false;      
    }
    
    /**
     * Check if the player has a specific card in his hand
     * @param card
     * @return 
     */
    public boolean hasACard(Card card) {
        return hand.contains(card);
    }
    
    /**
     * Check values of cards won by a player depending of the color of the trump
     * @param trump
     * @return the points gained by the player during a trick
     */
    public int wonPoints(Color trump) {
        int points = 0;
        for (Card card : wonCards) {
            points += card.getValue(trump);
        }
        return points;
    }
   
    /**
     * Get the human associated with the player.
     * @return the Human
     */
    public Client getHuman() {
        return human;
    }
    
}

