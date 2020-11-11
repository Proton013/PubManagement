/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pubmanagement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 *
 * @author eugenie_dalmas
 */
public class Bar {
    
    /**
     * Path names of the initiation files 
     */
    public static final String FEMALE_NAMES_PATH = "/data/female_names";
    public static final String MALE_NAMES_PATH = "/data/male_names";
    public static final String SURNAMES_PATH = "/data/surnames";
    public static final String DRINKS_PATH = "/data/surnames";
    public static final String SHOUT_PATH = "/data/shouts";
    public static final String ACCESSORIES_PATH = "/data/accessories";
    public static final String COLORS_PATH = "/data/colors";
    
    private final String name;
    // following attributes are loaded from a text file
    private Boss boss; // is female
    private ArrayList<Employee> employees;
    private ArrayList<Client> clients;
    private ArrayList<Client[]> tables = new ArrayList<>(); // 4 places each (null if place is Empty)
    private static ArrayList<Drink> drinksMenu;
    private double till;
    private Map<String, Integer> stocks;
    
    /**
     * Constructor
     * @param name 
     */
    public Bar(String name) {
        this.name = name;
    }
    
    // ----- Add (user) -----
    /**
     * Add a new waiter to the bar.
     * @param waiter : new instance
     */
    private void addWaiter(Waiter waiter) {
        this.employees.add(waiter);
    }
    
    /**
     * Add a new barman to the bar.
     * @param barman 
     */
    private void addBarman(Barman barman) {
        this.employees.add(barman);
    }
    
    /**
     * Add a new client to the bar.
     * @param client 
     */
    private void addClient(Client client) {
        this.clients.add(client);
    }
    
    /**
     * Add new tables to the pub.
     * @param amount of tables to add
     */
    private void addTables(int amount) {
        Client[] table = new Client[6];
        // add empty tables
        for(int i = 0; i<amount; i++) {
            this.tables.add(table);
        }
    }
    
    
    // ----- Displays (user) -----
    
    /**
     * Displays in the console for the user the drinks menu.
     */
    public void displayDrinksMenu() {
        System.out.println("Drinks menu !");
        for(int i = 0; i<Bar.drinksMenu.size(); i++) {
            System.out.println(" . " + Bar.drinksMenu.get(i).getName());
        }
    }
    
    /**
     * Displays the stocks as a list for the user in the console.
     */
    public void displayStock() {
        System.out.println("*** Stocks ***");
        this.stocks.keySet().forEach(e -> {
            System.out.println(" ." + e + " : " + this.stocks.get(e));
        });
        System.out.println("* * *    * * *");
    }
    
    /**
     * Initiate the Bar with its starting values and people.
     * @throws IOException from loadData()
     */
    private void init() throws IOException{
        // initiating tables: 4
        for(int i = 0; i<4; i++) {
            this.tables.add(new Client[4]);
        }
        // Drinks 
        ArrayList<String> drinks = loadData(DRINKS_PATH, ",");
        // Humans: loading used infos from files
        ArrayList<String> maleNames = loadData(MALE_NAMES_PATH, ",");
        ArrayList<String> femaleNames = loadData(FEMALE_NAMES_PATH, ",");
        ArrayList<String> surnames = loadData(SURNAMES_PATH, ",");
        ArrayList<String> shouts = loadData(SHOUT_PATH, ",");
        ArrayList<String> accessories = loadData(ACCESSORIES_PATH, ",");
        ArrayList<String> colors = loadData(COLORS_PATH, ",");
        // - Boss
        // - Employees: 1 Barman and 2 Waiter (both gender)
        // - Clients: 10
        
    }
    
    // ----- Loaders ----- (for initiating every starting attributes)
    /**
     * Load data from a file.
     * @param pathName
     * @param separator
     * @return an ArrayList of each data parse to String
     * @throws IOException 
     */
    public ArrayList<String> loadData(String pathName, String separator) throws IOException {
        ArrayList<String> data;    // stored final data
        data = new ArrayList<>(0); // will be resized afterwards
        BufferedReader reader = new BufferedReader(new FileReader(pathName));
        
        try {
            String line;
            line = reader.readLine();
            
            // until we have red every line
            while(line!=null) {
                if (!line.equals("")) {
                    // skip line jumps
                    data.addAll(Arrays.asList(line.split(separator)));
                }
                line = reader.readLine();
            }
        }
        catch(FileNotFoundException e) {
            System.err.println("Error : File not found.");
            System.out.println(e.getMessage());
        }
        finally {
            reader.close();
        }
        
        return data;
    }
    
    // ----- Setters -----
    public void setTillBalance(double NewBalance) {
        this.till = NewBalance;
    }
    
    // ----- Getters -----    
    public String getName() {
        return this.name;
    }
    
    public double getTillBalance() {
        return this.till;
    }
    
    public Map<String, Integer> getStocks() {
        return this.stocks;
    }
    
    public ArrayList<Client> getClients() {
        return this.clients;
    }
    
    public ArrayList<Client[]> getTables() {
        return this.tables;
    }
    
    /**
     * Get the clients that are in the pub, at a table.
     * @return an ArrayList of Client
     */
    public ArrayList<Client> getPresentClients() {
        ArrayList<Client> present = new ArrayList<>();
        // get every client at a table
        this.tables.forEach(table -> {
            for (int i = 0; i<table.length; i++) {
                present.add(table[i]);
            }
        });
        return present;
    }
    
    public ArrayList<Employee> getEmployees() {
        return this.employees;
    }
    
    public Boss getBoss() {
        return this.boss;
    }
    
    public static ArrayList<Drink> getDrinksMenu() {
        return Bar.drinksMenu;
    }
}
