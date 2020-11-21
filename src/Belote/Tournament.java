/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Belote;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import pubmanagement.*;

/**
 *
 * @author eugenie_dalmas
 */
public class Tournament {
    
    public static final String HISTORY_PATH = "src/ressources/tournamentHistory.txt";
    public static final double REGISTER_PRICE = 10;
    
    private ArrayList<Team> teams = new ArrayList<>();
    private double registerPoll = 0;
    private Bar bar;
    private ArrayList<Team> leaderBoard = new ArrayList<>();
    private String history;
    
    public Tournament(Bar bar) {
        this.bar = bar;
    }
    
    public void run() {
        // everyone against averyone (teams)
    }
    
    public void match(Team team1, Team team2) {
    }
    
    // displayResults
    
    public Team getWinners() {
    }
    
    public Team getLosers() {
    }
    
    /**
     * Action to be done after the end of the tournament for losers and winners.
     */
    public void endAction() {
        Player[] winners = getWinners().getPlayers();
        Player[] losers = getLosers().getPlayers();
        
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
    
    public void writeHistory() throws IOException {
        FileWriter out = null;
        try {
            out = new FileWriter(HISTORY_PATH);
            out.write(history);
            out.write("\n\n");
        }
        finally {
            if (out != null) out.close();
        }
    }
    
    public void setRegisterPoll(double newValue) {
        registerPoll = newValue;
    }
    
    public double getRegisterPoll() {
        return registerPoll;
    }
}

/*
--- bar_name - date - Tournament n°i ---
-> team1 vs team2
...


String history = "";
history += "-> " team1.getName() + " vs " + team2.getName() + "\n";
history += "ghjkol\n"+"fghjkl\n";
file.write(history);
*/

