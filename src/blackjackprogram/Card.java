/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blackjackprogram;

/**
 *
 * @author aidan
 */
public class Card {
    // ------ Declare Enums ------ //
    public enum Rank{
        Ace,
        Two,
        Three,
        Four,
        Five,
        Six,
        Seven,
        Eight,
        Nine,
        Ten,
        Jack,
        Queen,
        King
    }
    public enum Suit{
        Clubs,
        Spades,
        Diamonds,
        Hearts
    }
    
    // ------ Card Methods ------ //
    // Constructor Method for card
    public Rank rank;
    public Suit suit;
    Card(Rank rank, Suit suit){
        this.rank = rank;
        this.suit = suit;
    }

    // Get method for rank.
    public Rank getRank() {
        return rank;
    }

    // Get method for suit.
    public Suit getSuit() {
        return suit;
    }

    // To string method.
    @Override
    public String toString() {
        return rank+" of "+suit;
    }
}
