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
    public static final String FEMALE_NAMES_PATH = "src/resources/female_names.txt";
    public static final String MALE_NAMES_PATH = "src/resources/male_names.txt";
    public static final String SURNAMES_PATH = "src/resources/surnames.txt";
    public static final String DRINKS_PATH = "src/resources/drinks.txt";
    public static final String SHOUT_PATH = "src/resources/shouts.txt";
    public static final String ACCESSORIES_PATH = "src/resources/accessories.txt";
    public static final String COLORS_PATH = "src/resources/colors.txt";
    
    /**
     * Stored data that is loaded at the initiation.
     */
    public static ArrayList<String> DRINKS;
    public static ArrayList<String> MALE_NAMES;
    public static ArrayList<String> FEMALE_NAMES;
    public static ArrayList<String> SURNAMES;
    public static ArrayList<String> SHOUTS;
    public static ArrayList<String> ACCESSORIES;
    public static ArrayList<String> COLORS;
    
    /**
     * Name of the pub.
     * (Should be defined by the user)
     */
    private final String name;
    /**
     * Boss of the pub.
     * (is always female)
     */
    private Boss boss;
    /**
     * Employee list with all instanciated employee of the this bar.
     */
    private ArrayList<Employee> employees = new ArrayList<>();
    /**
     * Possible clients of the bar.
     * (Is like the population that has acces to bars as clients)
     */
    private ArrayList<Client> clients = new ArrayList<>();
    /**
     * Space that may be used by clients.
     * (Says if the client is in or out this bar)
     * (4 place per table at most, null if unused space)
     */
    private ArrayList<Client[]> tables = new ArrayList<>();
    /**
     * All drinks instances for all bars.
     */
    private static ArrayList<Drink> drinksMenu = new ArrayList<>();
    /**
     * Money of the bar.
     */
    private double till;
    /**
     * Stocks (name, quantity) of each drink of the drinksMenu.
     */
    private Map<String, Integer> stocks;
    
    
    /**
     * Constructor
     * @param name 
     */
    public Bar(String name) {
        this.name = name;
        this.till = Math.random()*200;
        
        this.init();
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
    
    
    // ---- Initiation -----    
    /**
     * Initiate the Bar with its starting values and people.
     * (drinksMenu, 1 boss, 3 employees, 10 clients)
     */
    private void init() {
        // load the resources
        try {
            Bar.loadAttributes();
        } 
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        
        // initiating tables: 4
        for(int i = 0; i<4; i++) {
            this.tables.add(new Client[4]);
        }
        // Drinks 
        for (int i = 0; i<Bar.DRINKS.size(); i++) {
            if (i%3 == 0){
                Bar.drinksMenu.add(new Drink(
                        Bar.DRINKS.get(i), 
                        Integer.parseInt(Bar.DRINKS.get(i+1)), 
                        Double.parseDouble(Bar.DRINKS.get(i+2)), 
                        Double.parseDouble(Bar.DRINKS.get(i+2)) + 1.50
                ));
            }
        }
        // - Boss
        this.boss = new Boss (
                this,
                Bar.FEMALE_NAMES.get((int) (Math.random()*Bar.FEMALE_NAMES.size())),
                Bar.SURNAMES.get((int) (Math.random()*Bar.SURNAMES.size())),
                Math.random()*200,
                (int) (Math.random()*10),
                Bar.SHOUTS.get((int) (Math.random()*Bar.SHOUTS.size())),
                Bar.drinksMenu.get((int) (Math.random()*Bar.drinksMenu.size())),
                Bar.drinksMenu.get((int) (Math.random()*Bar.drinksMenu.size())),
                (int) (Math.random()*10)
        );
        
        // - Employees: 1 Barman and 2 Waiter (both gender)
        this.employees.add(new MaleWaiter(
            this,
            Bar.MALE_NAMES.get((int) (Math.random()*Bar.MALE_NAMES.size())),
            Bar.SURNAMES.get((int) (Math.random()*Bar.SURNAMES.size())),
            Math.random()*200,
            (int) (Math.random()*10),
            Bar.SHOUTS.get((int) (Math.random()*Bar.SHOUTS.size())),
            (int) (Math.random()*10)
        ));
        
        this.employees.add(new FemaleWaiter(
            this,
            Bar.FEMALE_NAMES.get((int) (Math.random()*Bar.FEMALE_NAMES.size())),
            Bar.SURNAMES.get((int) (Math.random()*Bar.SURNAMES.size())),
            Math.random()*200,
            (int) (Math.random()*10),
            Bar.SHOUTS.get((int) (Math.random()*Bar.SHOUTS.size())),
            (int) (Math.random()*10)
        ));
        // the barman does not really have a gender but both gender's names are possibles
        this.employees.add(new Barman(
            this,
            Bar.FEMALE_NAMES.get((int) (Math.random()*Bar.FEMALE_NAMES.size())),
            Bar.SURNAMES.get((int) (Math.random()*Bar.SURNAMES.size())),
            Math.random()*200,
            (int) (Math.random()*10),
            Bar.SHOUTS.get((int) (Math.random()*Bar.SHOUTS.size()))
        )); 
        
        // - Clients: 10
        for(int i = 0; i<10; i++) {
            double rand = Math.random();
            // equally chance of generation between male and female
            if (rand < 0.5) {
                this.clients.add(new MaleClient(
                    this,
                    Bar.FEMALE_NAMES.get((int) (Math.random()*Bar.FEMALE_NAMES.size())),
                    Bar.SURNAMES.get((int) (Math.random()*Bar.SURNAMES.size())),
                    Math.random()*200,
                    (int) (Math.random()*10),
                    Bar.SHOUTS.get((int) (Math.random()*Bar.SHOUTS.size())),
                    Bar.drinksMenu.get((int) (Math.random()*Bar.drinksMenu.size())),
                    Bar.drinksMenu.get((int) (Math.random()*Bar.drinksMenu.size())),
                    (int) (Math.random()*10),
                    Bar.COLORS.get((int) (Math.random()*Bar.COLORS.size()))+" tee-shirt"
                ));
            }
            else {
                ArrayList<String> accessories = new ArrayList<>();
                for (int j = 0; j<(int) (Math.random()*5); j++) {
                    accessories.add(Bar.ACCESSORIES.get((int) (Math.random()*Bar.ACCESSORIES.size())));
                }
                this.clients.add(new FemaleClient(
                    this,
                    Bar.MALE_NAMES.get((int) (Math.random()*Bar.FEMALE_NAMES.size())),
                    Bar.SURNAMES.get((int) (Math.random()*Bar.SURNAMES.size())),
                    Math.random()*200,
                    (int) (Math.random()*10),
                    Bar.SHOUTS.get((int) (Math.random()*Bar.SHOUTS.size())),
                    Bar.drinksMenu.get((int) (Math.random()*Bar.drinksMenu.size())),
                    Bar.drinksMenu.get((int) (Math.random()*Bar.drinksMenu.size())),
                    (int) (Math.random()*10),
                    accessories
                )); 
            }
        }
    }
    
    // ----- Loaders ----- (for initiating every starting attributes)
    /**
     * Load data from a file.
     * @param pathName
     * @param separator
     * @return an ArrayList of each data parse to String
     * @throws IOException 
     */
    public static ArrayList<String> loadData(String pathName, String separator) throws IOException {
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
    
    /**
     * Load all needed resources for bar initiation and auto instanciations.
     * @throws IOException 
     */
    public static void loadAttributes() throws IOException {
        // Drinks 
        Bar.DRINKS = Bar.loadData(DRINKS_PATH, "\t");
        // Humans
        Bar.MALE_NAMES = Bar.loadData(MALE_NAMES_PATH, ",");
        Bar.FEMALE_NAMES = Bar.loadData(FEMALE_NAMES_PATH, ",");
        Bar.SURNAMES = Bar.loadData(SURNAMES_PATH, ",");
        Bar.SHOUTS = Bar.loadData(SHOUT_PATH, ",");
        Bar.ACCESSORIES = Bar.loadData(ACCESSORIES_PATH, ",");
        Bar.COLORS = Bar.loadData(COLORS_PATH, ",");
    }
    
    
    // ----- Setters -----
    /**
     * Set the till to its new balance.
     * @param NewBalance 
     */
    public void setTillBalance(double NewBalance) {
        this.till = NewBalance;
    }
    
    // ----- Getters -----    
    /**
     * Get the name of this bar.
     * @return 
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Get the till balance if this bar.
     * @return 
     */
    public double getTillBalance() {
        return this.till;
    }
    
    /**
     * Get the stocks (names, quantities) of the bar.
     * @return 
     */
    public Map<String, Integer> getStocks() {
        return this.stocks;
    }
    
    /**
     * Get the list of all the potential clients.
     * @return 
     */
    public ArrayList<Client> getClients() {
        return this.clients;
    }
    
    /**
     * Get the tables of the bar.
     * @return 
     */
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
    
    /**
     * Get the list of employees of this bar.
     * @return 
     */
    public ArrayList<Employee> getEmployees() {
        return this.employees;
    }
    
    /**
     * Get the boss of the bar.
     * @return 
     */
    public Boss getBoss() {
        return this.boss;
    }
    
    /**
     * Get the drink menu of all bars.
     * @return 
     */
    public static ArrayList<Drink> getDrinksMenu() {
        return Bar.drinksMenu;
    }
}
