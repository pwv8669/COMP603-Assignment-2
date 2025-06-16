/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blackjackprogram;

/**
 *
 * @author blake
 */
class GameState {
    public Deck deck;
    public Player player;
    public Dealer dealer;

    // Used to keep track of the current state of the deck, dealer and player.
    public GameState(Deck deck, Dealer dealer, Player player) {
        this.deck = deck;
        this.dealer = dealer;
        this.player = player;
    }
}
