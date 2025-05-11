/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
/**
 *
 * @author aidan
 */
package blackjackprogram;

import java.util.Scanner;

/**
 *
 * @author blake
 */
public class BlackjackProgram {
    /**
     
     * @param args the command line arguments
     */
    
    static Bets bet = new Bets();
    
    public static void newGame(){
        // Create a scanner for CUI inputs.
        Scanner scanner = new Scanner(System.in);
        
        
        // ------ Place Bet ------ //
        System.out.println("You have $" + bet.getPlayerMoney());
        System.out.print("Place your bet: ");
        int betAmount = 0;
        boolean validBet = false;
        while(!validBet){ // Force user to input an int which is at least 1.
            try{
                betAmount = Integer.parseInt(scanner.nextLine()); 
                validBet = bet.placeBet(betAmount);
            }catch(NumberFormatException e){
                System.out.println("Invalid number, you must input a whole number.");
            }
        }
        
        
        // ------ Setup Dealer and Player's Hands ------ //
        Hand playerHand = new Hand(new Card[12]);
        playerHand.hit(new Card[12]);
        Hand dealerHand = new Hand(playerHand.cards);
        System.out.println("Dealer's hand: "+dealerHand);
        System.out.println("Player's hand: "+playerHand);
        
        
        String input="";
        // ------ Natural Blackjack: Stand ------ //
        if(playerHand.value()==21){
            System.out.println("\nPlayer has a natural blackjack!");
            playerHand.stand(dealerHand);
        }
        // ------ Player Inputs Their Choice ------ //
        else{
            System.out.print("\nWould you like to \"Hit\" or \"Stand\" or \"Double down\"?: ");
            // Break out of while loop if player busts, or choses to either stand or double down.
            while(playerHand.busted!=true&&!input.equals("stand")&&!input.equals("double down")){
                input = scanner.nextLine();
                switch(input.toLowerCase().trim()){
                    case "hit":
                        // Add a card to player's deck and check if the have busted.
                        playerHand.hit(dealerHand.cards);
                        System.out.println("Player's hand: "+playerHand);
                        if(playerHand.busted){
                            System.out.println("Player has busted with "+playerHand.value()+".");
                        }
                        else{
                            System.out.println("Player's hand's value: "+playerHand.value());
                        }
                        break;
                    case "double down":
                        // Check if the player has enough money in order to double their bet.
                        if(bet.canDoubleDown()){
                            bet.doubleDown();
                        }
                        else{
                            System.out.println("You cannot double down as you do not have enough funds.");
                            // Because the loop will end if input=="double down" we need to change input, they cant double down.
                            input = "(an input that isn't double down)";
                            break;
                        }
                        // Add a card to the players deck and stand if they haven't busted.
                        playerHand.hit(dealerHand.cards);
                        System.out.println("Player's hand: "+playerHand);
                        if(playerHand.busted){
                            System.out.println("Player has busted with "+playerHand.value()+".");
                        }
                        else{
                            System.out.println("Player's hand's value: "+playerHand.value());
                            // Have the dealer hit until they reach 17.
                            playerHand.stand(dealerHand);
                        }
                        break;
                    case "stand":
                        // Have the dealer hit until they reach 17.
                        playerHand.stand(dealerHand);
                        break;
                    default:
                        // If the player inputs anything else give a message saying their input is invalid.
                        System.out.println("Invalid input.");
                }
            }
        }
        
        // ------ Game Outcomes ------ //
        // Player loses
        if(playerHand.value()<dealerHand.value()&&dealerHand.value()<=21||playerHand.busted){
            System.out.println("Player has lost.");
            bet.lose();
        }
        // Player ties
        else if(playerHand.value()==dealerHand.value()){
            System.out.println("Dealer and player have tied.");
            // Refund Bet.
            bet.playerMoney += bet.currentBet;
            bet.currentBet = 0;
            System.out.println("Bet Returned.\n");
        }
        // Player wins
        else if(playerHand.value()>dealerHand.value()||dealerHand.busted){
            System.out.println("Player wins!\n");
            //Check for a natural blackjack (21 with only two cards)
            boolean isBlackJack = (playerHand.cards[2] == null && playerHand.value() == 21);
            bet.win(isBlackJack);
        }
        bet.saveMoney(); // No matter the outcome, save players money to file.
        
        // ------ Play Again Prompt ------ //
        System.out.println("\nWould you like to \"Leave\" or \"Play\" again?");
        // Keep scanning until player makes a valid decision.
        while(!input.equals("leave")&&!input.equals("play")){
            input = scanner.nextLine();
            switch(input.toLowerCase().trim()){
                case "leave":
                    // Program will terminate as a new game will not be started.
                    if(bet.getPlayerMoney() <= 0){
                        bet.playerMoney = 100;
                    }
                    bet.saveMoney();
                    break;
                case "play":
                    // Starts a new round.
                    if(bet.getPlayerMoney() <= 0){
                        System.out.println("\nYou're out of money, so you withdrew $100 from your kid's college savings.");
                        bet.playerMoney = 100;
                    }
                    bet.saveMoney();
                    newGame();
                    break;
                default:
                    System.out.println("Invalid input.");
            }
        }
    }
    
    public static void main(String[] args) {
        // Begin new game loop.
        bet.loadMoney();
        newGame();
        
    }
    
}
