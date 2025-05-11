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
public class Bets {
    
    protected int playerMoney;
    protected int currentBet = 0;
    
    private static final String SAVE_FILE = "player_money.txt";
    
    // Use BufferedWriter to save the players score (Money)
    public void saveMoney(){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(SAVE_FILE))){
            writer.write(Integer.toString(playerMoney));
        }catch(IOException e){
            System.out.println("Error saving money");
            
        }
    }
    // Use FileReader to find the player_money.txt file and read it for the current score (Money)
    public void loadMoney(){
        File file = new File(SAVE_FILE);
            if(file.exists()){
                try(BufferedReader read = new BufferedReader(new FileReader(SAVE_FILE))){
                    String line = read.readLine();
                    playerMoney = Integer.parseInt(line);
                }
                catch(IOException | NumberFormatException e){
                    System.out.println("Error loading money");
                    playerMoney = 100;
                }
            }
            else{
            playerMoney = 100;
        }
        
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
            payout = currentBet * 1.5;
        }
        else{
            // Otherwise, player is rewarded the full 2:1.
            payout = currentBet * 2;
        }
        playerMoney += payout;
        System.out.println("Remaining Funds: " + playerMoney+".");
        currentBet = 0;
    }
    
    // Set bet to 0 and print player's remaining money.
    public void lose(){
        currentBet = 0;
        System.out.println("Remaining Funds: " + playerMoney+".");
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
