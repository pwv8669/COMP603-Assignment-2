/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blackjackprogram;

import java.util.Scanner;

/**
 *
 * @author blake
 */
public class GameManager {

    BetManager betManager = new BetManager();
    MoneyStorage moneyStorage = betManager.storage;

    public void newGame() {
        // Create a scanner for CUI inputs.
        Scanner scanner = new Scanner(System.in);

        // ------ Place Bet ------ //
        System.out.println("You have $" + betManager.getPlayerMoney());
        System.out.print("Place your bet: ");
        int betAmount = 0;
        boolean validBet = false;
        while (!validBet) { // Force user to input an int which is at least 1.
            try {
                betAmount = Integer.parseInt(scanner.nextLine());
                validBet = betManager.placeBet(betAmount);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number, you must input a whole number.");
            }
        }

        // ------ Setup Dealer and Player's Hands ------ //
        Dealer dealer = new Dealer(new Card[12]);
        Player player = new Player(dealer.cards);
        dealer.showFirstCard();
        System.out.println("Player's hand: "+player+" ("+player.value()+")");

        String input = "";

        // ------ Natural Blackjack: Stand ------ //
        if (player.value() == 21) {
            System.out.println("\nPlayer has a natural blackjack!");
            player.stand(dealer);
        } // ------ Player Inputs Their Choice ------ //
        else {
            System.out.print("\nWould you like to \"Hit\" or \"Stand\" or \"Double down\"?: ");
            // Break out of while loop if player busts, or choses to either stand or double down.
            while (!player.isBust() && !input.equals("stand")) {
                input = scanner.nextLine().toLowerCase().trim();
                switch (input) {
                    case "hit":
                        // Add a card to player's deck and check if the have busted.
                        player.hit(dealer.cards);
                        System.out.println("Player's hand: " + player);
                        if (player.isBust()) {
                            System.out.println("Player has busted with " + player.value() + ".");
                        } else {
                            System.out.println("Player's hand's value: " + player.value());
                        }
                        break;
                    case "double down":
                        // Check if the player has enough money in order to double their bet.
                        if (betManager.canDoubleDown()) {
                            betManager.doubleDown();
                            // Add a card to the players deck and stand if they haven't busted.
                            player.hit(dealer.cards);
                            System.out.println("Player's hand: " + player);
                            if (player.isBust()) {
                                System.out.println("Player has busted with " + player.value() + ".");
                            } else {
                                System.out.println("Player's hand's value: " + player.value());
                                // Have the dealer hit until they reach 17.
                                player.stand(dealer);
                            }
                            input="stand";
                            break;
                        } else {
                            System.out.println("You cannot double down as you do not have enough funds.");
                            // Because the loop will end if input=="double down" we need to change input, they cant double down.
                            input = "(an input that isn't double down)";
                            break;
                        }

                    case "stand":
                        // Have the dealer hit until they reach 17.
                        player.stand(dealer);
                        break;
                    default:
                        // If the player inputs anything else give a message saying their input is invalid.
                        System.out.println("Invalid input.");
                }
            }
        }

        // ------ Game Outcomes ------ //
        boolean playerBust = player.isBust();
        boolean dealerBust = dealer.isBust();
        int playerValue = player.value();
        int dealerValue = dealer.value();
        
        // Player loses
        if (playerBust||(!dealerBust&&dealerValue>playerValue)) {
            System.out.println("Player has lost.");
            betManager.lose();
        } // Player ties
        else if (dealerValue==playerValue) {
            System.out.println("Dealer and player have tied.");
            // Refund Bet.
            betManager.playerMoney += betManager.currentBet;
            betManager.currentBet = 0;
            System.out.println("Bet Returned.\n");
            betManager.tie();
        } // Player wins
        else {
            System.out.println("Player wins!\n");
            //Check for a natural blackjack (21 with only two cards)
            boolean isBlackJack = (player.cards[2]==null && player.value()==21);
            betManager.win(isBlackJack);
        }

        // ------ Play Again Prompt ------ //
        System.out.println("\nWould you like to \"Leave\" or \"Play\" again?");
        input="";
        // Keep scanning until player makes a valid decision.
        while (!input.equals("leave") && !input.equals("play")) {
            input = scanner.nextLine().toLowerCase().trim();
            switch (input) {
                case "leave":
                    // Program will terminate as a new game will not be started.
                    if (betManager.getPlayerMoney() <= 0) {
                        moneyStorage.saveMoney(100);
                    }
                    break;
                case "play":
                    // Starts a new round.
                    if (betManager.getPlayerMoney() <= 0) {
                        System.out.println("\nYou're out of money, so you withdrew $100 from your kid's college savings.");
                        moneyStorage.saveMoney(100);
                    }
                    newGame();
                    break;
                default:
                    System.out.println("Invalid input.");
            }
        }
    }
}
