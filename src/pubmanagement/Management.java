/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pubmanagement;

import Exceptions.UnsupportedInputException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


/**
 *
 * @author eugenie_dalmas
 */
public class Management {
    
    public static final String CMD_MAIN_MENU = "M,Q,N";
    public static final String CMD_M_MENU = "Q,A,E,I,T,R";
    public static final String CMD_AE_MENU = "Q,C,W,B,R";
    public static final String CMD_I_MENU = "Q,C,W,B,S,F,P,D,R";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Bar OldGoat = new Bar("Old Goat");
        displayStarting();
        Boolean goodInput = false;
        while(!goodInput) {
            try {
                dialog(OldGoat);
                goodInput = true;
            } catch (UnsupportedInputException e) {
                System.err.println(e.getMessage());
            }
        }
    }
    
    /**
     * Quit sequence that ask a confirmation to the user before ending program.
     * @return false if the user did not quit
     */
    private static Boolean quit() {
        System.out.println("Are you sure you want to quit ?  No saves are made [O/X]");
        Scanner in = new Scanner(System.in);
        System.out.print(">>> ");
        String input = in.nextLine().toUpperCase();
        if (input.equals("O")) {
            System.out.println("See you !");
            System.exit(0);
            return true;
        }
        else if (input.equals("X")) return false;
        return false;
    }
    
    /**
     * Display the dialog menu (main display) and scan for user input.
     * @param bar
     * @throws UnsupportedInputException if a wrong command is entered
     */
    private static void dialog(Bar bar) throws UnsupportedInputException {
        System.out.println("--------------------------------------------------");
        System.out.println(" [M] Menu    [N] Next                    [Q] Quit ");
        System.out.println("--------------------------------------------------");
        String input = scanInput(CMD_MAIN_MENU);
        if (null != input) switch (input) {
            case "Q" -> {
                if(!quit()) {
                    System.out.println("You did not quit, choose a command !");
                    dialog(bar);
                }
            }
            case "N" -> {
                runShuffleLoop(bar);
                dialog(bar);
            }
            case "M" -> menu(bar);
            //default -> 
        }
    }
    
    /**
     * Display the menu main (M) and its options.
     * @param bar to get the information from
     * @throws UnsupportedInputException 
     */
    private static void menu(Bar bar) throws UnsupportedInputException {
        System.out.println("--------------------------------------------------");
        System.out.println(" -> Menu                      [R] Return [Q] Quit ");
        System.out.println(" [A] Add    [E] Erase    [I] Get Information");
        System.out.println("--------------------------------------------------");
        String input = scanInput(CMD_M_MENU);
        if (null != input) switch (input) {
            case "Q" -> {
                if(!quit()) {
                    System.out.println("You did not quit, choose a command !");
                    menu(bar);
                }
            }
            case "A" -> add(bar);
            case "E" -> erase(bar);
            case "I" -> information(bar);
            case "R" -> dialog(bar);
            default -> {
            }
        }
    }
    
    /**
     * Display the menu information (I) and its options.
     * @param bar to get the information from
     * @throws UnsupportedInputException 
     */
    private static void information(Bar bar) throws UnsupportedInputException {
        System.out.println("--------------------------------------------------");
        System.out.println(" -> Information               [R] Return [Q] Quit ");
        System.out.println(" [C] Client [W] Waiter  [B] Barman [S] Supplier   ");
        System.out.println(" [F] Boss   [P] Pub     [D] Drinks                ");
        System.out.println("--------------------------------------------------");
        String input = scanInput(CMD_I_MENU);
        if (null != input) switch (input) {
            case "Q" -> {
                if(!quit()) {
                    System.out.println("You did not quit, choose a command !");
                    information(bar);
                }
            }
            case "C" -> {
                bar.displayClients();
                clientInfo(bar);
            }
            case "W" -> {
                bar.displayWaiters();
                waiterInfo(bar);
            }
            case "B" -> {
                bar.displayBarmans();
                barmanInfo(bar);
            }
            case "S" -> {
                Bar.getSupplier().displayInformation();
                information(bar);
            }
            case "F" -> {
                bar.getBoss().displayInformation();
                information(bar);
            }
            case "P" -> {
                bar.displayInformation();
                information(bar);
            }
            case "D" -> {
                bar.displayDrinksMenu();
                information(bar);
            }
            case "R" -> menu(bar);
            default -> information(bar);
        }
    }
    
    /**
     * Display the clients and let the user choose which one displays its 
     * informations.
     * @param bar
     * @throws UnsupportedInputException 
     */
    private static void clientInfo(Bar bar) throws UnsupportedInputException {
        System.out.println("[R] Return  [Q] Quit");
        String clientCMD = "Q,R,E,";
        for (int i = 0; i<bar.getClients().size(); i++) {
            if (i == bar.getClients().size() - 1) clientCMD += Integer.toString(i);
            else clientCMD += Integer.toString(i)+",";
        }
        String input = scanInput(clientCMD);
        if (null != input) switch (input) {
            case "Q" -> {
                if(!quit()) {
                    System.out.println("You did not quit, choose a command !");
                    clientInfo(bar);
                }
            }
            case "R" -> information(bar);
            //case "E" ->  clientInfo(bar);
            default ->  {
                bar.getClients().get(Integer.parseInt(input)).displayInformation();
                clientInfo(bar);
            }
        }
    }
    
    /**
     * Display the waiters and let the user choose which one displays its 
     * informations.
     * @param bar
     * @throws UnsupportedInputException 
     */
    private static void waiterInfo(Bar bar) throws UnsupportedInputException {
        System.out.println("[R] Return  [Q] Quit");
        String waiterCMD = "Q,R,";
        for (int i = 0; i<bar.getWaiters().size(); i++) {
            if (i == bar.getWaiters().size() - 1) waiterCMD += Integer.toString(i);
            else waiterCMD += Integer.toString(i)+",";
        }
        String input = scanInput(waiterCMD);
        if (null != input) switch (input) {
            case "Q" -> {
                if(!quit()) {
                    System.out.println("You did not quit, choose a command !");
                    waiterInfo(bar);
                }
            }
            case "R" -> information(bar);
            default ->  {
                bar.getWaiters().get(Integer.parseInt(input)).displayInformation();
                waiterInfo(bar);
            }
        }
    }
    
    /**
     * Display the barmans and let the user choose which one displays its 
     * informations.
     * @param bar
     * @throws UnsupportedInputException 
     */
    private static void barmanInfo(Bar bar) throws UnsupportedInputException {
        System.out.println("[R] Return  [Q] Quit");
        String barmanCMD = "Q,R,";
        for (int i = 0; i<bar.getBarmans().size(); i++) {
            if (i == bar.getBarmans().size() - 1) barmanCMD += Integer.toString(i);
            else barmanCMD += Integer.toString(i)+",";
        }
        String input = scanInput(barmanCMD);
        if (null != input) switch (input) {
            case "Q" -> {
                if(!quit()) {
                    System.out.println("You did not quit, choose a command !");
                    barmanInfo(bar);
                }
            }
            case "R" -> information(bar);
            default ->  {
                bar.getBarmans().get(Integer.parseInt(input)).displayInformation();
                barmanInfo(bar);
            }
        }
    } 
    
    /**
     * Display the add menu.
     * @param bar
     * @throws UnsupportedInputException 
     */
    private static void add(Bar bar) throws UnsupportedInputException {
        System.out.println("--------------------------------------------------");
        System.out.println(" Menu -> Add                  [R] Return [Q] Quit ");
        System.out.println(" [C] Client [W] Waiter  [B] Barman ");
        System.out.println("--------------------------------------------------");
        String input = scanInput(CMD_AE_MENU);
        if (null != input) switch (input) {
            case "Q" -> {
                if(!quit()) {
                    System.out.println("You did not quit, choose a command !");
                    add(bar);
                }
            }
            case "C" -> {
                addClient(bar);
            }
            case "W" -> {
                addWaiter(bar);
            }
            case "B" -> {
                addBarman(bar);
            }
            case "R" -> menu(bar);
            default -> add(bar);
        }
    }
    
    /**
     * Make the generation of a new client either manually (user) or automatically.
     * @param bar
     * @throws UnsupportedInputException 
     */
    private static void addClient(Bar bar) throws UnsupportedInputException {
        Scanner in = new Scanner(System.in);
        System.out.println(" [A] Auto   [M] Manual        [R] Return");
        String input = scanInput("A,M,Q,R");
        if (null != input) switch (input) {
            case "Q" -> {
                if(!quit()) {
                    System.out.println("You did not quit, choose a command !");
                    add(bar);
                }
            }
            case "R" -> add(bar);
            case "A" -> {
                System.out.println("Automatic generation...");
                double rand = Math.random();
                // equally chance of generation between male and female
                Client newClient;
                if (rand < 0.5) {
                    newClient = new MaleClient(
                        bar,
                        Bar.MALE_NAMES.get((int) (Math.random()*Bar.FEMALE_NAMES.size())),
                        Bar.SURNAMES.get((int) (Math.random()*Bar.SURNAMES.size())), 
                        (int) (Math.random() * 200),
                        (int) (Math.random()*10),
                        Bar.SHOUTS.get((int) (Math.random()*Bar.SHOUTS.size())),
                        Bar.getDrinksMenu().get((int) (Math.random()*Bar.getDrinksMenu().size())),
                        Bar.getDrinksMenu().get((int) (Math.random()*Bar.getDrinksMenu().size())),
                        (int) (Math.random()*10),
                        Bar.COLORS.get((int) (Math.random()*Bar.COLORS.size()))+" tee-shirt"
                    );
                }
                else {
                    ArrayList<String> accessories = new ArrayList<>();
                    for (int j = 0; j<(int) (Math.random()*5); j++) {
                        accessories.add(Bar.ACCESSORIES.get((int) (Math.random()*Bar.ACCESSORIES.size())));
                    }
                    newClient = new FemaleClient(
                        bar,
                        Bar.FEMALE_NAMES.get((int) (Math.random()*Bar.FEMALE_NAMES.size())),
                        Bar.SURNAMES.get((int) (Math.random()*Bar.SURNAMES.size())),
                        (int) (Math.random() * 200),
                        (int) (Math.random()*10),
                        Bar.SHOUTS.get((int) (Math.random()*Bar.SHOUTS.size())),
                        Bar.getDrinksMenu().get((int) (Math.random()*Bar.getDrinksMenu().size())),
                        Bar.getDrinksMenu().get((int) (Math.random()*Bar.getDrinksMenu().size())),
                        (int) (Math.random()*10),
                        accessories
                    ); 
                }
                bar.addClient(newClient);
                System.out.println("Client "+newClient.getName()+" "+newClient.getSurname()+" created !"); 
                add(bar);
            }
            case "M" -> {
                // gender name surname shout beloteLevel
                System.out.println("Client -> Manual generation, please enter the following information: ");
                System.out.println("Gender : [F] female [M] male");
                String gender = scanInput("F,M");
                System.out.println("Name :");
                input = in.nextLine();
                String name = input.substring(0, 1).toUpperCase() + input.substring(1);
                System.out.println("Surname :");
                input = in.nextLine();
                String surname = input.substring(0, 1).toUpperCase() + input.substring(1);
                System.out.println("Belote level : (range 0 to 10)");
                int beloteLevel = Integer.parseInt(scanInput("0,1,2,3,4,5,6,7,8,9,10")); // un peu moche mais court
                
                if (gender.equals("F")) {
                    ArrayList<String> accessories = new ArrayList<>();
                    for (int j = 0; j<(int) (Math.random()*5); j++) {
                        accessories.add(Bar.ACCESSORIES.get((int) (Math.random()*Bar.ACCESSORIES.size())));
                    }
                    bar.addClient(new FemaleClient(
                        bar, name, surname,
                        (int) (Math.random() * 200),
                        (int) (Math.random()*10),
                        Bar.SHOUTS.get((int) (Math.random()*Bar.SHOUTS.size())),
                        Bar.getDrinksMenu().get((int) (Math.random()*Bar.getDrinksMenu().size())),
                        Bar.getDrinksMenu().get((int) (Math.random()*Bar.getDrinksMenu().size())),
                        beloteLevel, accessories
                    ));   
                }
                else {
                    bar.addClient(new MaleClient(
                        bar, name, surname,
                        (int) (Math.random() * 200),
                        (int) (Math.random()*10),
                        Bar.SHOUTS.get((int) (Math.random()*Bar.SHOUTS.size())),
                        Bar.getDrinksMenu().get((int) (Math.random()*Bar.getDrinksMenu().size())),
                        Bar.getDrinksMenu().get((int) (Math.random()*Bar.getDrinksMenu().size())),
                        beloteLevel,
                        Bar.COLORS.get((int) (Math.random()*Bar.COLORS.size()))+" tee-shirt"
                    ));
                }
                System.out.println("Client "+name+" "+surname+" created !");
                add(bar);
            }
        }
    }
    
    /**
     * Make the generation of a new waiter either manually (user) or automatically.
     * @param bar
     * @throws UnsupportedInputException 
     */
    private static void addWaiter(Bar bar) throws UnsupportedInputException {
        Scanner in = new Scanner(System.in);
        System.out.println(" [A] Auto   [M] Manual        [R] Return");
        String input = scanInput("A,M,Q,R");
        if (null != input) switch (input) {
            case "Q" -> {
                if(!quit()) {
                    System.out.println("You did not quit, choose a command !");
                    add(bar);
                }
            }
            case "R" -> add(bar);
            case "A" -> {
                System.out.println("Automatic generation...");
                double rand = Math.random();
                // equally chance of generation between male and female
                Waiter newWaiter;
                if (rand < 0.5) {
                    newWaiter = new MaleWaiter(
                        bar,
                        Bar.MALE_NAMES.get((int) (Math.random()*Bar.MALE_NAMES.size())),
                        Bar.SURNAMES.get((int) (Math.random()*Bar.SURNAMES.size())), 
                        (int) (Math.random() * 200),
                        (int) (Math.random()*10),
                        Bar.SHOUTS.get((int) (Math.random()*Bar.SHOUTS.size())),
                        (int) (Math.random()*10)
                    );
                }
                else {
                    newWaiter = new FemaleWaiter(
                        bar,
                        Bar.FEMALE_NAMES.get((int) (Math.random()*Bar.FEMALE_NAMES.size())),
                        Bar.SURNAMES.get((int) (Math.random()*Bar.SURNAMES.size())), 
                        (int) (Math.random() * 200),
                        (int) (Math.random()*10),
                        Bar.SHOUTS.get((int) (Math.random()*Bar.SHOUTS.size())),
                        (int) (Math.random()*10)
                    ); 
                }
                bar.addWaiter(newWaiter);
                System.out.println("Waiter "+newWaiter.getName()+" "+newWaiter.getSurname()+" created !"); 
                add(bar);
            }
            case "M" -> {
                // gender name surname shout beloteLevel
                System.out.println("Waiter -> Manual generation, please enter the following information: ");
                System.out.println("Gender : [F] female [M] male");
                String gender = scanInput("F,M");
                System.out.println("Name :");
                input = in.nextLine();
                String name = input.substring(0, 1).toUpperCase() + input.substring(1);
                System.out.println("Surname :");
                input = in.nextLine();
                String surname = input.substring(0, 1).toUpperCase() + input.substring(1);
                System.out.println("Charm/Biceps level : (range 0 to 10)");
                int attribute = Integer.parseInt(scanInput("0,1,2,3,4,5,6,7,8,9,10")); // un peu moche mais court
                
                if (gender.equals("F")) {
                    bar.addWaiter(new FemaleWaiter(
                        bar, name, surname,
                        (int) (Math.random() * 200),
                        (int) (Math.random()*10),
                        Bar.SHOUTS.get((int) (Math.random()*Bar.SHOUTS.size())),
                        attribute
                    ));   
                }
                else {
                    bar.addWaiter(new MaleWaiter(
                        bar, name, surname,
                        (int) (Math.random() * 200),
                        (int) (Math.random()*10),
                        Bar.SHOUTS.get((int) (Math.random()*Bar.SHOUTS.size())),
                        attribute
                    ));   
                }
                System.out.println("Waiter "+name+" "+surname+" created !");
                add(bar);
            }
        }
    }
    
    /**
     * Make the generation of a new barman either manually (user) or automatically.
     * @param bar
     * @throws UnsupportedInputException 
     */
    private static void addBarman(Bar bar) throws UnsupportedInputException {
        Scanner in = new Scanner(System.in);
        System.out.println(" [A] Auto   [M] Manual        [R] Return");
        String input = scanInput("A,M,Q,R");
        if (null != input) switch (input) {
            case "Q" -> {
                if(!quit()) {
                    System.out.println("You did not quit, choose a command !");
                    add(bar);
                }
            }
            case "R" -> add(bar);
            case "A" -> {
                System.out.println("Automatic generation...");
                double rand = Math.random(); 
                
                Barman newBarman = new Barman(
                    bar,
                    Bar.MALE_NAMES.get((int) (Math.random()*Bar.MALE_NAMES.size())),
                    Bar.SURNAMES.get((int) (Math.random()*Bar.SURNAMES.size())), 
                    (int) (Math.random() * 200),
                    (int) (Math.random()*10),
                    Bar.SHOUTS.get((int) (Math.random()*Bar.SHOUTS.size()))
                );
                
                bar.addBarman(newBarman);
                System.out.println("Barman "+newBarman.getName()+" "+newBarman.getSurname()+" created !"); 
                add(bar);
            }
            case "M" -> {
                // gender name surname shout beloteLevel
                System.out.println("Waiter -> Manual generation, please enter the following information: ");
                System.out.println("Name :");
                input = in.nextLine();
                String name = input.substring(0, 1).toUpperCase() + input.substring(1);
                System.out.println("Surname :");
                input = in.nextLine();
                String surname = input.substring(0, 1).toUpperCase() + input.substring(1);
                
                Barman newBarman = new Barman(
                    bar, name, surname, 
                    (int) (Math.random() * 200),
                    (int) (Math.random()*10),
                    Bar.SHOUTS.get((int) (Math.random()*Bar.SHOUTS.size()))
                );
                
                System.out.println("Barman "+name+" "+surname+" created !");
                add(bar);
            }
        }
    }
    
    private static void erase(Bar bar) throws UnsupportedInputException {
        System.out.println("--------------------------------------------------");
        System.out.println(" -> Menu -> Erase             [R] Return [Q] Quit ");
        System.out.println(" [C] Client [W] Waiter  [B] Barman ");
        System.out.println("--------------------------------------------------");
        String input = scanInput(CMD_AE_MENU);
        
    }
    
    /**
     * Run a loop of a shuffled list of the present humans in the bar.
     * @param bar
     * @param clients that are present
     */
    private static void runShuffleLoop(Bar bar) {
        ArrayList<Client> clients = bar.getPresentClients();
        ArrayList<Human> shuffled = getShuffled(bar);
        for (int i = 0; i<shuffled.size()/2; i++) {
            shuffled.get(i).action(clients);
        }
    }
    
    /**
     * Get a random human between all that are present in the bar.
     * @param bar 
     * @return a random Human
     */
    private static Human getRandomHumanPresent(Bar bar) {
        ArrayList<Client> clients = bar.getClients();
        ArrayList<Employee> employees = bar.getEmployees();
        
        int totalSize = clients.size()+employees.size()+1; // +1 for boss
        int randIndex = (int) (Math.random() * totalSize);
        
        if (randIndex < clients.size()) return (Human) clients.get(randIndex %clients.size());
        if (randIndex < clients.size() + employees.size()) return (Human) employees.get(randIndex %clients.size());
        else return bar.getBoss();
    }
    
    /**
     * Get a shuffled list of all present human in the bar.
     * @param bar
     * @return an ArrayList of Human
     */
    private static ArrayList<Human> getShuffled(Bar bar) {
        ArrayList<Client> clients = bar.getClients();
        ArrayList<Employee> employees = bar.getEmployees();
        
        // get all humans of the bar in one array list
        ArrayList<Human> all = new ArrayList<>();
        for (int i = 0; i<clients.size(); i++) all.add((Human) clients.get(i));
        for (int i = 0; i<employees.size(); i++) all.add((Human) employees.get(i));
        all.add((Human) bar.getBoss());
        
        // compute a shuffle order of indexes
        Integer[] intArray = new Integer[all.size()];
        for (int i = 0; i<intArray.length; i++) intArray[i] = i;
        List<Integer> list = Arrays.asList(intArray);
        Collections.shuffle(list);
        Integer[] order = list.toArray(intArray);
        ArrayList<Human> shuffled = new ArrayList<>();
        for (int i = 0; i<order.length; i++) shuffled.add(all.get(order[i]));
        
        return shuffled;
    }
    
    /**
     * Display the welcoming message.
     */
    private static void displayStarting() {
        System.out.println();
        System.out.println("Welcome to your pub !\n");
        System.out.println("Use the letters from the bar menu to go through\n" 
                         + "the dialogs (N) or display the menu. This menu\n"
                         + "[M] will allow to access other functionalities of\n"
                         + "this program.\nYou may quit anytime with [Q].");
    }
    
    /**
     * Scan the input of the user.
     * @param CMD constant String of all available command letters
     * @return the String input
     * @throws UnsupportedInputException 
     */
    private static String scanInput(String CMD) throws UnsupportedInputException {
        ArrayList<String> cmd = new ArrayList<>(Arrays.asList(CMD.split(",")));
        Scanner in = new Scanner(System.in);
        System.out.print(">>> ");
        String input = in.nextLine().toUpperCase();
        if (!cmd.contains(input)) 
            throw new UnsupportedInputException("Input is not one of the available option.");
        return input;
    }
}
