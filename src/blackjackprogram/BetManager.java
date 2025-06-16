/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blackjackprogram;


import java.io.*;




/**
 *
 * @author aidan
 */
public class BetManager {
    
    protected int playerMoney;
    protected int currentBet = 0;
    
    public MoneyStorage storage;
    
    public static final double BLACKJACK_PAYOUT = 1.5;
    public static final int NON_BLACKJACK_PAYOUT = 2;
    
    public BetManager(){
        storage = new MoneyStorage();
        playerMoney = storage.loadMoney();
    }
    
    //Allows the user to place bets
    public boolean placeBet(int amount){
        if(amount > playerMoney){
            System.out.println("Invalid bet, you don't have enough funds.");
            return false;
        }
        else if(amount<=0){
            System.out.println("Bets must be more than 0.");
            return false;
        }
        currentBet = amount;
        playerMoney -= amount;
        return true;
    }
    
    // Increase money.
    public void win(boolean isBlackJack){
        double payout; 
        if(isBlackJack){
            // If it is a natural blackjack, the player is only rewarded 3:2.
            payout = currentBet * BLACKJACK_PAYOUT;
        }
        else{
            // Otherwise, player is rewarded the full 2:1.
            payout = currentBet * NON_BLACKJACK_PAYOUT;
        }
        playerMoney += payout;
        System.out.println("Remaining Funds: $" + playerMoney+".");
        currentBet = 0;
        storage.saveMoney(playerMoney);
    }
    
    // Set bet to 0 and print player's remaining money.
    public void lose(){
        currentBet = 0;
        System.out.println("Remaining Funds: $" + playerMoney+".");
        storage.saveMoney(playerMoney);
    }
    
    // Double the players bet.
    public boolean doubleDown(){
        if(playerMoney >= currentBet){
            playerMoney -= currentBet;
            currentBet *= 2;
            System.out.println("Bet increased to "+currentBet+".");
            return true;
        }
        else{
            System.out.println("Not enough money to double down, standing.");
            return false;
        }
    }
    
    // Refund bet.
    public void tie(){
        playerMoney += currentBet;
        System.out.println("Hand tied. Returned Bet");
        currentBet = 0;
        storage.saveMoney(playerMoney);
    }
    
    // Get method for player's money.
    public int getPlayerMoney(){
        return playerMoney;
    }
     
    // Get method for current bet.
    public int getCurrentBet(){
        return currentBet;
    }
    
    // Checks if the player has money to spend.
    public boolean canPlaceBet(){
        return playerMoney > 0;
    }
    
    // Checks if the player has enough money to double their bet.
    public boolean canDoubleDown(){
        return playerMoney >= currentBet;
    }
}
