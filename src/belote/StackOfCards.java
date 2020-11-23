/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package belote;


import java.util.ArrayList;
import java.util.Collections;
/**
 * Our stack of cards using the class Card
 * @author ploui
 */
public class StackOfCards extends ArrayList<Card> {
    
    /**
     * Construct a stack of cards
     */
    public StackOfCards() {
        //super();
        for (Value v : Value.values()) {
            for (Color c : Color.values())
                add(new Card(v, c));
        }
    }
    
    
    /**
     * Shuffle the stack of cards
     */
    public void shuffle() {
        Collections.shuffle(this);
    }
   
    /** 
     * 
     * @param n
     * @return n cards without taking them off the stack
     */
    public ArrayList<Card> pick(int n) {
        if (n > size())
            throw new RuntimeException("le paquet comporte moins de " +n+ " cartes");
        ArrayList<Card> returned = new ArrayList<>();
        for (int i=0;i<n;i++)
            returned.add(get(i));
        return returned; // cartes retournees
    }
    
/**
 * 
 * @return the card at the top of the stack
 */
    public Card pick() {
        return pick(1).get(0);
    }
    
    /**
     * remove n cards on the top of the stack
     * @param n
     */
    public void pullCard(int n) {
        removeAll(pick(n));
    }
    
    /**
     * remove one card at the top of the stack
     */
    public void pullCard() {
        pullCard(1);
    }
}

