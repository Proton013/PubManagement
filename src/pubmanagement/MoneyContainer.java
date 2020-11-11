/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pubmanagement;

/**
 *
 * @author eugenie_dalmas
 */
public class MoneyContainer {
    /**
     * Amounts of each note and coins contained.
     * (money unit is €)
     */
    private int note100;    // notes quantities
    private int note50;
    private int note20;
    private int note10;
    private int note5;
    
    private int coin2;      // coins quantities
    private int coin1;
    private int coin05;
    private int coin02;
    private int coin01;
    
    private int coin005;    // cents quantities
    private int coin002;
    private int coin001;
    
    /*
    ideas for methods:
        asEnough(double for) -> true if for>= getBalance()
        setters (individual)
        getters (individual)
    */
    
    /**
     * Get the total balance of the money container.
     * @return a double
     */
    public double getBalance() {
        return (this.note100*100 + this.note50*50 + this.note20*20 + this.note10*10
                + this.note5*5 + this.coin2*2 + this.coin1*1 + this.coin05*0.5
                + this.coin02*0.2 + this.coin01*0.1 + this.coin005*0.05
                + this.coin002*0.02 + this.coin001*0.01);
    }
    
    /**
     * Set multiple note amount for ergonomic use in code.
     * Amounts as integers:
     * @param note100
     * @param note50
     * @param note20
     * @param note10
     * @param note5 
     */
    public void setMultipleNotes(int note100, int note50, int note20, int note10, int note5) {
        this.note100 = note100;
        this.note50 = note50;
        this.note20 = note20;
        this.note10 = note10;
        this.note5 = note5;
    }
    
    /**
     * Set multiple coins amount for ergonomic use in code.
     * Amounts as integers:
     * @param coin2
     * @param coin1
     * @param coin05
     * @param coin02
     * @param coin01
     * @param coin005
     * @param coin002
     * @param coin001 
     */
    public void setMultipleCoins(int coin2, int coin1, int coin05, int coin02, int coin01,
            int coin005, int coin002, int coin001) {
        this.coin2 = coin2;
        this.coin1 = coin1;
        this.coin05 = coin05;
        this.coin02 = coin02;
        this.coin01 = coin01;
        this.coin005 = coin005;
        this.coin002 = coin002;
        this.coin001 = coin001;
    }
    
    // ----- Getters -----
    
    // ----- Setters -----
    
}
