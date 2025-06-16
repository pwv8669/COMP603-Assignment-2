/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blackjackprogram;

/**
 *
 * @author blake
 */
public class GameManager {

    private BetManager betManager;
    private MoneyStorage moneyStorage;
    public GameState gameState;
    public Player player;

    public GameManager() {
        this.betManager = new BetManager();
        this.moneyStorage = betManager.storage;
        
        // If we leaded with $0, immediatley give player 100.
        if(betManager.getPlayerMoney()==0&&betManager.getCurrentBet()==0){
            moneyStorage.saveMoney(100);
            this.betManager = new BetManager();
        }
    }

    // Returns the current betmanager.
    public BetManager getBetManager() {
        return this.betManager;
    }

    // Creates a new game.
    public String newGame(int betAmount) {
        StringBuilder output = new StringBuilder();

        // If the player fails to place the bet, it will be due to insufficient funds.
        if (!betManager.placeBet(betAmount)) {
            return "Invalid bet. You only have $"+betManager.getPlayerMoney()+".\n";
        }

        // Deal out starting hands.
        Deck deck = new Deck();
        Dealer dealer = new Dealer(deck);
        player = new Player(deck);

        // Present the visible cards to the player.
        output.append("Bet placed: $").append(betAmount).append("\n");
        output.append("Player's hand: ").append(player).append(" (Value: ").append(player.value()).append(")\n");
        output.append("Dealer shows: ").append(dealer.cards[1]).append("\n");

        // If there is a natural blackjack, the player will win immediatley so we muct check now.
        if (player.value() == 21 && player.cards[2] == null) {
            output.append("Player has a natural blackjack!\n");
            int winnings = (int) (betManager.getCurrentBet() * betManager.BLACKJACK_PAYOUT);
            // Betmanager uses boolean as an argument to represent whether its natural blackjack or any other win.
            betManager.win(true); 
            output.append("You won with a blackjack! Payout: $" + winnings + "\n");
            output.append("Money: $" + betManager.getPlayerMoney() + "\n");

            // Signal that the round has ended.
            gameState = null; 
            output.append(checkAndResetMoney());
            return output.toString();
        }

        // Not an immediate blackjack so we muct keep track of the state of the game.
        gameState = new GameState(deck, dealer, player);
        return output.toString();
    }

    // Player draws a card.
    public String hit() {
        if (gameState == null) {
            return "No game in progress.\n";
        }

        // If the player has already busted, then they cant draw.
        Player player = gameState.player;
        if (player.isBust()) {
            return "You are already busted. Round over.\n";
        }

        // Else, draw a card.
        player.hit();
        StringBuilder sb = new StringBuilder();
        sb.append("Player hits!\n");
        sb.append("Player's hand: ").append(player).append("\n");
        sb.append("Hand value: ").append(player.value()).append("\n");

        // If the newly drawn card has busted the player, they lose the bet and the game ends.
        if (player.isBust()) {
            int lostAmount = betManager.getCurrentBet(); // Quickly grab the bet value before loose() resets it.
            betManager.lose();
            sb.append("Player has busted!\n");
            sb.append("You lost your bet of $").append(lostAmount).append(".\n");
            gameState = null;
            // Make sure that the player isnt on $0. If they are give some money so they can keep playing.
            sb.append(checkAndResetMoney());
        }
        return sb.toString();
    }

    // Player stops drawing cards and dealer draws until they have at least 17.
    public String stand() {
        if (gameState == null) {
            return "No game in progress.\n";
        }

        Dealer dealer = gameState.dealer;
        Player player = gameState.player;
        // Signal the dealer to start drawing until 17.
        player.stand(dealer);
        StringBuilder sb = new StringBuilder();
        sb.append("Dealer's hand: ").append(dealer).append("\n");
        sb.append("Dealer's value: ").append(dealer.value()).append("\n");
        // Get values to compare outcome.
        boolean playerBust = player.isBust();
        boolean dealerBust = dealer.isBust();
        int playerValue = player.value();
        int dealerValue = dealer.value();
        // Compare results.
        if (playerBust || (!dealerBust && dealerValue > playerValue)) {
            sb.append("Player has lost.\n");
            betManager.lose();
        } else if (dealerValue == playerValue) {
            sb.append("It's a tie!\n");
            betManager.tie();
        } else {
            sb.append("Player wins!\n");
            boolean isBlackjack = (player.cards[2] == null && player.value() == 21);
            betManager.win(isBlackjack);
        }

        // Show updated money.
        sb.append("Money: $").append(betManager.getPlayerMoney()).append("\n");
        gameState = null;
        // Update money to 100 if player ran out.
        sb.append(checkAndResetMoney());
        return sb.toString();
    }

    public String doubleDown() {
        // They player must have enough money to double down.
        if (!betManager.canDoubleDown()) {
            return "You don't have enough funds to double down.\n";
        }

        Player player = gameState.player;
        Deck deck = gameState.deck;
        // Double the bet.
        betManager.doubleDown();
        // Deal one card.
        player.hit();
        StringBuilder sb = new StringBuilder();
        sb.append("You chose to double down.\n");
        sb.append("New bet: $").append(betManager.getCurrentBet()).append("\n");
        sb.append("Player's hand: ").append(player).append("\n");
        sb.append("Hand value: ").append(player.value()).append("\n");

        // If the player is busted by the card, they lose.
        if (player.isBust()) {
            sb.append("Player has busted!\n");
            int lostAmount = betManager.getCurrentBet();
            betManager.lose();
            sb.append("You lost your bet of $" + lostAmount + ".\n");
            gameState = null;
            // Give player $100 if they ran out from losing.
            sb.append(checkAndResetMoney());
        // Otherwise, they will stand.
        } else {
            sb.append("\nNow standing...\n\n");
            sb.append(stand());
            sb.append(checkAndResetMoney());
        }
        return sb.toString();
    }

    // Checks to see if the player has $0 and if so gives them $100 as to not softlock them.
    public String checkAndResetMoney() {
        if (gameState==null&&betManager.getPlayerMoney()==0&&betManager.getCurrentBet()==0) {
            moneyStorage.saveMoney(100);
            betManager = new BetManager();
            return "Added $100 funds as you ran out.\n";
        }
        return "";
    }
}
