/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package belote;

import pubmanagement.Bar;
import java.util.ArrayList;
import java.util.Collections;
/**
 *
 * @author ploui
 */
public class Tournament {
    
    /**
     * Registeration fee for each participant.
     */
    public static final double REGISTER_PRICE = 10;
    /**
     * Number of teams that can participate in a tournament. 
     */
    public static final int NB_TEAMS = 3;
    
    // public static String history = "";
    public ArrayList<Team> teams = new ArrayList<>();
    private double registerPoll = 0;
    private final Bar bar;
    public ArrayList<Team> leaderBoard = new ArrayList<>();

    
    public Tournament(Bar bar) {  
        this.bar = bar;
    }
    
    /**
     * Starts the tournament and run every match, then display the results
     */
    public void run(){
        int i = 0;
        int j = 0;
        for (i = 0; i< teams.size(); i++){
            for (j = i+1; j<teams.size();j++){
                match(teams.get(i),teams.get(j));
            }
        }
        setleaderBoard();
        displayResult();
    }
    
    /**
     * Set up of a match between 2 teams
     * Adds points to winning team and get scores of each team
     * @param team1
     * @param team2 
     */
    public void match(Team team1,Team team2){
        
        Belote newGame = new Belote();
        ArrayList<Player> winners = new ArrayList<>();
        
        newGame.players.add(new Player(team1.getPlayers()[0].getHuman(),team1.getPlayers()[0].getIsAI()));
        newGame.players.add(new Player(team2.getPlayers()[0].getHuman(),team2.getPlayers()[0].getIsAI()));
        newGame.players.add(new Player(team1.getPlayers()[1].getHuman(),team1.getPlayers()[1].getIsAI()));
        newGame.players.add(new Player(team2.getPlayers()[1].getHuman(),team2.getPlayers()[1].getIsAI()));
        
        int gamePoint1 = 0;
        int gamePoint2 = 0;
        
        winners = newGame.entireGame(bar);
        
        if (winners.get(0).getName() == team2.getPlayers()[1].getName() && winners.get(1).getName() == team2.getPlayers()[0].getName()){
            team1.points += 3;
            team1.results.add(3);
            team2.results.add(0);
            gamePoint1 = 3;
            System.out.println("L'equipe "+team1.getName() +" a gagne contre " + team2.getName());
            System.out.println("");
        }
        else{
            team2.points += 3;
            team1.results.add(0);
            team2.results.add(3);
            gamePoint2 = 3;
            System.out.println("L'equipe "+team2.getName() +" a gagne contre " + team1.getName());
            System.out.println("");
        }
        // history += "L'equipe "+team1.getName() +" a gagne contre " + team2.getName() +"\n";
        // history += "\t"+team1.getName()+" : "+ gamePoint1 + "\t"+team2.getName()+" : " + gamePoint2 + "\n";
    }
    
    /**
     * Action to be done after the end of the tournament for losers and winners.
     */
    public void endAction() {
        Player[] winners = getWinner().getPlayers();
        Player[] losers = getLoser().getPlayers();
        
        // for winners 
        for (int i = 0; i<winners.length; i++) {
            winners[i].winnerAction();
            double oldBalance = winners[i].getHuman().getWalletBalance();
            winners[i].getHuman().setWalletBalance(oldBalance + (double) ((int)(registerPoll/4 *100))/100);
        }
        double oldBalance =bar.getBoss().getWalletBalance();
        bar.getBoss().setWalletBalance(oldBalance + (double) ((int)(registerPoll/2 *100))/100);
        
        //for losers
        for (int i = 0; i<losers.length; i++) losers[i].loserAction(winners);
    }
    
    /**
     * We sort the teams from the winners to the last team by their points
     */
    public void setleaderBoard(){
        for (Team team : teams){
            leaderBoard.add(team);  
        }
        Collections.sort(leaderBoard,Team.ptsComparator);
        for (int i = 0;i< leaderBoard.size();i++){
            leaderBoard.get(i).place = i+1;
        }
    }
    /**
     * We catch up the winner of the tournament
     * @return the winners or null if all teams have equal points
     */
    public Team getWinner(){
        if (leaderBoard.get(0).points > leaderBoard.get(2).points){
           return leaderBoard.get(0);
        }
        else{
            return null;
        }
    }
    /**
     * We catch up the losers of the tournament
     * @return the losers or null if all teams have equal points
     */
    public Team getLoser(){
        if (leaderBoard.get(2).points < leaderBoard.get(0).points){
           return leaderBoard.get(2);
        }
        else{
            return null;
        }
    }
    /**
     * Display the results of the tournament
     */
    public void displayResult(){
        System.out.println("");
        System.out.println("");
        System.out.println("----------------------------------------------- RESULTS : -----------------------------------------------");
        System.out.println("");
        System.out.print("TEAMS                |");
        for (Team team : teams){
            System.out.print("" + team.name); 
            for (int l = team.name.length(); l< 18;l++){
                System.out.print(" ");
            }
            System.out.print("|");
        }
        System.out.println("   TOTAL   |   LEADERBOARD   ");

        for (int i = 0; i< teams.size();i++) {
            System.out.print(teams.get(i).name);
            // Spacement differs from the length of each team name
            for (int a = teams.get(i).name.length(); a< 20;a++){
                System.out.print(" ");
            }
            for (int j = 0; j< teams.size()-1; j++){ 
                if (i == j){
                    System.out.print(" |                 ");
                }
                System.out.print(" |         " + teams.get(i).results.get(j) + "       ");
                if (i == 2 && j == 1){
                    System.out.print(" |                 ");
        }
                
            }
            System.out.print(" |     " + teams.get(i).points + "     |        " + teams.get(i).place );
            System.out.println("");

        }
    }
    
    /**
     * Set the registration poll.
     * @param newValue to be setted
     */
    public void setRegisterPoll(double newValue) {
        registerPoll = newValue;
    }
    
    /**
     * Get the registration poll.
     * @return the double 
     */
    public double getRegisterPoll() {
        return registerPoll;
    }
}
