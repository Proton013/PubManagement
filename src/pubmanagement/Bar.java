/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pubmanagement;

import belote.Team;
import exceptions.DoesNotExistException;
import exceptions.MaxCapacityReachedException;
import exceptions.MinCapacityReachedException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Object of the management where all human actions takes place.
 * @author eugenie_dalmas
 */
public class Bar implements InformationDisplayer {
    
    /*
     * Path names of the initiation files 
     */
    public static final String FEMALE_NAMES_PATH = "src/resources/female_names.txt";
    public static final String MALE_NAMES_PATH = "src/resources/male_names.txt";
    public static final String SURNAMES_PATH = "src/resources/surnames.txt";
    public static final String DRINKS_PATH = "src/resources/drinks.txt";
    public static final String SHOUT_PATH = "src/resources/shouts.txt";
    public static final String ACCESSORIES_PATH = "src/resources/accessories.txt";
    public static final String COLORS_PATH = "src/resources/colors.txt";
    
    /*
     * Stored data that is loaded at the initiation.
     */
    public static ArrayList<String> DRINKS;
    public static ArrayList<String> MALE_NAMES;
    public static ArrayList<String> FEMALE_NAMES;
    public static ArrayList<String> SURNAMES;
    public static ArrayList<String> SHOUTS;
    public static ArrayList<String> ACCESSORIES;
    public static ArrayList<String> COLORS;
    
    /*
     * Fixed limits.
     */
    public static final int LOW_QUANTITY_LIMIT = 3;
    public static final int HIGH_QUANTITY_LIMIT = 5;
    public static final double TILL_LIMIT = 250;
    public static final int TABLES_LIMIT = 8;
    public static final int EMPLOYEE_LIMIT = 10;
    public static final int CLIENT_LIMIT = TABLES_LIMIT*4;
    
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
     * Supplier of all bars.
     */
    private static Supplier supplier;
    /**
     * Employee list with all instanciated employee of the this bar.
     */
    private ArrayList<Employee> employees = new ArrayList<>();
    /**
     * Possible clients of the bar.
     * (Is like the population that has access to bars as clients)
     */
    private ArrayList<Client> clients = new ArrayList<>();
    /**
     * Space that may be used by clients.
     * (Says if the client is in or out this bar)
     * (4 places per table at most, null if unused space)
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
    private Map<String, Integer> stocks = new HashMap<String, Integer>();
    /**
     * Number of drinks that ran out in stockfor this bar.
     */
    private int runOutDrinks = 0;
    /**
     * Record the number of belote tournaments that have been runned for this bar.
     */
    private int tournamentCount = 0;
    
    /**
     * Constructor
     * @param name 
     */
    public Bar(String name) {
        this.name = name;
        this.till = (int) (Math.random()*200);
        this.init();
    }
    
    
    // ----- Add/Remove (user) -----
    /**
     * Add a new waiter to the bar.
     * @param waiter : new instance
     * @throws exceptions.MaxCapacityReachedException
     */
    public void addWaiter(Waiter waiter) throws MaxCapacityReachedException {
        if (employees.size() == EMPLOYEE_LIMIT) {
            throw new MaxCapacityReachedException("Max number of employees already reached, "
            + "cannot add more.");
        }
        this.employees.add(waiter);
    }
    
    /**
     * Add a new barman to the bar.
     * @param barman 
     * @throws exceptions.MaxCapacityReachedException 
     */
    public void addBarman(Barman barman) throws MaxCapacityReachedException {
        if (employees.size() == EMPLOYEE_LIMIT) {
            throw new MaxCapacityReachedException("Max number of employees already reached, "
            + "cannot add more.");
        }
        this.employees.add(barman);
    }
    
    /**
     * Remove a barman from the employees list.
     * @param employee
     * @return true if the barman is successfully removed
     * @throws exceptions.MinCapacityReachedException 
     */
    public Boolean removeBarman(Employee employee) throws MinCapacityReachedException {
        if (getBarmans().size() == 1) {
            throw new MinCapacityReachedException("Min number of barmans already reached, "
            + "cannot remove more.");
        }
        return employees.remove(employee);
    }
    
    /**
     * Remove a waiter from the employees list.
     * @param employee
     * @return true if a waiter is successfully removed
     * @throws exceptions.MinCapacityReachedException 
     */
    public Boolean removeWaiter(Employee employee) throws MinCapacityReachedException {
        if (getWaiters().size() == 1) {
            throw new MinCapacityReachedException("Min number of waiters already reached, "
            + "cannot remove more.");
        }
        return employees.remove(employee);
    }
    
    /**
     * Remove a waiter from the employees list.
     * For changeGender use
     * @param employee to be removed
     * @param changeGender Boolean added to specify the use of this version
     * @return true if the waiter is successfully removed
     */
    public Boolean removeWaiter(Employee employee, Boolean changeGender) {
        if (changeGender) return employees.remove(employee);
        else return false;
    }
    
    /**
     * Add a new client to the bar.
     * @param client 
     * @throws exceptions.MaxCapacityReachedException 
     */
    public void addClient(Client client) throws MaxCapacityReachedException {
        if (clients.size() == TABLES_LIMIT*4) {
            throw new MaxCapacityReachedException("Max number of clients already reached, "
            + "cannot add more.");
        }
        this.clients.add(client);
    }
    
    /**
     * Remove a client from the clients list.
     * @param client
     * @return true if the client is successufully removed
     * @throws exceptions.MinCapacityReachedException 
     */
    public Boolean removeClient(Client client) throws MinCapacityReachedException {
        if (clients.size() == 1) {
            throw new MinCapacityReachedException("Min number of clients already reached, "
            + "cannot remove more.");
        }
        return clients.remove(client);
    }
    
    /**
     * Remove a client from the clients list.
     * For changeGender use
     * @param client to be removed
     * @param changeGender Boolean added to specify the use of this version
     * @return true of the client is successfully removed
     */
    public Boolean removeClient(Client client, Boolean changeGender) {
        if (changeGender) return clients.remove(client);
        else return false;
    }
    
    /**
     * Add new tables to the pub.
     * @throws exceptions.MaxCapacityReachedException
     */
    public void addTable() throws MaxCapacityReachedException {
        if (tables.size() == TABLES_LIMIT) {
            throw new MaxCapacityReachedException("Max number of tables already reached, "
            + "cannot add more.");
        }
        this.tables.add(new Client[4]);
    }
    
    /**
     * Remove the last table of the ArrayList of tables by making of clients to 
     * the removed table leave the pub.
     * @return
     * @throws MinCapacityReachedException 
     */
    public Boolean removeTable() throws MinCapacityReachedException {
        if (tables.size() == 1) {
            throw new MinCapacityReachedException("Min number of tables already reached, "
            + "cannot remove more.");
        }
        Client[] lastTable = tables.get(tables.size() - 1);
        for (int i = 0; i<lastTable.length; i++) {
            if (lastTable[i] != null) lastTable[i].leavePub();
        }
        return tables.remove(lastTable);
    }
    
    // ----- Displays (user) -----
    /**
     * Displays in the console for the user the drinks menu.
     */
    public void displayDrinksMenu() {
        System.out.println("Drinks menu !");
        for(int i = 0; i<drinksMenu.size(); i++) {
            System.out.println(" . " + drinksMenu.get(i).getName());
        }
    }
    
    /**
     * Displays the stocks as a list for the user in the console.
     */
    public void displayStock() {
        System.out.println("*** Stocks ***");
        this.stocks.keySet().forEach(e -> {
            System.out.println(" ." + e + " :   "+ this.stocks.get(e));
        });
        System.out.println("* * *    * * *");
    }
    
    /**
     * Displays all the informations on the waiter for the user.
     * Used in the management class for Information menu
     */
    @Override
    public void displayInformation() {
        System.out.println("[Pub]   "+name);
        System.out.println("    Boss: "+getBoss().getName()+" "+getBoss().getSurname());
        System.out.println("    Till balance: "+till);
        System.out.println("    Number of employees: "+getEmployees().size() +" / "+ EMPLOYEE_LIMIT);
        System.out.println("        -> Waiters: "+getWaiters().size());
        System.out.println("        -> Barmans: "+getBarmans().size());
        System.out.println("    Number of present clients: "+getPresentClients().size() +" / "+ CLIENT_LIMIT);
        System.out.println("    Number of tables: "+getTables().size() +" / "+ TABLES_LIMIT);
    }
    
    /**
     * Display the list of clients with numbers as id.
     */
    public void displayClients() {
        for (int i = 0; i<clients.size(); i++) {
            System.out.println("["+i+"] "+clients.get(i).getName()+" "+clients.get(i).getSurname());
        }
    }
    
    /**
     * Display the list of waiters with numbers as id.
     */
    public void displayWaiters() {
        for (int i = 0; i<getWaiters().size(); i++) {
            System.out.println("["+i+"] "+getWaiters().get(i).getName()+" "+getWaiters().get(i).getSurname());
        }
    }
    
    /**
     * Display the list of barmans with numbers as id.
     */
    public void displayBarmans() {
        for (int i = 0; i<getBarmans().size(); i++) {
            System.out.println("["+i+"] "+getBarmans().get(i).getName()+" "+getBarmans().get(i).getSurname());
        }
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
            System.err.println(e.getMessage());
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
        // Stocks
        for (int i = 0; i<drinksMenu.size(); i++) stocks.put(drinksMenu.get(i).getName(), HIGH_QUANTITY_LIMIT);
        
        // Humans :
        // - Boss
        this.boss = new Boss (
                this,
                Bar.FEMALE_NAMES.get((int) (Math.random()*Bar.FEMALE_NAMES.size())),
                Bar.SURNAMES.get((int) (Math.random()*Bar.SURNAMES.size())),
                (int) (Math.random() * 200),
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
            (int) (Math.random() * 200),
            (int) (Math.random()*10),
            Bar.SHOUTS.get((int) (Math.random()*Bar.SHOUTS.size())),
            (int) (Math.random()*10)
        ));
        
        this.employees.add(new FemaleWaiter(
            this,
            Bar.FEMALE_NAMES.get((int) (Math.random()*Bar.FEMALE_NAMES.size())),
            Bar.SURNAMES.get((int) (Math.random()*Bar.SURNAMES.size())),
            (int) (Math.random() * 200),
            (int) (Math.random()*10),
            Bar.SHOUTS.get((int) (Math.random()*Bar.SHOUTS.size())),
            (int) (Math.random()*10)
        ));
        // the barman does not really have a gender but both gender's names are possibles
        this.employees.add(new Barman(
            this,
            Bar.FEMALE_NAMES.get((int) (Math.random()*Bar.FEMALE_NAMES.size())),
            Bar.SURNAMES.get((int) (Math.random()*Bar.SURNAMES.size())),
            (int) (Math.random() * 200),
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
                    Bar.MALE_NAMES.get((int) (Math.random()*Bar.MALE_NAMES.size())),
                    Bar.SURNAMES.get((int) (Math.random()*Bar.SURNAMES.size())), 
                    (int) (Math.random() * 200),
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
                    Bar.FEMALE_NAMES.get((int) (Math.random()*Bar.FEMALE_NAMES.size())),
                    Bar.SURNAMES.get((int) (Math.random()*Bar.SURNAMES.size())),
                    (int) (Math.random() * 200),
                    (int) (Math.random()*10),
                    Bar.SHOUTS.get((int) (Math.random()*Bar.SHOUTS.size())),
                    Bar.drinksMenu.get((int) (Math.random()*Bar.drinksMenu.size())),
                    Bar.drinksMenu.get((int) (Math.random()*Bar.drinksMenu.size())),
                    (int) (Math.random()*10),
                    accessories
                )); 
            }
        }
        // - Supplier
        Bar.supplier = new Supplier(
                this,
                Bar.FEMALE_NAMES.get((int) (Math.random()*Bar.FEMALE_NAMES.size())),
                Bar.SURNAMES.get((int) (Math.random()*Bar.SURNAMES.size())),
                (int) (Math.random() * 200),
                (int) (Math.random()*10),
                Bar.SHOUTS.get((int) (Math.random()*Bar.SHOUTS.size()))
        );
    }
    
    // ----- Loaders ----- (for initiating every starting attributes)
    /**
     * Load data from a file.
     * @param pathName
     * @param separator
     * @return an ArrayList of each data parse to String
     * @throws IOException from file opening
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
     * @throws IOException from file opening
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
        // Teams
        Team.NAMES = Bar.loadData(Team.TEAM_NAMES_PATH, ",");
    }
    
    
    // ----- Setters -----
    /**
     * Set the till to its new balance.
     * @param NewBalance
     */
    public void setTillBalance(double NewBalance) {
        this.till = NewBalance;
    }
    
    /**
     * Set the number of drinks that ran out in stocks.
     * @param number integer to set
     */
    public void setRunOutDrinks(int number) {
        this.runOutDrinks = number;
    }
    
    /**
     * Set the tournament count.
     * @param newCount integer to set
     */
    public void serTournamentCount(int newCount) {
        this.tournamentCount = newCount;
    }
    
    // ----- Getters -----    
    /**
     * Get the name.
     * @return the string name of the bar
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Get the till balance.
     * @return the double balance of the bar
     */
    public double getTillBalance() {
        return this.till;
    }
    
    /**
     * Get the stocks (names, quantities) of the bar.
     * @return the mapping stock of all drink
     */
    public Map<String, Integer> getStocks() {
        return this.stocks;
    }
    
    /**
     * Get the quantity of a given drink from the stocks.
     * @param drink
     * @return the quantity
     */
    public int getStock(Drink drink) {
        return this.getStocks().get(drink.getName());
    }
    
    /**
     * Get the number of drinks that ran out in stocks.
     * @return the integer number
     */
    public int getRunOutDrinks() {
        return runOutDrinks;
    }
    
    /**
     * Get the list of all the potential clients.
     * @return all clients that may enter in the bar
     */
    public ArrayList<Client> getClients() {
        return this.clients;
    }
    
    /**
     * Get the tables.
     * @return all table arrays of the bar
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
                if (table[i]!= null) present.add(table[i]);
            }
        });
        return present;
    }
    
    /**
     * Get the employees.
     * @return all employees of the bar
     */
    public ArrayList<Employee> getEmployees() {
        return this.employees;
    }
    
    /**
     * Get the waiters.
     * @return all waiters of the bar
     */
    public ArrayList<Waiter> getWaiters() {
        ArrayList<Waiter> waiters = new ArrayList<>();
        for(int i = 0; i<employees.size(); i++) {
            if (employees.get(i) instanceof Waiter employee) waiters.add(employee);
        }
        return waiters;
    }
    
    /**
     * Get the barmans.
     * @return all barman of the bar
     */
    public ArrayList<Barman> getBarmans() {
        ArrayList<Barman> barmans = new ArrayList<>();
        for(int i = 0; i<employees.size(); i++) {
            if (employees.get(i) instanceof Barman employee) barmans.add(employee);
        }
        return barmans;
    }
    
    /**
     * Get the boss.
     * @return the boss of the bar
     */
    public Boss getBoss() {
        return this.boss;
    }
    
    /**
     * Get the supplier.
     * @return the supplier 
     */
    public static Supplier getSupplier() {
        return supplier;
    }
    
    /**
     * Get the drink menu of all bars.
     * @return the list of all drink objects of the bar
     */
    public static ArrayList<Drink> getDrinksMenu() {
        return drinksMenu;
    }
    
    /**
     * Get the drink Object from its name.
     * @param name
     * @return the drink 
     * @throws DoesNotExistException if the name is not one of the existant drinks.
     */
    public static Drink getDrink(String name) throws DoesNotExistException {
        ArrayList<String> drinkNames = new ArrayList<>();
        for(int i = 0; i<drinksMenu.size(); i++) {
            drinkNames.add(drinksMenu.get(i).getName());
        }
        if (!drinkNames.contains(name)) {
            throw new DoesNotExistException("The drink does not exist in the drink menu.");
        }
        
        for (int i = 0; i<drinksMenu.size(); i++) {
            if (drinksMenu.get(i).getName().equals(name)) return drinksMenu.get(i);
        }
        return null; // if not found but should not append
    }
    
    /**
     * Get the tournament count.
     * @return the number of runned tournaments
     */
    public int getTournamentCount() {
        return this.tournamentCount;
    }
}
