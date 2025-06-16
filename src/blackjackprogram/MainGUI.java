/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blackjackprogram;

import java.awt.BorderLayout;
import javax.swing.*;

/**
 *
 * @author blake
 */
public class MainGUI extends JFrame {

    private GameManager gameManager;
    private BetManager betManager;

    private JPanel menuPanel;
    private JPanel gamePanel;
    private JLabel moneyLabel;
    private JTextField betField;
    private JTextArea gameOutput;
    private JButton placeBetButton;
    private JButton startGameButton;
    private JButton hitButton;
    private JButton standButton;
    private JButton doubleDownButton;

    public MainGUI() {
        // Initialise the game.
        gameManager = new GameManager();
        betManager = gameManager.getBetManager();

        // Set some details for the panel. 
        setTitle("Blackjack");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create field for game updates.
        gameOutput = new JTextArea(5,40);
        gameOutput.setEditable(false);
        JScrollPane outputScroll = new JScrollPane(gameOutput);

        // Main menu panel to be shown before gameplay begins.
        menuPanel = new JPanel();
        moneyLabel = new JLabel("Money: $" + betManager.getPlayerMoney());
        betField = new JTextField(5);
        placeBetButton = new JButton("Place Bet");
        startGameButton = new JButton("Start Game");
        startGameButton.setEnabled(false);

        menuPanel.add(moneyLabel);
        menuPanel.add(new JLabel("Bet:"));
        menuPanel.add(betField);
        menuPanel.add(placeBetButton);
        menuPanel.add(startGameButton);

        // Group game output field and the main menu buttons.
        JPanel topContainer = new JPanel();
        topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
        topContainer.add(outputScroll);
        topContainer.add(menuPanel);
        add(topContainer, BorderLayout.NORTH);

        // Game panel, gets swapped to from main menu and has player decisions.
        gamePanel = new JPanel();
        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");
        doubleDownButton = new JButton("Double Down");
        gamePanel.add(hitButton);
        gamePanel.add(standButton);
        gamePanel.add(doubleDownButton);
        gamePanel.setVisible(false);
        add(gamePanel, BorderLayout.CENTER);

        // Button listeners:
        // Checks if bets are valid and enables the start game button.
        placeBetButton.addActionListener(e -> {
            try {
                int amount = Integer.parseInt(betField.getText());  // validate only
                if (amount < 1) {
                    gameOutput.append("Minimum bet is $1\n");
                    return;
                }
                if (amount > betManager.getPlayerMoney()) {
                    gameOutput.append("You only have $" + betManager.getPlayerMoney() + ".\n");
                    return;
                }
                gameOutput.setText("");
                placeBetButton.setEnabled(false);
                startGameButton.setEnabled(true);
            } catch (NumberFormatException ex) {
                gameOutput.append("Invalid bet amount. Must be a number.\n");
            }
        });

        // Starts the game.
        startGameButton.addActionListener(e -> {
            int betAmount = Integer.parseInt(betField.getText());
            String result = gameManager.newGame(betAmount); // Replace with GUI-friendly version
            betManager = gameManager.getBetManager();
            gameOutput.append(result);
            moneyLabel.setText("Money: $" + betManager.getPlayerMoney());
            // Immediatley go back to menu if natural blackjack.
            if (result.contains("No game in progress.") || result.contains("Player has a natural blackjack")) {
                swapToMenuPanel();
            } else {
                swapToGamePanel();
            }
        });

        // Double down button.
        doubleDownButton.addActionListener(e -> {
            String result = gameManager.doubleDown();
            betManager = gameManager.getBetManager();
            gameOutput.append(result);
            moneyLabel.setText("Money: $"+betManager.getPlayerMoney());
            if (gameManager.gameState == null) {
                swapToMenuPanel();
            }
        });

        // Handles hitting, and checks if the player has busted.
        hitButton.addActionListener(e -> {
            String result = gameManager.hit();
            betManager = gameManager.getBetManager();
            gameOutput.append(result);
            moneyLabel.setText("Money: $"+betManager.getPlayerMoney());
            if (gameManager.gameState==null) {
                swapToMenuPanel();
            }
        });

        // Prompts the GameManager to have dealer draw.
        standButton.addActionListener(e -> {
            String result = gameManager.stand();
            betManager = gameManager.getBetManager();
            gameOutput.append(result);
            moneyLabel.setText("Money: $"+betManager.getPlayerMoney());
            swapToMenuPanel();
        });

        setVisible(true);
    }

    // Swaps the GUI to have the player's decision buttons.
    private void swapToGamePanel() {
        menuPanel.setVisible(false);
        gamePanel.setVisible(true);
        hitButton.setEnabled(true);
        standButton.setEnabled(true);
        doubleDownButton.setEnabled(true);
    }

    // Swaps the GUI to the main menu.
    private void swapToMenuPanel() {
        gamePanel.setVisible(false);
        menuPanel.setVisible(true);
        placeBetButton.setEnabled(true);
        startGameButton.setEnabled(false);
        betField.setText("");
        moneyLabel.setText("Money: $" + betManager.getPlayerMoney());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI());
    }
}
