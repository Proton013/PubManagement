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
    public static final String CMD_M_MENU = "A,I,T";
    public static final String CMD_A_MENU = "C,W,B";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Bar OldGoat = new Bar("Old Goat");
        displayStarting();
        try {
            dialog(OldGoat);
        } catch (UnsupportedInputException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public static void dialog(Bar bar) throws UnsupportedInputException {
        ArrayList<String> cmd = new ArrayList<>(Arrays.asList(CMD_MAIN_MENU.split(",")));
        Scanner in = new Scanner(System.in);
        String s = in.nextLine().toUpperCase();
        if (!cmd.contains(s)) 
            throw new UnsupportedInputException("Input is not one of the available option.");
        
        dialogMenu();
        if ("Q".equals(s)) System.exit(0);
        else if ("N".equals(s)) {
            runShuffleLoop(bar);
            dialog(bar);
        }
    }
    
    public static void menu(Bar bar) throws UnsupportedInputException {
        ArrayList<String> cmd = new ArrayList<>(Arrays.asList(CMD_M_MENU.split(",")));
        Scanner in = new Scanner(System.in);
        String s = in.nextLine().toUpperCase();
        if (!cmd.contains(s)) 
            throw new UnsupportedInputException("Input is not one of the available option.");
    }
    
    /**
     * Run a loop of a shuffled list of the present humans in the bar.
     * @param bar
     * @param clients that are present
     */
    private static void runShuffleLoop(Bar bar) {
        ArrayList<Client> clients = bar.getPresentClients();
        ArrayList<Human> shuffled = getShuffled(bar);
        for (int i = 0; i<shuffled.size(); i++) {
            shuffled.get(i).action(clients);
        }
    }
    
    /**
     * Get a random human between all that are present in the bar.
     * @param bar 
     * @return a random Human
     */
    private static Human getRandomHumanPresent(Bar bar) {
        ArrayList<Client> clients = bar.getPresentClients();
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
        ArrayList<Client> clients = bar.getPresentClients();
        ArrayList<Employee> employees = bar.getEmployees();
        
        // get all present humans in the bar in one array list
        ArrayList<Human> allPresent = new ArrayList<>();
        for (int i = 0; i<clients.size(); i++) allPresent.add((Human) clients.get(i));
        for (int i = 0; i<employees.size(); i++) allPresent.add((Human) employees.get(i));
        allPresent.add((Human) bar.getBoss());
        
        // compute a shuffle order of indexes
        Integer[] intArray = new Integer[allPresent.size()];
        for (int i = 0; i<intArray.length; i++) intArray[i] = i;
        List<Integer> list = Arrays.asList(intArray);
        Collections.shuffle(list);
        Integer[] order = list.toArray(intArray);
        ArrayList<Human> shuffled = new ArrayList<>();
        for (int i = 0; i<order.length; i++) shuffled.add(allPresent.get(order[i]));
        
        return shuffled;
    }
    
    /**
     * Displays the bar menu with keys that the user may use.
     */
    private static void dialogMenu() {
        System.out.println(" [M] Menu    [N] Next                    [Q] Quit ");
        System.out.println("--------------------------------------------------");
        System.out.println();
    }
    
    private static void menuMenu() {
        System.out.println(" _Menu                                   [Q] Quit ");
        System.out.println(" [A] Add    [I] Get Information");
        System.out.println("--------------------------------------------------");
        System.out.println();
    }
    
    private static void menuAdd() {
        System.out.println(" _Add                                    [Q] Quit ");
        System.out.println(" [C] Client [W] Waiter  [B] Barman ");
        System.out.println("--------------------------------------------------");
        System.out.println();
    }
    /**
     * Displays the welcoming message.
     */
    private static void displayStarting() {
        dialogMenu();
        System.out.println("Welcome to your pub !\n");
        System.out.println("Use the letters from the bar menu to go through\n" 
                         + "the dialogs (N) or display the menu. This menu\n"
                         + "[M] will allow to access other functionalities of\n"
                         + "this program.\n");
    }
}
