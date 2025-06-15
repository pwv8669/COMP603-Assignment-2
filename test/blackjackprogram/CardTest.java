/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package blackjackprogram;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author blake
 */
public class CardTest {

    @Test
    // Test that he toString method works as intended.
    public void testCardToString() {
        Card card = new Card(Card.Rank.Ace, Card.Suit.Spades);
        assertEquals("Ace of Spades", card.toString());
    }

}
