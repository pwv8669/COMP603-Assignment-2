/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blackjackprogram;

/**
 *
 * @author blake
 */
public class Player extends Hand{
    
    public Player(Deck deck) {
        super(deck);
        this.hit();// Immediatley draw second card
    }
    
    // Print hand value and make the dealer play.
    public void stand(Dealer dealer){
        System.out.println("Player hand value: "+this.value());
        dealer.play();
    }
}
