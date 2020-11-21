/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pubmanagement;

import Belote.*;

import Exceptions.MaxCapacityReachedException;
import Exceptions.MinCapacityReachedException;
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
public abstract class Management {
    
    /*
     * Commands that are available for the user and commun.
     */
    public static final String CMD_MAIN_MENU = "M,Q,N";
    public static final String CMD_M_MENU = "Q,A,E,I,B,S,R";
    public static final String CMD_A_MENU = "Q,C,W,B,T,R";
    public static final String CMD_I_MENU = "Q,C,W,B,S,F,P,D,R";
    public static final String CMD_B_MENU = "Q,G,T,R";
    
    private static ArrayList<Bar> userBars = new ArrayList<>();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        userBars.add(displayStarting());
        dialog(userBars.get(0)); // start with main bar
    }
    
    /**
     * Display the welcoming message and ask for the user's bar name.
     */
    private static Bar displayStarting() {
        System.out.println();
        System.out.println("Welcome to your pub !\n");
        System.out.println("Use the letters from the bar menu to go through\n" 
                         + "the dialogs (N) or display the menu. This menu\n"
                         + "[M] will allow to access other functionalities of\n"
                         + "this program.\nYou may quit anytime with [Q].");
        System.out.println("\nWhat is your pub called ?");
        Scanner in = new Scanner(System.in);
        System.out.print(">>> ");
        String input = in.nextLine();
        return new Bar(input);
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
    private static void dialog(Bar bar) {
        System.out.println("--------------------------------------------------");
        System.out.println(" * "+bar.getName()+" * ");
        System.out.println(" [M] Menu    [N] Next                    [Q] Quit ");
        System.out.println("--------------------------------------------------");
        String input = null;
        try { input = scanInput(CMD_MAIN_MENU); }
        catch (UnsupportedInputException e) {
            System.err.println(e.getMessage());
            dialog(bar);
        }
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
        }
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
     * Display the menu main (M) and its options.
     * @param bar to get the information from
     * @throws UnsupportedInputException 
     */
    private static void menu(Bar bar) {
        System.out.println("--------------------------------------------------");
        System.out.println(" -> Menu                      [R] Return [Q] Quit ");
        System.out.println(" [A] Add        [I] Information ");
        System.out.println(" [B] Belote     [S] Switch Pub  ");
        System.out.println("--------------------------------------------------");
        String input = null;
        try { input = scanInput(CMD_M_MENU); }
        catch (UnsupportedInputException e) {
            System.err.println(e.getMessage());
            menu(bar);
        }
        if (null != input) switch (input) {
            case "Q" -> {
                if(!quit()) {
                    System.out.println("You did not quit, choose a command !");
                    menu(bar);
                }
            }
            case "A" -> add(bar);
            case "I" -> information(bar);
            case "B" -> belote(bar);
            case "S" -> switchPub(bar);
            case "R" -> dialog(bar);
        }
    }
    
    /**
     * Display the add menu.
     * @param bar
     */
    private static void add(Bar bar) {
        System.out.println("--------------------------------------------------");
        System.out.println(" Menu -> Add                  [R] Return [Q] Quit ");
        System.out.println(" [C] Client [W] Waiter  [B] Barman  [T] Table ");
        System.out.println("--------------------------------------------------");
        String input = null;
        try { input = scanInput(CMD_A_MENU); }
        catch (UnsupportedInputException e) {
            System.err.println(e.getMessage());
            add(bar);
        }
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
            case "T" -> {
                try {
                    bar.addTable();
                    System.out.println("One table added");
                    add(bar);
                } 
                catch(MaxCapacityReachedException e) {System.err.println(e.getMessage());}
                
            }
            default -> add(bar);
        }
    }
    
    /**
     * Make the generation of a new client either manually (user) or automatically.
     * @param bar
     */
    private static void addClient(Bar bar) {
        Scanner in = new Scanner(System.in);
        System.out.println(" [A] Auto   [M] Manual        [R] Return");
        String input = null;
        try { input = scanInput("A,M,Q,R"); }
        catch (UnsupportedInputException e) {
            System.err.println(e.getMessage());
            addClient(bar);
        }
        if (null != input) switch (input) {
            case "Q" -> {
                if(!quit()) {
                    System.out.println("You did not quit, choose a command !");
                    addClient(bar);
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
                try {
                    bar.addClient(newClient);
                    System.out.println("Client "+newClient.getName()+" "+newClient.getSurname()+" created !"); 
                }
                catch (MaxCapacityReachedException e) { System.err.println(e.getMessage()); }
                add(bar);
            }
            case "M" -> {
                // gender name surname shout beloteLevel
                System.out.println("Client -> Manual generation, please enter the following information: ");
                System.out.println("Gender : [F] female [M] male");
                String gender = "F";
                try { gender = scanInput("F,M"); }
                catch (UnsupportedInputException e) {
                    System.err.println(e.getMessage());
                    addClient(bar);
                }
                System.out.println("Name :");
                input = in.nextLine();
                String name = input.substring(0, 1).toUpperCase() + input.substring(1);
                System.out.println("Surname :");
                input = in.nextLine();
                String surname = input.substring(0, 1).toUpperCase() + input.substring(1);
                System.out.println("Belote level : (range 0 to 10)");
                int beloteLevel = 0;
                try { beloteLevel = Integer.parseInt(scanInput("0,1,2,3,4,5,6,7,8,9,10")); }
                catch (UnsupportedInputException e) {
                    System.err.println(e.getMessage());
                    
                }
                
                Client newClient;
                if (gender.equals("F")) {
                    ArrayList<String> accessories = new ArrayList<>();
                    for (int j = 0; j<(int) (Math.random()*5); j++) {
                        accessories.add(Bar.ACCESSORIES.get((int) (Math.random()*Bar.ACCESSORIES.size())));
                    }
                    newClient = new FemaleClient(
                        bar, name, surname,
                        (int) (Math.random() * 200),
                        (int) (Math.random()*10),
                        Bar.SHOUTS.get((int) (Math.random()*Bar.SHOUTS.size())),
                        Bar.getDrinksMenu().get((int) (Math.random()*Bar.getDrinksMenu().size())),
                        Bar.getDrinksMenu().get((int) (Math.random()*Bar.getDrinksMenu().size())),
                        beloteLevel, accessories
                    );   
                }
                else {
                    newClient = new MaleClient(
                        bar, name, surname,
                        (int) (Math.random() * 200),
                        (int) (Math.random()*10),
                        Bar.SHOUTS.get((int) (Math.random()*Bar.SHOUTS.size())),
                        Bar.getDrinksMenu().get((int) (Math.random()*Bar.getDrinksMenu().size())),
                        Bar.getDrinksMenu().get((int) (Math.random()*Bar.getDrinksMenu().size())),
                        beloteLevel,
                        Bar.COLORS.get((int) (Math.random()*Bar.COLORS.size()))+" tee-shirt"
                    );
                }
                try {
                    bar.addClient(newClient);
                    System.out.println("Client "+name+" "+surname+" created !"); 
                }
                catch (MaxCapacityReachedException e) { System.err.println(e.getMessage()); }
                add(bar);
            }
        }
    }
    
    /**
     * Make the generation of a new waiter either manually (user) or automatically.
     * @param bar
     */
    private static void addWaiter(Bar bar) {
        Scanner in = new Scanner(System.in);
        System.out.println(" [A] Auto   [M] Manual        [R] Return");
        String input = null;
        try { input = scanInput("A,M,Q,R"); }
        catch (UnsupportedInputException e) {
            System.err.println(e.getMessage());
            addWaiter(bar);
        }
        if (null != input) switch (input) {
            case "Q" -> {
                if(!quit()) {
                    System.out.println("You did not quit, choose a command !");
                    addWaiter(bar);
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
                try {
                    bar.addWaiter(newWaiter);
                    System.out.println("Waiter "+newWaiter.getName()+" "+newWaiter.getSurname()+" created !"); 
                }
                catch (MaxCapacityReachedException e) { System.err.println(e.getMessage()); }
                add(bar);
            }
            case "M" -> {
                // gender name surname shout beloteLevel
                System.out.println("Waiter -> Manual generation, please enter the following information: ");
                System.out.println("Gender : [F] female [M] male");
                String gender = "F";
                try { gender = scanInput("F,M"); }
                catch (UnsupportedInputException e) {
                    System.err.println(e.getMessage());
                    addClient(bar);
                }
                System.out.println("Name :");
                input = in.nextLine();
                String name = input.substring(0, 1).toUpperCase() + input.substring(1);
                System.out.println("Surname :");
                input = in.nextLine();
                String surname = input.substring(0, 1).toUpperCase() + input.substring(1);
                System.out.println("Charm/Biceps level : (range 0 to 10)");
                int attribute = 0;
                try { attribute = Integer.parseInt(scanInput("0,1,2,3,4,5,6,7,8,9,10")); }
                catch (UnsupportedInputException e) {
                    System.err.println(e.getMessage());
                    addWaiter(bar);
                }
                
                Waiter newWaiter;
                if (gender.equals("F")) {
                    newWaiter = new FemaleWaiter(
                        bar, name, surname,
                        (int) (Math.random() * 200),
                        (int) (Math.random()*10),
                        Bar.SHOUTS.get((int) (Math.random()*Bar.SHOUTS.size())),
                        attribute
                    );   
                }
                else {
                    newWaiter = new MaleWaiter(
                        bar, name, surname,
                        (int) (Math.random() * 200),
                        (int) (Math.random()*10),
                        Bar.SHOUTS.get((int) (Math.random()*Bar.SHOUTS.size())),
                        attribute
                    );   
                }
                try {
                    bar.addWaiter(newWaiter);
                    System.out.println("Waiter "+name+" "+surname+" created !"); 
                }
                catch (MaxCapacityReachedException e) { System.err.println(e.getMessage()); }
                add(bar);
            }
        }
    }
    
    /**
     * Make the generation of a new barman either manually (user) or automatically.
     * @param bar
     */
    private static void addBarman(Bar bar) {
        Scanner in = new Scanner(System.in);
        System.out.println(" [A] Auto   [M] Manual        [R] Return");
        String input = null;
        try { input = scanInput("A,M,Q,R"); }
        catch (UnsupportedInputException e) {
            System.err.println(e.getMessage());
            addBarman(bar);
        }
        if (null != input) switch (input) {
            case "Q" -> {
                if(!quit()) {
                    System.out.println("You did not quit, choose a command !");
                    addBarman(bar);
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
                try {
                    bar.addBarman(newBarman);
                    System.out.println("Barman "+newBarman.getName()+" "+newBarman.getSurname()+" created !"); 
                }
                catch (MaxCapacityReachedException e) { System.err.println(e.getMessage()); }
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
                try {
                    bar.addBarman(newBarman);
                    System.out.println("Barman "+name+" "+surname+" created !"); 
                }
                catch (MaxCapacityReachedException e) { System.err.println(e.getMessage()); }
                add(bar);
            }
        }
    }
    
    /**
     * Display the menu information (I) and its options.
     * @param bar to get the information from
     * @throws UnsupportedInputException 
     */
    private static void information(Bar bar) {
        System.out.println("--------------------------------------------------");
        System.out.println(" Menu -> Information          [R] Return [Q] Quit ");
        System.out.println(" [C] Client [W] Waiter  [B] Barman  [S] Supplier  ");
        System.out.println(" [F] Boss   [P] Pub     [D] Drinks                ");
        System.out.println("--------------------------------------------------");
        String input = null;
        try { input = scanInput(CMD_I_MENU); }
        catch (UnsupportedInputException e) {
            System.err.println(e.getMessage());
            information(bar);
        }
        if (null != input) switch (input) {
            case "Q" -> {
                if(!quit()) {
                    System.out.println("You did not quit, choose a command !");
                    information(bar);
                }
            }
            case "C" -> {
                System.out.println(" -> Client Info     [E] Erase [R] Return  [Q] Quit");
                bar.displayClients();
                clientInfo(bar);
            }
            case "W" -> {
                System.out.println(" -> Waiter info     [E] Erase [R] Return  [Q] Quit");
                bar.displayWaiters();
                waiterInfo(bar);
            }
            case "B" -> {
                System.out.println(" -> Barman Info     [E] Erase [R] Return  [Q] Quit");
                bar.displayBarmans();
                barmanInfo(bar);
            }
            case "S" -> {
                System.out.println(" -> Supplier Info");
                Bar.getSupplier().introduce();
                Bar.getSupplier().displayInformation();
                information(bar);
            }
            case "F" -> {
                System.out.println(" -> Boss Info");
                bar.getBoss().introduce();
                bar.getBoss().displayInformation();
                information(bar);
            }
            case "P" -> {
                System.out.println(" -> Pub Info       [S] Stocks [R] Return  [Q] Quit");
                pubInfo(bar);
            }
            case "D" -> {
                System.out.println(" -> Drinks Info");
                bar.displayDrinksMenu();
                information(bar);
            }
            case "R" -> menu(bar);
            default -> information(bar);
        }
    }
    
    /**
     * Display the pub inforamtion and the user may choose to display the stocks.
     * @param bar
     */
    private static void pubInfo(Bar bar) {
        bar.displayInformation();
        System.out.println(" -> Pub Info       [S] Stocks [R] Return  [Q] Quit");
        System.out.println("                   [T] Erase a table");
        String input = null;
        try { input = scanInput("Q,R,S,T"); }
        catch (UnsupportedInputException e) {
            System.err.println(e.getMessage());
            pubInfo(bar);
        }
        if (null != input) switch (input) {
            case "Q" -> {
                if(!quit()) {
                    System.out.println("You did not quit, choose a command !");
                    clientInfo(bar);
                }
            }
            case "R" -> information(bar);
            case "S" -> {
                bar.displayStock();
                information(bar);
            }
            case "T" -> {
                try { 
                    bar.removeTable();
                    System.out.println("A table removed");
                }
                catch(MinCapacityReachedException e) { System.err.println(e.getMessage()); }
                pubInfo(bar);
            }
        }
    }
    
    /**
     * Display the clients and let the user choose which one displays its 
     * informations.
     * @param bar
     */
    private static void clientInfo(Bar bar) {
        System.out.println(" -> Client Info     [E] Erase [R] Return  [Q] Quit");
        String clientCMD = "E,Q,R,";
        for (int i = 0; i<bar.getClients().size(); i++) {
            if (i == bar.getClients().size() - 1) clientCMD += Integer.toString(i);
            else clientCMD += Integer.toString(i)+",";
        }
        String input = null;
        try { input = scanInput(clientCMD); }
        catch (UnsupportedInputException e) {
            System.err.println(e.getMessage());
            clientInfo(bar);
        }
        if (null != input) switch (input) {
            case "Q" -> {
                if(!quit()) {
                    System.out.println("You did not quit, choose a command !");
                    clientInfo(bar);
                }
            }
            case "R" -> information(bar);
            case "E" -> eraseClient(bar, clientCMD);
            default ->  {
                bar.getClients().get(Integer.parseInt(input)).introduce();
                bar.getClients().get(Integer.parseInt(input)).displayInformation();
                clientInfo(bar);
            }
        }
    }
    
    /**
     * Erase a client from the user input which is the index of the existant one.
     * @param bar
     * @param cmd 
     */
    private static void eraseClient(Bar bar, String cmd) {
        System.out.println("Which client would you like to erase ?");
        String input = null;
        try { input = scanInput(cmd.substring(2)); }
        catch (UnsupportedInputException e) {
            System.err.println(e.getMessage());
            clientInfo(bar);
        }
        if (null != input) switch (input) {
            case "Q" -> {
                if(!quit()) {
                    System.out.println("You did not quit, choose a command !");
                    eraseClient(bar, cmd);
                }
            }
            case "R" -> clientInfo(bar); 
            default -> {
                try { 
                    bar.removeClient(bar.getClients().get(Integer.parseInt(input))); 
                    clientInfo(bar);
                }
                catch (MinCapacityReachedException e) { System.err.println(e.getMessage()); }
            }
        }
    }
    
    /**
     * Display the waiters and let the user choose which one displays its 
     * informations.
     * @param bar
     */
    private static void waiterInfo(Bar bar) {
        System.out.println(" -> Waiter info     [E] Erase [R] Return  [Q] Quit");
        String waiterCMD = "Q,E,R,";
        for (int i = 0; i<bar.getWaiters().size(); i++) {
            if (i == bar.getWaiters().size() - 1) waiterCMD += Integer.toString(i);
            else waiterCMD += Integer.toString(i)+",";
        }
        String input = null;
        try { input = scanInput(waiterCMD); }
        catch (UnsupportedInputException e) {
            System.err.println(e.getMessage());
            clientInfo(bar);
        }
        if (null != input) switch (input) {
            case "Q" -> {
                if(!quit()) {
                    System.out.println("You did not quit, choose a command !");
                    waiterInfo(bar);
                }
            }
            case "R" -> information(bar);
            case "E" -> eraseWaiter(bar, waiterCMD);
            default ->  {
                bar.getWaiters().get(Integer.parseInt(input)).introduce();
                bar.getWaiters().get(Integer.parseInt(input)).displayInformation();
                waiterInfo(bar);
            }
        }
    }
    
    /**
     * Erase a waiter from the user input which is the index of the existant one.
     * @param bar
     * @param cmd 
     */
    private static void eraseWaiter(Bar bar, String cmd) {
        System.out.println("Which client would you like to erase ?");
        String input = null;
        try { input = scanInput(cmd.substring(2)); }
        catch (UnsupportedInputException e) {
            System.err.println(e.getMessage());
            clientInfo(bar);
        }
        if (null != input) switch (input) {
            case "Q" -> {
                if(!quit()) {
                    System.out.println("You did not quit, choose a command !");
                    eraseWaiter(bar, cmd);
                }
            }
            case "R" -> waiterInfo(bar); 
            default -> {
                try { 
                    bar.removeWaiter(bar.getWaiters().get(Integer.parseInt(input))); 
                    waiterInfo(bar);
                }
                catch (MinCapacityReachedException e) { System.err.println(e.getMessage()); }
            }
        }
    }
    
    /**
     * Display the barmans and let the user choose which one displays its 
     * informations.
     * @param bar
     */
    private static void barmanInfo(Bar bar) {
        System.out.println(" -> Barman Info     [E] Erase [R] Return  [Q] Quit");
        String barmanCMD = "Q,E,R,";
        for (int i = 0; i<bar.getBarmans().size(); i++) {
            if (i == bar.getBarmans().size() - 1) barmanCMD += Integer.toString(i);
            else barmanCMD += Integer.toString(i)+",";
        }
        String input = null;
        try { input = scanInput(barmanCMD); }
        catch (UnsupportedInputException e) {
            System.err.println(e.getMessage());
            barmanInfo(bar);
        }
        if (null != input) switch (input) {
            case "Q" -> {
                if(!quit()) {
                    System.out.println("You did not quit, choose a command !");
                    barmanInfo(bar);
                }
            }
            case "R" -> information(bar);
            case "E" -> eraseBarman(bar, barmanCMD);
            default ->  {
                bar.getBarmans().get(Integer.parseInt(input)).introduce();
                bar.getBarmans().get(Integer.parseInt(input)).displayInformation();
                barmanInfo(bar);
            }
        }
    } 
    
    /**
     * Erase a barman from the user input which is the index of the existant one.
     * @param bar
     * @param cmd 
     */
    private static void eraseBarman(Bar bar, String cmd) {
        System.out.println("Which client would you like to erase ?");
        String input = null;
        try { input = scanInput(cmd.substring(2)); }
        catch (UnsupportedInputException e) {
            System.err.println(e.getMessage());
            clientInfo(bar);
        }
        if (null != input) switch (input) {
            case "Q" -> {
                if(!quit()) {
                    System.out.println("You did not quit, choose a command !");
                    eraseBarman(bar, cmd);
                }
            }
            case "R" -> barmanInfo(bar); 
            default -> {
                try { 
                    bar.removeBarman(bar.getBarmans().get(Integer.parseInt(input))); 
                    barmanInfo(bar);
                }
                catch (MinCapacityReachedException e) { System.err.println(e.getMessage()); }
            }
        }
    }
    
    /**
     * Menu that allows the user to create a new pub, erase one or switch to an 
     * existant pub.
     * @param bar currently viewed
     */
    private static void switchPub(Bar bar) {
        System.out.println("--------------------------------------------------");
        System.out.println(" -> Switch                    [R] Return [Q] Quit ");
        System.out.println("--------------------------------------------------");
        System.out.println("Select the bar you want to see !");
        String switchCmd = "N,E,R,Q,";
        for (int i = 0; i<userBars.size(); i++) { 
            if (i == userBars.size() - 1) switchCmd += Integer.toString(i);
            else switchCmd += Integer.toString(i)+",";
            System.out.println("["+i+"] "+userBars.get(i).getName());
        }
        System.out.println(" [N] New     [E] Erase");
        String input = null;
        try { input = scanInput(switchCmd); }
        catch (UnsupportedInputException e) {
            System.err.println(e.getMessage());
            switchPub(bar);
        }
        if (null != input) switch (input) {
            case "Q" -> {
                if(!quit()) {
                    System.out.println("You did not quit, choose a command !");
                    switchPub(bar);
                }
            }
            case "N" -> {
                addBar();
                switchPub(bar);
            }
            case "E" -> {
                if (userBars.get(Integer.parseInt(input)) == bar) {
                    System.out.println("Switch to another bar before erasing "
                        + userBars.get(Integer.parseInt(input)).getName());
                    switchPub(bar);
                }
                String erasedPub = userBars.get(Integer.parseInt(input)).getName();
                userBars.remove(userBars.get(Integer.parseInt(input)));
                System.out.println("You erased "+erasedPub);
            }
            case "R" -> menu(bar);
            default -> dialog(userBars.get(Integer.parseInt(input)));
        }
    }
    
    /**
     * Add a bar to the array list of the user's pubs.
     */
    public static void addBar() {
        System.out.println(" ");
        System.out.println("Please enter the name your new pub");
        Scanner in = new Scanner(System.in);
        System.out.print(">>> ");
        String input = in.nextLine();
        userBars.add(new Bar(input));
    }
    
    /**
     * Belote menu that allows to start a single belote game or a tournament if 
     * the number of people is high enough (for a tournament of 3 teams).
     * @param bar 
     */
    public static void belote(Bar bar) {
        System.out.println("--------------------------------------------------");
        System.out.println(" -> Menu -> Belote            [R] Return [Q] Quit ");
        if (getOrdered(bar).size() > 3*2) System.out.println(" [G] Single Game    [T] Tournament ");
        else System.out.println(" [G] Single Game ");
        if (bar.getTournamentCount() != 0) System.out.println(" [H] See tournaments history");
        System.out.println("--------------------------------------------------");
        String input = null;
        try { 
            if (getOrdered(bar).size() > 3*2) input = scanInput(CMD_B_MENU); 
            else if (bar.getTournamentCount() != 0) input = scanInput("Q,G,T,H,R");
            else input = scanInput("Q,G,R");
        }
        catch (UnsupportedInputException e) {
            System.err.println(e.getMessage());
            belote(bar);
        }
        if (null != input) switch (input) {
            case "Q" -> {
                if(!quit()) {
                    System.out.println("You did not quit, choose a command !");
                    belote(bar);
                }
            }
            case "G" -> {
                singleGame(bar);
                belote(bar);
            }
            case "T" -> {
                tournament(bar);
                belote(bar);
            }
            case "R" -> menu(bar);
            case "H" -> {
                // display the history
                belote(bar);
            }
        }
    }
    
    public static void singleGame(Bar bar) {
        ArrayList<Player> players = singleGameRegister(bar);
        // game initiation and run
        // getWinners
        // winnerAction(false)
        // loserAction(false, winners)
    }
    
    /**
     * Single belote game register menu.
     * @param bar
     * @return 
     */
    public static ArrayList<Player> singleGameRegister(Bar bar) {
        ArrayList<Player> players = new ArrayList<>();
        ArrayList<Human> all = getOrdered(bar);
        
        System.out.println("Are you playing ? (O/X)");
        String input = null;
        try { input = scanInput("X,O,R,Q"); }
        catch (UnsupportedInputException e) {
            System.err.println(e.getMessage());
            singleGameRegister(bar);
        }
        if (null != input) switch (input) {
            case "Q" -> {
                if(!quit()) {
                    System.out.println("You did not quit, choose a command !");
                    belote(bar);
                }
            }
            case "R" -> belote(bar);
            case "O" -> {
                Player user = new Player(null, false);
                players.add(user);
                
            }
        }
        
        // other registrations
        System.out.println("Make the registrations");
        System.out.println("[A] Auto    [M] Manual");
        try { input = scanInput("A,M,R,Q"); }
        catch (UnsupportedInputException e) {
            System.err.println(e.getMessage());
            singleGameRegister(bar);
        }
        if (null != input) switch (input) {
            case "Q" -> {
                if(!quit()) {
                    System.out.println("You did not quit, choose a command !");
                    belote(bar);
                }
            }
            case "R" -> belote(bar);
            case "M" -> {
                Player aiPlayer = null;
                while (players.size() < 4) {
                    aiPlayer = manualRegister(bar, all);
                    // prevent registering multiple times the same human
                    all.remove(aiPlayer.getHuman());
                    players.add(aiPlayer);
                    System.out.println(aiPlayer.getHuman().getName()+ " "
                            + aiPlayer.getHuman().getSurname() +"is registered for the game");
                }
            }
            case "A" -> {
                Player aiPlayer = null;
                while (players.size() < 4) {
                    aiPlayer = automaticRegister(bar, all);
                    // prevent registering multiple times the same human
                    all.remove(aiPlayer.getHuman());
                    players.add(aiPlayer);
                    System.out.println(aiPlayer.getHuman().getName()+ " "
                            + aiPlayer.getHuman().getSurname() +"is registered for the game");
                }
            }
        }
        return players; // last return to validate the method
    }
    
    public static void tournament(Bar bar) {
        Tournament tournament = new Tournament(bar);
        ArrayList<Team> teams = tournamentRegister(bar, tournament);
        // game initiation and run
        // getWinners
        // winnerAction(false)
        // loserAction(false, winners)
    }
    
    /**
     * Belote tournament registration menu.
     * @param bar 
     * @param tournament object
     * @return the teams that were chosen
     */
    public static ArrayList<Team> tournamentRegister(Bar bar, Tournament tournament) {
        Scanner in = new Scanner(System.in);
        ArrayList<Team> teams = new ArrayList<>();
        ArrayList<Human> all = getOrdered(bar);
        
        System.out.println("Are you playing ? (O/X)");
        String input = null;
        try { input = scanInput("X,O,R,Q"); }
        catch (UnsupportedInputException e) {
            System.err.println(e.getMessage());
            singleGameRegister(bar);
        }
        
        if (null != input) switch (input) {
            case "Q" -> {
                if(!quit()) {
                    System.out.println("You did not quit, choose a command !");
                    belote(bar);
                }
            }
            case "R" -> belote(bar);
            case "O" -> {
                // create user's team
                Player[] userTeam = new Player[2];
                Player user = new Player(null, false);
                userTeam[0] = user;
                System.out.println("Choose your team member :");
                userTeam[1] = manualRegister(bar, all);
                all.remove(userTeam[1].getHuman());
                System.out.println("What is your team name ?");
                input = in.nextLine();
                teams.add(new Team(userTeam, input));
                userTeam[1].getHuman().setWalletBalance(userTeam[1].getHuman().getWalletBalance() - Tournament.REGISTER_PRICE);
                tournament.setRegisterPoll(tournament.getRegisterPoll() + Tournament.REGISTER_PRICE);
            }
        }
        
        // other registrations
        System.out.println("Make the registrations");
        System.out.println("[A] Auto    [M] Manual");
        Player[] aiPlayers = new Player[2];
        try { input = scanInput("A,M,R,Q"); }
        catch (UnsupportedInputException e) {
            System.err.println(e.getMessage());
            singleGameRegister(bar);
        }
        if (null != input) switch (input) {
            case "Q" -> {
                if(!quit()) {
                    System.out.println("You did not quit, choose a command !");
                    belote(bar);
                }
            }
            case "R" -> belote(bar);
            case "M" -> {
                while (teams.size() < 3) {
                    aiPlayers[0] = manualRegister(bar, all);
                    all.remove(aiPlayers[0].getHuman()); // prevent registering multiple times the same human
                    aiPlayers[1] = manualRegister(bar, all);
                    all.remove(aiPlayers[1].getHuman());
                    
                    for (Player aiPlayer : aiPlayers) {
                        // pay the register fee
                        aiPlayer.getHuman().setWalletBalance(aiPlayer.getHuman().getWalletBalance() - Tournament.REGISTER_PRICE);
                        tournament.setRegisterPoll(tournament.getRegisterPoll() + Tournament.REGISTER_PRICE);
                        System.out.println(aiPlayer.getHuman().getName()+ " "
                                + aiPlayer.getHuman().getSurname() +"is registered for the tournament");
                    }
                    teams.add(new Team(aiPlayers, Team.NAMES.get((int) (Math.random()*Team.NAMES.size()))));
                }
            }
            case "A" -> {
                while (teams.size() < 3) {
                    aiPlayers[0] = automaticRegister(bar, all);
                    all.remove(aiPlayers[0].getHuman()); // prevent registering multiple times the same human
                    aiPlayers[1] = automaticRegister(bar, all);
                    all.remove(aiPlayers[1].getHuman());
                    
                    for (Player aiPlayer : aiPlayers) {
                        // pay the register fee
                        aiPlayer.getHuman().setWalletBalance(aiPlayer.getHuman().getWalletBalance() - Tournament.REGISTER_PRICE);
                        tournament.setRegisterPoll(tournament.getRegisterPoll() + Tournament.REGISTER_PRICE);
                        System.out.println(aiPlayer.getHuman().getName()+ " "
                                + aiPlayer.getHuman().getSurname() +"is registered for the tournament");
                    }
                    teams.add(new Team(aiPlayers, Team.NAMES.get((int) (Math.random()*Team.NAMES.size()))));
                }
            }
        }
        return teams; // last return to validate the method
    }
    
    /**
     * Manual Registration of a Human.
     * The user choose a human between all existant one that are linked to the bar.
     * @param bar
     * @param all human ArrayList to choose from
     * @return the player chosen
     */
    public static Player manualRegister(Bar bar, ArrayList<Human> all) {
        System.out.println("Choose the human you want to register :");
        String registerCmd = "Q,R,";
        for (int i = 0; i<all.size(); i++) {
            if (i == all.size() - 1) registerCmd += Integer.toString(i);
            else registerCmd += Integer.toString(i) + ",";
            System.out.println("["+i+"] "+all.get(i).getName()+" "+all.get(i).getSurname());
        }
        String input = null;
        try { input = scanInput(registerCmd); }
        catch (UnsupportedInputException e) {
            System.err.println(e.getMessage());
            manualRegister(bar, all);
        }
        if (null != input) switch (input) {
            case "Q" -> {
                if(!quit()) {
                    System.out.println("You did not quit, choose a command !");
                    manualRegister(bar, all);
                }
            }
            case "R" -> belote(bar);
            default -> { return new Player(all.get(Integer.parseInt(input)), true); }
        }
        return null;
    }
    
    /**
     * Automatic Registration of a Human with a random choice.
     * @param bar
     * @param all human ArrayList to choose from
     * @return the player chosen
     */
    public static Player automaticRegister(Bar bar, ArrayList<Human> all) {
        return new Player(all.get((int)(Math.random()*all.size())), true);
    }
    
    /**
     * Get a shuffled list of all humans in the bar.
     * @param bar
     * @return an ArrayList of Human
     */
    private static ArrayList<Human> getShuffled(Bar bar) {
        ArrayList<Human> all = getOrdered(bar);
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
     * Get an ordered list of all humans in the bar
     * @param bar
     * @return an ArrayList of Human
     */
    private static ArrayList<Human> getOrdered(Bar bar) {
        ArrayList<Client> clients = bar.getClients();
        ArrayList<Employee> employees = bar.getEmployees();
        
        // get all humans of the bar in one array list
        ArrayList<Human> all = new ArrayList<>();
        for (int i = 0; i<clients.size(); i++) all.add((Human) clients.get(i));
        for (int i = 0; i<employees.size(); i++) all.add((Human) employees.get(i));
        all.add((Human) bar.getBoss());
        
        return all;
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
