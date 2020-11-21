/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Belote;

import java.util.ArrayList;

/**
 *
 * @author eugenie_dalmas
 */
public class Team {
    
    public static final String TEAM_NAMES_PATH = "src/resources/teamNames.txt";
    public static ArrayList<String> NAMES;
    
    Player[] players = new Player[2];
    String name;
    
    public Team(Player[] players, String name) {
        this.players = players;
        this.name = name;
    }
    
    
    public Player[] getPlayers() {
        return players;
    }
    
    public String getName() {
        return name;
    }
}
