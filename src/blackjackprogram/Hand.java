/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blackjackprogram;

import java.util.Random;


/**
 *
 * @author blake
 */
public class Hand {
    Card[] cards = new Card[12]; // 11 is the most cards one could have without busting, we reserve a space if they want to hit.
    boolean busted = false;
    
    // Constructor Method
    public Hand(Card[] otherHandsCards) {
        Card card = randomCard(otherHandsCards);
        cards[0] = card; 
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
    public int value(){
        int points = 0;
        int aceSum = 0;
        for(int i=0; i<this.cards.length; i++) {
            if(this.cards[i]!=null){
                Card.Rank rank = this.cards[i].rank;
                switch(rank){
                    case Card.Rank.Ace:
                        // Because aces can equal 1 or 11, we track how many aces are in the hand and add them to the points later.
                        aceSum+=1;
                        break;
                    case Card.Rank.Two:
                        points+=2;
                        break;
                    case Card.Rank.Three:
                        points+=3;
                        break;
                    case Card.Rank.Four:
                        points+=4;
                        break;
                    case Card.Rank.Five:
                        points+=5;
                        break;
                    case Card.Rank.Six:
                        points+=6;
                        break;
                    case Card.Rank.Seven:
                        points+=7;
                        break;
                    case Card.Rank.Eight:
                        points+=8;
                        break;
                    case Card.Rank.Nine:
                        points+=9;
                        break;
                    default:
                        points+=10;
                        break;
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
    public void hit(Card[] otherHandsCards){
        for(int i=1; i<this.cards.length; i++){ // We start at i=1 because the hand constuctor method initializes one card in the hand.
            if(this.cards[i]==null){
                // Add a random card to the deck.
                this.cards[i]=randomCard(otherHandsCards);
                break;
            }
        }
        if(this.value()>21){
            // Bust if over 21.
            this.busted = true;
        }
    }
    
    // Stand
    public void stand(Hand dealerHand){
        // Dealer will draw until 17, as per Blackjack rules.
        // By now, the most the dealer can have is 11, so they will need to draw at least once, this accounts for the hidden card.
        while(dealerHand.value()<17){
            dealerHand.hit(this.cards);
        }
        // Print the full dealer hand so player can look.
        System.out.println("Dealer's hand: "+dealerHand);
        // Print dealer's hand's value.
        if(dealerHand.busted){
            System.out.println("Dealer has busted with "+dealerHand.value()+".");
        }
        else{
            System.out.println("Dealer's hand's value: "+dealerHand.value());
        }
    }
    
    // ------ Random Card Functions ------ //
    // Returns a unique random card.
    private Card randomCard(Card[] otherHandsCards){
        Card card = new Card(this.randomRank(), this.randomSuit());
        // Iterate through both player and dealer's decks.
        for(int i=0; i<this.cards.length; i++){
            if(this.cards[i]!=null){
                // If the card exists in this deck already, use a different random card.
                if(card.rank==this.cards[i].rank&&card.suit==this.cards[i].suit){
                    card=randomCard(otherHandsCards);
                }
            }
            if(otherHandsCards[i]!=null){
                // If the card exists in the other deck already, use a different random card.
                if(card.rank==otherHandsCards[i].rank&&card.suit==otherHandsCards[i].suit){
                    card=randomCard(otherHandsCards);
                }
            }
        }
        return card;
    }
    
    Random random = new Random();
    // Returns a random rank.
    private Card.Rank randomRank(){
        return Card.Rank.values()[random.nextInt(Card.Rank.values().length)];
    }
    
    // Returns a random suit.
    private Card.Suit randomSuit(){
        return Card.Suit.values()[random.nextInt(Card.Suit.values().length)];
    }
}
