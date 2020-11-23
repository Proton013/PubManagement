/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package belote;

import java.util.ArrayList;
import java.util.HashMap;
import pubmanagement.Bar;

/**
 *Controler of the game and methods to run the game
 * We extends the class BeloteManagement in order to access methods needed easily
 * @author ploui
 */
public class Belote extends BeloteManagement {
    
    /**
     * Fixed limit of player for a belote game.
     */
    public static final int NB_PLAYER = 4;
    
    /**
     * Liste of players at the table
     */
    public ArrayList<Player> players;
    
    /**
     * Our stack of Card
     */
    private StackOfCards stack;
    
    /**
     * Color of the trump
     */
    private Color trump;
    
    /**
     * Color of the current trick
     */
    private Color trickColor;
    
    /**
     * Winner of the last trick and player who will
     * start to play for the next trick
     */
    private Player trickWinner;
    
    /**
     * Player that chosed the trump
     */
    private Player playerHasTrump;
    
    /**
     * Both teams playing
     */
    private ArrayList<Player> team1 = new ArrayList<>();
    private ArrayList<Player> team2 = new ArrayList<>();
    
    /**
     * Cards put on the table at each trick
     */
    private HashMap<Player,Card> table;

    /**
     * Constructor
     */
    public Belote(){
        this.players = new ArrayList<Player>();
        this.stack = new StackOfCards();
        this.trump = null;
        this.trickColor = null;
        this.trickWinner = null;
        this.playerHasTrump = null;
        this.table = new HashMap<Player,Card>();   
    }

    /**
     * 
     * @return the players at the table
     */
    private ArrayList<String> getPlayersName(){
        ArrayList<String> names = new ArrayList<String>();
        for (Player player : players)
        {
                names.add(player.getName());
        }
        return names;
    }
    
    /**
     * We assign a number to each player
     */
    public void assignNumber(){
        for (int i = 0; i < 3; i++){
            players.get(i).setNumber(i);
        } 
    }
    
    /**
     * On fill both teams with the players
     */
    public void fillTeams(){
        team1.add(players.get(0));
        team1.add(players.get(2));
        team2.add(players.get(1));
        team2.add(players.get(3));
    }
    

    /**
     * Start of a round
     * @return a list with the points of both teams
     */
    public int[] start(){

        int team1Points = 0;
        int team2Points = 0;
        
        
        if (players.size() != 4){
            throw new RuntimeException("Belote requires 4 players !");
        }
        
        updateNames(getPlayersName());
        
        //We gather all the cards on the table in the stack
        stack.addAll(table.values());
        table.clear();
        
        do{
            //Takes every cards of each player to put them back in the stack
            for (Player p : players){
                stack.addAll(p.getHand());
                stack.addAll(p.getWonCards());
                p.getHand().clear();
                p.getWonCards().clear();
            }
            stack.shuffle();
        }
        while (!distribute());
        
        
        //Initialisation of the first player to play
        int numberOfTrickWinner = 0;
        trickWinner =  players.get(0);
        
        for (int i = 1 ; i <= 8 ; i++){
            
            //Set up of a round
            updateTrick(i);
            roundTable(numberOfTrickWinner);
            numberOfTrickWinner = trickWinner.getNumber();
            updateTrickWinner(numberOfTrickWinner);
            updatePlayerPoints(numberOfTrickWinner,getTablePoints());
            if (numberOfTrickWinner == 0 || numberOfTrickWinner == 2){
                team1Points += getTablePoints();
            }
            else{
                team2Points += getTablePoints();
            }
            trickWinner.getWonCards().addAll(table.values());
            table.clear();   
        }
        
        //Dix de der       
        updatePlayerPoints(numberOfTrickWinner, 10);
        int[] nbWonRounds = getWinnersRound();
        updateWinners(nbWonRounds[0], nbWonRounds[1]); 
        
        if (numberOfTrickWinner == 0 || numberOfTrickWinner == 2){
            team1Points += 10;
        }
        else{
            team1Points += 10;
        }
        
        // Count points for each team
        if (playerHasTrump.getNumber() == 0 || playerHasTrump.getNumber() == 2){
            if (team1Points < 82){
                team1Points = 0;
            }
        }
                if (playerHasTrump.getNumber() == 1 || playerHasTrump.getNumber() == 3){
            if (team2Points < 82){
                team2Points = 0;
            }
        }
            
        System.out.println("Team1 : " + team1Points + " points");
        System.out.println("Team2 : " + team2Points + " points");
        System.out.println(" ");
        
        //Array of points for both teams
        int []pts = {team1Points,team2Points};
        return pts;
    }
    
    /**
     * Round Table asking one by one every player to put a card on the table
     * Starting by the player that won the last trick
     * Can only accept validate cards
     * @param NbOfLastTrickWinner 
     */
    private void roundTable(int NbOfLastTrickWinner){
        trickColor = null;
        trickWinner = null;
        boolean display = true;
        for(int i = 4; i > 0; i--){
            
            Player player = players.get((NbOfLastTrickWinner + i)%4);
            
            //We print the board and the cards inside the hand of the user
            // if he plays
            if (player.getIsAI() == false){
                boardDisplay(trump);
                displayHand(player.getHand());
            }

            updatePlayerIn(player.getNumber());
            Card card = null;
            ArrayList<Card> validateCards = getValidateCards(player.getHand(),player);
                        
            while (!validateCards.contains(card)){
                if (player.getIsAI() == false){
                    card = askCard(player.getHand());
                    if (!validateCards.contains(card) && player.getIsAI() == false){
                        System.out.println("You can't play this card, choose an other one!");
                    }
                }
                else if (player.getIsAI() == true){
                    card = askAICard(player.getHand());      
                }

            }
            
            Color color = card.getColor();
            
            //first card of the trick
            if (trickColor == null && trickWinner == null){
                trickColor = color;
                trickWinner = player;
            }
            
            //if the card is superior to the dominating card
            else if(card.isSuperior(table.get(trickWinner), trump, trickColor)){
                trickWinner = player;
            }
            
            player.getHand().remove(card);
            table.put(player,card);
            updatePlayedCard(player.getNumber(), card);  
        }
        
        // Again, if the user is playing we display the board with the cards
        //played by each player and the hand of the user
        for (int i = 0; i < players.size(); i++){
            if (players.get(i).getIsAI() == false){
                boardDisplay(trump,table);
                displayHand(players.get(i).getHand());
            }
        }        
    }
    /**
     * Give the total points of cards on the table depending on the trump
     * @return an integer of points
     */
    private int getTablePoints(){
        int val = 0;
        for (Card card : table.values()){
            val += card.getValue(trump);
        }
        return val;
    }
    
    /**
     * Check the hand of the player for the validity of the cards he can play
     * @param cards
     * @param player
     * @return an ArrayList of validate cards
     */
    private ArrayList<Card> getValidateCards(ArrayList<Card> cards, Player player){
        ArrayList<Card> validCards = new ArrayList<>();
        for (Card card : cards){
            if (cardValidity(card,player)){
                validCards.add(card);
            }
        }
        return validCards;
        
    }
    /**
     * Here we check if the card insine the hand of the player can be played
     * "if" are ordered in term of importance
     * @param card
     * @param player
     * @return true if it can be played, else return false
     */
    private boolean cardValidity(Card card, Player player){
        //null card
        if (card == null) return false;
        
        //if the player doesn't have the card in his hand
        if (!player.hasACard(card)) return false;
        
        //first card of the trick (first player to play)
        if (trickColor == null) return true;
        
        Color color = card.getColor();
        
        //correct if the card is of the color of the trick
        if (color == trickColor) return true;
        
        //correct if the dominate card is put by his teammate
        if(trickWinner.getNumber() == (player.getNumber()+ 2) %4) return true;
        
        //if the player has a card of the color of the trick and
        // he wants to play another card not valid
        if (player.hasAColor(trickColor)) return false;
        
        //correct if the card has the same color as the trump
        if (color == trump) return true;
        
        //not correct if he has a card of the same color as the trump
        //but he wants to play something else
        if (player.hasAColor(trump)) return false;
        
        return true;
    }
    
    
    /**
     * We distribute all the cards of the stack
     * @return true if trump has been taken, else return false
     */
    public boolean distribute(){
        //first, distribute 3 cards to each player
        for (int i = 3; i >= 0; i--){
            players.get(i).getHand().addAll(stack.pick(3));
            stack.remove(0);
            stack.remove(0);
            stack.remove(0);
        }
        
        //then, distribute 2 cards to each player
        for (int i = 3; i >= 0; i--){
            players.get(i).getHand().addAll(stack.pick(2));
            stack.remove(0);
            stack.remove(0);
        }
        
        Card offeredCard = stack.pick();
        
        //Offer the next card as trump
        updateTrump(offeredCard);
        
        //We first make a round table to see if someone takes the trump
        for (int i = 3; i >= 0; i--){
            if (players.get(i).getIsAI() == false){
                System.out.println("Joueur " + i);
                if (askTakeTrump() == true){
                    trump =  offeredCard.getColor();
                    playerHasTrump = players.get(i);
                    break;
                }
            }
            else if (players.get(i).getIsAI() == true){
                if (askAITakeTrump() == true){
                    trump =  offeredCard.getColor();
                    playerHasTrump = players.get(i);
                    break;
                }
            }         

        }
        //Second roundtable if none took the trump before
        if (trump == null){
            for (int i = 3; i >= 0; i--){
                Color col = null;
                if (players.get(i).getIsAI() == false){
                    System.out.println("Joueur " + i);
                    col = askColorTrump();
                }
                else{
                    col = askAIColorTrump();
                }

                if(col != null){
                    trump = col; 
                    playerHasTrump = players.get(i);
                    break;
                }
            }
        }
        
        //if none took the trump, we start again the distribution
        if (trump == null) return false;
        
        updatePlayerTakesTrump(playerHasTrump.getNumber(), offeredCard);
        
        stack.remove(0);
        playerHasTrump.getHand().add(offeredCard);
        
        //We distribute the last cards
        for (int i = 3; i >= 0; i--){
            int n = 0;
            //2 cartes for the player that has the trump
            if (players.get(i) == playerHasTrump){
                n = 2;
                players.get(i).getHand().addAll(stack.pick(n));
                stack.remove(0);
                stack.remove(0);
            }
            else{
                n = 3;
                players.get(i).getHand().addAll(stack.pick(n));
                stack.remove(0);
                stack.remove(0); 
                stack.remove(0);
            }
        }
        
        return true;
    }
    
    /**
     * We get the winners of the round
     * @return int[] with both winners 
     */
    private int[] getWinnersRound(){
        Player winner = null;
        for (Player player : players){
            if (winner == null || player.wonPoints(trump) > winner.wonPoints(trump)){
                winner = player;               
            }
        }
        int[] winners = new int[2];
        winners[0] = winner.getNumber();
        winners[1] = (winners[0] + 2) % 4;
        return winners;
    }
    
    /**
     * Set up of a whole game and we count the points
     * The game ends when a team has reached 501 points
     * @param bar where the game takes place
     * @return
     */
    public ArrayList<Player> entireGame(Bar bar){
        assignNumber();
        fillTeams();
        init(bar);
        int team1TotalPoints = 0;
        int team2TotalPoints = 0;
        int i = 0;
        while (team1TotalPoints <501 && team2TotalPoints <501){
            int []pts = start();
            i++;
            System.out.println("########################");    
            System.out.println("###FIN DE LA MANCHE "+i+"###");
            System.out.println("########################");   
            System.out.println(" ");
            
            team1TotalPoints += pts[0];
            team2TotalPoints += pts[1];
            System.out.println("La Team1 a : " + team1TotalPoints );
            System.out.println("La Team2 a : " + team2TotalPoints );
            System.out.println("");
        }
        
        System.out.println("");
        System.out.println("SCORE FINAL : ");
        System.out.println("team1 :" + team1TotalPoints + "      team2 : " + team2TotalPoints);
        if (team1TotalPoints > team2TotalPoints){
            System.out.println("LES GAGNANTS DE LA PARTIE SONT :" + team1.get(0).getName() + " et " + team1.get(1).getName());
            return team1;
        }
        else {
            System.out.println("LES GAGNANTS DE LA PARTIE SONT :" + team2.get(0).getName() + " et " + team2.get(1).getName()); 
            return team2;
        }
        
    }
    
}

