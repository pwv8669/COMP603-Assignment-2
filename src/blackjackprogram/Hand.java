/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blackjackprogram;

import blackjackprogram.Card.Rank;


/**
 *
 * @author blake
 */
public abstract class Hand implements Participant{
    Card[] cards = new Card[12]; // 11 is the most cards one could have without busting, we reserve a space if they want to hit.
    boolean busted = false;
    protected Deck deck;
    
    // Constructor Method
    public Hand(Deck deck) {
        this.deck = deck;
        cards[0] = deck.drawCard(); 
        // Because the dealer has a hidden card, we do not generate cards[1] automatically.
        // Instead, for playerHand, we immediatley hit after creating the hand, which populates card[1].
    }
    
    // To String Method
    public String toString(){
        String output = "";
        // Iterate through the cards and concatenate each card to the output string, separated by commas.
        for (int i=0; i<this.cards.length; i++) {    
            if(this.cards[i]!=null){
                if(i>0){
                    output = output+", ";
                }
                output = output+ this.cards[i].toString();
            }
        }
        return output;
    }
    
    // ------ Compare Hands ------ //
    @Override
    public int value(){
        int points = 0;
        int aceSum = 0;
        for(int i=0; i<this.cards.length; i++) {
            if(this.cards[i]!=null){
                Rank rank = this.cards[i].rank;
                switch(rank){
                    // Because aces can equal 1 or 11, we track how many aces are in the hand and add them to the points later.
                    case Ace: aceSum+=1; break;
                    case Two:points+=2; break;
                    case Three: points+=3; break;
                    case Four: points+=4; break;
                    case Five: points+=5; break;
                    case Six: points+=6; break;
                    case Seven: points+=7; break;
                    case Eight: points+=8; break;
                    case Nine: points+=9; break;
                    default: points+=10; break;
                }
            }
        }
        // Derive points from cards which are Aces.
        for(int i=0; i<aceSum; i++){
            // In order to get the best hand, if adding 11 does not cause a bust, add 11.
            if(points+11<=21){
                points+=11;
            }
            // Otherwise, add only 1 to prevent busting.
            else{
                points+=1;
            }
        }
        return points;
    }
    
    // ------ Decision Functions ------ //
    // Hit
    @Override
    public void hit(){
        for(int i=1; i<this.cards.length; i++){ // We start at i=1 because the hand constuctor method initializes one card in the hand.
            if(this.cards[i]==null){
                // Add a random card to the deck.
                this.cards[i]=deck.drawCard();
                break;
            }
        }
        if(this.value()>21){
            // Bust if over 21.
            this.busted = true;
        }
    }
    
    @Override
    public boolean isBust(){
        return this.busted;
    }
}
