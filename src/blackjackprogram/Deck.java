/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blackjackprogram;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author blake
 */
public class Deck {
    private List<Card> cards;
    
    // Creates a list of 52 unique cards and shuffles it.
    public Deck(){
        cards = new ArrayList<>();
        for(Card.Suit suit : Card.Suit.values()){
            for(Card.Rank rank : Card.Rank.values()){
                cards.add(new Card(rank, suit));
            }
        }
        shuffle();
    }

    // Reshuffles the deck.
    public void shuffle() {
        Collections.shuffle(cards);
    }
    
    // Returns a card from the top of the deck and removes said card.
    public Card drawCard(){
        return cards.remove(0);
    }
    
    // Returns the amount of cards remaining in the deck.
    public int remainingCards() {
        return cards.size();
    }
}
