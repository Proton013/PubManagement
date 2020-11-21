/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Belote;

import pubmanagement.Human;

/**
 *
 * @author eugenie_dalmas
 */
public class Player {
    
    private Human human;
    private String name;
    private int order;
    private Boolean isAI;
    
    public Player(Human human, Boolean isAI) {
        this.human = human;
        this.isAI = isAI;
        
        if (human == null) name = "user";
        else name = human.getName();
    }
    
    
    public void loserAction(Player[] winners) {
        // offerDrink to winners
        // beloteLevel -- (proba 0,3)
        // popularity -- (proba 0,3)
    }
    
    public void winnerAction() {
        // beloteLevel ++ (proba 0,3)
        // popularity ++ (proba 0,5)
           
        // else :
           // wallet += registered poll /2 /2 (50% for whole team)
           // !! boss wallet + 50% but not here...
           // beloteLevel ++ (proba 0,3)
           // popularity ++ (proba 0,5)
    }
    
    public Human getHuman() {
        return human;
    }
}
