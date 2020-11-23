/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package belote;

/**
 *
 * @author ploui
 */
public class Card {
    /**
     * Color of the card(coeur,pique,...)
     */
    private Color color;
    
    /**
     * Value of the card(7,8,9...)
     */
    private Value value;
    /**
     * Constructor of Card
     * @param value
     * @param color 
     */
    public Card(Value value, Color color) {
        this.color = color;
        this.value = value;
    }
    /**
     * 
     * @return the color of the card
     */
    public Color getColor() {
        return color;
    }
    /**
     * 
     * @return the value of the card
     */
    public Value getValue() {
        return value;
    }
    
    /**
     * @param trump
     * @return the number of points (int) that the card is worth
     * taking account of the color of the trump 
     */
    public int getValue(Color trump){
        if (color == trump){
            if (value == Value.VALET)
                return 20;
            if (value == Value.NEUF)
                return 14;
        }
        
        else if (value == Value.VALET) 
            return 2;
        
        if (value == Value.AS)
            return 11;
        if (value == Value.DIX)
            return 10;
        if (value == Value.ROI)
            return 4;
        if (value == Value.DAME)
            return 3;
       
        return 0;
    }
    
   /**
    * for a the trump   : V 9 A 10 R D 8 7
    * not a trump       : A 10 R D V 9 8 7
    * @return the number of the order of the card
    */
    public int getOrder(Color trump) {
        if (color == trump) {
            switch(value) {
            case VALET : return 7;
            case NEUF  : return 6;
            case AS    : return 5;
            case DIX   : return 4;
            case ROI   : return 3;
            case DAME  : return 2;
            case HUIT  : return 1;
            
            default  : return 0;
            }
        }
        else {
            switch (value) {
            case VALET : return 3;
            case NEUF  : return 2;
            case AS    : return 7;
            case DIX   : return 6;
            case ROI   : return 5;
            case DAME  : return 4;
            case HUIT  : return 1;
            
            default  : return 0;
            }
        }
   }
   
/**
 * Check if the card is superior taking account of the trump and the trick
 * @param card
 * @param trump
 * @param colorFold
 * @return 
 */
    public boolean isSuperior(Card card, Color trump, Color colorFold) {
        // if the card is not of the color of the trump and our card is
        if (color == trump && card.color != trump) return true;

        // vice versa
        if (color != trump && card.color == trump) return false;

        // if the card is not of the color of the trick and our card is
        if (color == colorFold && card.color != colorFold) return true;

        // vice versa
        if (color != colorFold && card.color == colorFold) return false;

        // else we compare order
        return (getOrder(trump) > card.getOrder(trump));
        
    }
    /**
     * print a card with value and color
     * @param card 
     */
    public void printCard(Card card){
        System.out.println(card.getValue() + " de " + card.getColor());
    }
    
    
    
}
