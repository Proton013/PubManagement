/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package belote;

import java.util.ArrayList;
import java.util.Comparator;
/**
 *
 * @author ploui
 */
public class Team {
    
    /**
     * Path to team names .txt.
     */
    public static final String TEAM_NAMES_PATH = "src/resources/teamNames.txt";
    /**
     * Where are all the team names stored.
     */
    public static ArrayList<String> NAMES;
    
    /**
     * Attributes of a team
     */
    public Player[] players = new Player[2];
    public String name;
    public int points;
    public ArrayList<Integer> results = new ArrayList<>();
    public int place;

    
    /**
     * Constructor of a team
     * @param players
     * @param name 
     */
    public Team(Player[] players, String name) {
        this.players = players;
        this.name = name;  
        
    }
    /**
     * Compare team with the points they have in order to set the leaderboard
     */
    public static Comparator<Team> ptsComparator = new Comparator<Team>() {
        @Override
	public int compare(Team team1, Team team2) {
            int team1pts = team1.points;
            int team2pts = team2.points;
	   //ordre descendant
	   return team2pts-team1pts;
        }
    };
            
    /**
     * 
     * @return array of players in a team
     */
    public Player[] getPlayers() {
        return players;
    }
    
    /**
     * 
     * @return the name of the team
     */    
    public String getName() {
        return name;
    }

    /**
     *
     * @return points of the team
     */
    public int getPoints() {
        return points;
    }
    
    /**
     * set the points of a team
     * @param points 
     */
    public void setPoints(int points) {
        this.points = points;
    }
    

}
