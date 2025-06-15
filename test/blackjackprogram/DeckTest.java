package blackjackprogram;

import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;

public class DeckTest {

    @Test
    // Test if draw is successfully returning cards.
    public void testDrawCardReturnsCard() {
        Deck deck = new Deck();
        Card card = deck.drawCard();
        assertNotNull("Deck should return a card", card);
    }

    @Test
    // Test if the draw function reduces the size of the deck correctly.
    public void testDrawReducesDeckSize() {
        Deck deck = new Deck();
        for (int i = 0; i < 5; i++) {
            deck.drawCard();
        }
        assertEquals("Deck should now have 47 cards", 47, deck.remainingCards());
    }

    @Test
    // Test if all cards drawn are unique.
    public void testAllDrawnCardsAreUnique() {
        Deck deck = new Deck();
        Set<Card> drawn = new HashSet<>();
        for (int i = 0; i < 52; i++) {
            Card card = deck.drawCard();
            assertTrue("Card should be unique", drawn.add(card)); // add returns false if duplicate
        }
        assertEquals("Should have drawn 52 unique cards", 52, drawn.size());
    }
}
