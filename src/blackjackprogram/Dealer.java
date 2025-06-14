/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blackjackprogram;

/**
 *
 * @author blake
 */
public class Dealer extends Hand{
    
    public Dealer(Card[] otherHandsCards) {
        super(otherHandsCards);
        this.hit(otherHandsCards);
    }
    
    // Dealer draws until they reach 17.
    public void play(Card[] playerCards){
        // Dealer will draw until 17, as per Blackjack rules.
        // By now, the most the dealer can have is 11, so they will need to draw at least once, this accounts for the hidden card.
        while(value()<17){
            hit(playerCards);
        }
        // Print the full dealer hand so player can look.
        System.out.println("Dealer's hand: "+this);
        // Print dealer's hand's value.
        if(busted){
            System.out.println("Dealer has busted with "+value()+".");
        }
        else{
            System.out.println("Dealer's hand's value: "+value());
        }
    }
    
    public void showFirstCard(){
        if(cards[0]!=null){
            System.out.println("Dealers cards: "+cards[0]+", [Hidden]");
        }
        else{
            System.out.println("No cards are visible.");
        }
    }
}
