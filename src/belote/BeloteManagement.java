/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package belote;


import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.util.HashMap;
import pubmanagement.Bar;
import pubmanagement.Management;

/**
 * We update information during the game and print in
 * the terminal what we need
 * @author ploui
 */
public abstract class BeloteManagement {
 
    public static Bar bar;
    
    private static Scanner sc = new Scanner(System.in);
    
    /**
    * print an object in the terminal
    */
    private void show(Object obj){
	System.out.println(obj);
    }
    /**
     * Print the start of the game
     * @param bar wwhere the belote takes place
     */
    public void init(Bar bar){
        this.bar = bar;
        show("  #########################################");
        show("  ########### START OF THE GAME ###########");
        show("  #########################################");
        show("");
    }
    /**
     * Ask the user which card does he want to play (has to be in his hand of course)
     * Can leave the game here if input is 9
     * @param hand
     * @return card
     */ 
    public Card askCard(ArrayList<Card> hand){
        System.out.println("Which card do you want to play? (Put the number of the card : 1 - " + hand.size() + ") ");
        for (int i = 0 ; i < hand.size() ; i++)
            System.out.println((i+1) + " - " + hand.get(i).getValue() + " de " + hand.get(i).getColor());  // A CHANGER DEPEND DE LAFFICHAGE FINAL
        System.out.println("");
        System.out.println("[9] Return to Belote Menu ");
        System.out.println("");
        int c = 0; 
        
        while (c < 1 || c > hand.size()+1){
            
            try{
                c = sc.nextInt();
                if (c == 9){
                    Management.belote(bar);
                }
            }
            catch(Exception e1){
                 System.out.println("Put an integer please ");
                 sc.next();
                 continue;
            }
        }

        Card card = hand.get(c - 1);
        return card;
    }
    /**
     * Ask the player inside the pub which card he wants to play
     * @param hand
     * @return card
     */
    public Card askAICard(ArrayList<Card> hand){
        Random rand = new Random();
        int c = rand.nextInt(hand.size()) + 1;
        while (c < 1 || c > hand.size()){
            c = rand.nextInt(hand.size()) + 1;
        }
        Card card = hand.get(c - 1);
        return card;    
    }
    
    /**
     * update the current trump (Atout)
     * @param trump 
     */
    public void updateTrump(Card trump){
        show("The offered trump is " + trump.getValue() + " de " + trump.getColor());
        show(" ");
    }
    /**
     * update the last trick (Pli)
     * @param trick
     */
    public void updateLastTrick(ArrayList<String> trick){
        String s = "The last trick is : ";
        
        for(String value : trick){
            s += value + "\t";
	}       
        show(s);        
    }
    
    /**
     * Ask the user if he wants to take the trump
     * @return true if takes it, else return false
     */
    public boolean askTakeTrump(){
        char answer;
        do
        { // tant que la reponse n'est pas O ou N
                System.out.println("Do you want to take the trump ?");
                System.out.println("If you do, enter O, either enter N");
                answer = sc.next().charAt(0);
        } while (answer != 'O' && answer != 'N' && answer != 'o' && answer != 'n');
        if (answer == 'O' || answer == 'o')
            return true;
        else
            return false;
    }
    /**
     * Randomly choose the Trump for a player inside the pub
     * @return 
     */
    public boolean askAITakeTrump(){
        int answer;
        Random rand = new Random();
        answer = rand.nextInt(2);
        if (answer == 1) return true;
        else return false;
    }
    
    /**
     * choose the color of the trump
     * in the case none took the trump
     * @return Color chosen or null if trump is not taken
     */
    public Color askColorTrump(){
        int choice = 0;
        do{
        // while answer isn't between 1 and 5

            System.out.println("Do you want to take another color as trump?  ");
            System.out.println(
                            "1 - Coeur\n" +
                            "2 - Carreau\n" +
                            "3 - Trefle\n" +
                            "4 - Pique\n" +
                            "5 - No");

            choice  = sc.nextInt();

        } while (choice < 1 || choice > 5);

        switch (choice){
            case 1 : return Color.COEUR;
            case 2 : return Color.CARREAU;
            case 3 : return Color.TREFLE;
            case 4 : return Color.PIQUE;

            default: return null;
        }
    }
    
    /**
     * choose the trump for a player inside the pub
     * @return Color chosen or null if trump is not taken
     */
    public Color askAIColorTrump(){
        int choice;
        Random rand = new Random();
        choice = rand.nextInt(6)+1;
        
        switch (choice){
            case 1 : return Color.COEUR;
            case 2 : return Color.CARREAU;
            case 3 : return Color.TREFLE;
            case 4 : return Color.PIQUE;

            default: return null;
        }
    }
    
    /**
     * Print the card played by a player
     * @param playerNumber
     * @param card 
     */
    public void updatePlayedCard(int playerNumber, Card card){
        show("Player" + playerNumber + " put the card " +  card.getValue() + " de " + card.getColor());
    }
    
    /**
     * Update and print the number of the trick 
     * @param trickNumber 
     */
    public void updateTrick(int trickNumber){
        show("");
        show("##########Start of trick n° " + trickNumber + "##########");
        show("");
    }
    
    /**update and print the player that takes the trump and the trump
     * 
     * @param playerNumber
     * @param trump 
     */
    public void updatePlayerTakesTrump(int playerNumber, Card trump){
        show("Player " + playerNumber + " takes the trump " + trump.getValue() + " de " + trump.getColor());
    }
    
    /**
     * print the winner of the current Trick
     * @param playerNumber 
     */
    public void updateTrickWinner(int playerNumber){
        show("The winner of the trick is : Player "+ playerNumber);
    }
    
    /**
     * Print the names of the players at the table
     * @param playerNames 
     */
    public void updateNames(ArrayList<String> playerNames){
        show("The players at the table are  : " + playerNames);
        show("");
    }
    
    /**
     * Says to a player it is his turn to play
     * @param playerName 
     */
    public void updatePlayerIn(int playerName){
        show("Player " + playerName + " It is your turn to play ");
    }
    
    /**
     * print the points won by a player at the end of a trick
     * @param playerNumber
     * @param points 
     */
    public void updatePlayerPoints(int playerNumber, int points){
        show("Player " + playerNumber + " wins " + points + " points");
    }
    
    
    /**
     * Print the winners of a round
     * @param playerNumber1
     * @param playerNumber2 
     */
    public void updateWinners(int playerNumber1, int playerNumber2){
        show("Winners of the rounds are " + playerNumber1 + " and " + playerNumber2);
        show("");
    }
    
    /**
     * Convert colors to a more appropriate character
     * for a better display
     * @param colors
     * @return
     */
    public String colorConverter(Color colors){
        switch (colors) {
        case COEUR:
            return "♥";
        case CARREAU:
            return "♦";
        case PIQUE:
            return "♠";
        case TREFLE:
            return "♣";
        default:
            return "";
        }
    }
    

    /**
     * Convert values to a more appropriate character
     * for a better display
     * @param values
     * @return 
     */
    public String valueConverter(Value values){
        switch (values) {
            case SEPT:
                return "7 ";
            case HUIT:
                return "8 ";
            case NEUF:
                return "9 ";
            case DIX:
                return "10";
            case VALET:
                return "V ";
            case DAME:
                return "D ";
            case ROI:
                return "R ";
            case AS:
                return "A ";
            default:
                return "";
        }        
    }
    
    /**
     * Display the hand of the user 
     * @param hand 
     */
    public void displayHand(ArrayList<Card> hand){
        ArrayList<String> v = new ArrayList<>();
        ArrayList<String> c = new ArrayList<>();
        for (int i = 0; i< hand.size(); i++){
            v.add(valueConverter(hand.get(i).getValue()));
            c.add(colorConverter(hand.get(i).getColor()));
        }
        System.out.println(" ");
        if (hand.size() == 8){
        System.out.println("           Your cards : ");
        System.out.println(" ");
        System.out.println("            __  " + "  __  " + "  __  " + "  __  " + "  __  " + "  __  " + "  __  " + "  __  ");
        System.out.println("           |"+v.get(0)+"| " + " |"+v.get(1)+"| " + " |"+v.get(2)+"| " + " |"+v.get(3)+"| " + " |"+v.get(4)+"| " + " |"+v.get(5)+"| " + " |"+v.get(6)+"| " + " |"+v.get(7)+"| ");
        System.out.println("           |"+c.get(0)+" | " + " |"+c.get(1)+" | " + " |"+c.get(2)+" | " + " |"+c.get(3)+" | " + "|"+c.get(4)+" | " + " |"+c.get(5)+" | " + " |"+c.get(6)+" | " + "|"+c.get(7)+" | ");
        System.out.println("            --  " + "  --  " + "  --  " + "  --  " + "  --  " + "  --  " + "  --  " + "  --  ");
        System.out.println(" ");
        }
        
        else if (hand.size() == 7){
        System.out.println("           Your cards : ");
        System.out.println(" ");
        System.out.println("            __  " + "  __  " + "  __  " + "  __  " + "  __  " + "  __  " + "  __  ");
        System.out.println("           |"+v.get(0)+"| " + " |"+v.get(1)+"| " + " |"+v.get(2)+"| " + " |"+v.get(3)+"| " + " |"+v.get(4)+"| " + " |"+v.get(5)+"| " + " |"+v.get(6)+"|  ");
        System.out.println("           |"+c.get(0)+" | " + " |"+c.get(1)+" | " + " |"+c.get(2)+" | " + " |"+c.get(3)+" | " + "|"+c.get(4)+" | " + " |"+c.get(5)+" | " + "|"+c.get(6)+" |  ");
        System.out.println("            --  " + "  --  " + "  --  " + "  --  " + "  --  " + "  --  " + "  --  ");
        System.out.println(" ");
        }
        
        else if (hand.size() == 6){
        System.out.println("           Your cards : ");
        System.out.println(" ");
        System.out.println("            __  " + "  __  " + "  __  " + "  __  " + "  __  " + "  __  ");
        System.out.println("           |"+v.get(0)+"| " + " |"+v.get(1)+"| " + " |"+v.get(2)+"| " + " |"+v.get(3)+"| " + " |"+v.get(4)+"| " + " |"+v.get(5)+"| ");
        System.out.println("           |"+c.get(0)+" | " + " |"+c.get(1)+" | " + " |"+c.get(2)+" | " + " |"+c.get(3)+" | " + "|"+c.get(4)+" | " + " |"+c.get(5)+" | ");
        System.out.println("            --  " + "  --  " + "  --  " + "  --  " + "  --  " + "  --  ");
        System.out.println(" ");
        }
        
        else if (hand.size() == 5){
        System.out.println("           Your cards : ");
        System.out.println(" ");
        System.out.println("            __  " + "  __  " + "  __  " + "  __  " + "  __  ");
        System.out.println("           |"+v.get(0)+"| " + " |"+v.get(1)+"| " + " |"+v.get(2)+"| " + " |"+v.get(3)+"| " + " |"+v.get(4)+"| ");
        System.out.println("           |"+c.get(0)+" | " + " |"+c.get(1)+" | " + " |"+c.get(2)+" | " + " |"+c.get(3)+" | " + "|"+c.get(4)+" | ");
        System.out.println("            --  " + "  --  " + "  --  " + "  --  " + "  --  ");
        System.out.println(" ");
        }
        
        else if (hand.size() == 4){
        System.out.println("           Your cards : ");
        System.out.println(" ");
        System.out.println("            __  " + "  __  " + "  __  " + "  __  ");
        System.out.println("           |"+v.get(0)+"| " + " |"+v.get(1)+"| " + " |"+v.get(2)+"| " + " |"+v.get(3)+"| ");
        System.out.println("           |"+c.get(0)+" | " + " |"+c.get(1)+" | " + " |"+c.get(2)+" | " + " |"+c.get(3)+" | ");
        System.out.println("            --  " + "  --  " + "  --  " + "  --  ");
        System.out.println(" ");
        }
        
        else if (hand.size() == 3){
        System.out.println("           Your cards : ");
        System.out.println(" ");
        System.out.println("            __  " + "  __  " + "  __  ");
        System.out.println("           |"+v.get(0)+"| " + " |"+v.get(1)+"| " + " |"+v.get(2)+"| ");
        System.out.println("           |"+c.get(0)+" | " + " |"+c.get(1)+" | " + " |"+c.get(2)+" | ");
        System.out.println("            --  " + "  --  " + "  --  ");
        System.out.println(" ");
        }
        else if (hand.size() == 2){
        System.out.println("           Your cards : ");
        System.out.println(" ");
        System.out.println("            __  " + "  __  " );
        System.out.println("           |"+v.get(0)+"| " + " |"+v.get(1)+"| ");
        System.out.println("           |"+c.get(0)+" | " + " |"+c.get(1)+" | ");
        System.out.println("            --  " + "  --  " );
        System.out.println(" ");
        }
        else if (hand.size() == 1){
        System.out.println("           Your cards : ");
        System.out.println(" ");
        System.out.println("            __  ");
        System.out.println("           |"+v.get(0)+"| ");
        System.out.println("           |"+c.get(0)+" | ");
        System.out.println("            --  ");
        System.out.println(" ");
        }
    }
    
    /**
     * Display a view of the board at the start of a trick 
     * @param trump 
     */
    public void boardDisplay(Color trump){
        String co = colorConverter(trump);

        System.out.println(" ");
        System.out.println(" ");
        System.out.println("                                   PLAYER 2                     ");
        System.out.println("                __  " + "  __  " + "  __  " + "  __  " + "  __  " + "  __  " + "  __  " + "  __  ");
        System.out.println("               |  | " + " |  | " + " |  | " + " |  | " + " |  | " + " |  | " + " |  | " + " |  | ");
        System.out.println("               |  | " + " |  | " + " |  | " + " |  | " + " |  | " + " |  | " + " |  | " + " |  | ");
        System.out.println("                --  " + "  --  " + "  --  " + "  --  " + "  --  " + "  --  " + "  --  " + "  --                               TRUMP : (atout) ");
        System.out.println("                                                                                             __             ");
        System.out.println("                                                                                            |  |            ");
        System.out.println("                                                                                            |"+co+" |       ");
        System.out.println("                                     __                                                      --             ");
        System.out.println("    __                              |  |                               __                                   ");
        System.out.println("   |__|                             |  |                              |__|  ");
        System.out.println("P  |__|                              --                               |__| P ");
        System.out.println("L  |__|                    __                 __                      |__| L ");
        System.out.println("A  |__|                   |  |               |  |                     |__| A ");
        System.out.println("Y  |__|                   |  |               |  |                     |__| Y ");
        System.out.println("E  |__|                    --                 --                      |__| E ");
        System.out.println("R  |__|                              __                               |__| R ");
        System.out.println("1  |  |                             |  |                              |  | 3 ");
        System.out.println("   |  |                             |  |                              |  |  ");
        System.out.println("    --                               --                                --   ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println("                             PLAYER 0 ");
    }
    
    /**
     * Display a view of the board at the end of a trick 
     * @param trump
     * @param table 
     */
    public void boardDisplay(Color trump,HashMap<Player,Card> table){
        String co = colorConverter(trump);
        ArrayList<String> v = new ArrayList<>();
        ArrayList<String> c = new ArrayList<>();
 
        for (Card card : table.values()){
            v.add(valueConverter(card.getValue()));
            c.add(colorConverter(card.getColor()));
        }
        
        System.out.println(" ");
        System.out.println(" ");
        System.out.println("                                   PLAYER 2                     ");
        System.out.println("                __  " + "  __  " + "  __  " + "  __  " + "  __  " + "  __  " + "  __  " + "  __  ");
        System.out.println("               |  | " + " |  | " + " |  | " + " |  | " + " |  | " + " |  | " + " |  | " + " |  | ");
        System.out.println("               |  | " + " |  | " + " |  | " + " |  | " + " |  | " + " |  | " + " |  | " + " |  | ");
        System.out.println("                --  " + "  --  " + "  --  " + "  --  " + "  --  " + "  --  " + "  --  " + "  --                               ATOUT : ");
        System.out.println("                                                                                             __    ");
        System.out.println("                                                                                            |  |   ");
        System.out.println("                                                                                            |"+co+" |   ");
        System.out.println("                                     __                                                      --    ");
        System.out.println("    __                              |"+v.get(0)+"|                               __  ");
        System.out.println("   |__|                             |"+c.get(0)+" |                              |__|  ");                 
        System.out.println("P  |__|                              --                               |__| P ");
        System.out.println("L  |__|                    __                 __                      |__| L ");
        System.out.println("A  |__|                   |"+v.get(3)+"|               |"+v.get(1)+"|                     |__| A ");
        System.out.println("Y  |__|                   |"+c.get(3)+" |               |"+c.get(1)+" |                     |__| Y ");
        System.out.println("E  |__|                    --                 --                      |__| E ");
        System.out.println("R  |__|                              __                               |__| R ");
        System.out.println("1  |  |                             |"+v.get(2)+"|                              |  | 3 ");
        System.out.println("   |  |                             |"+c.get(2)+" |                              |  |  ");
        System.out.println("    --                               --                                --   ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println("                               PLAYER 0 ");
    }
}

