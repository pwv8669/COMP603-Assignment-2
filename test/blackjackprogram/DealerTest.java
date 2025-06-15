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
public class DealerTest {

    private Dealer dealer;
    private Deck deck;

    @Before
    public void setUp() {
        deck = new Deck();
        deck.shuffle();  // You might want to control this or use a stub for deterministic results
        dealer = new Dealer(deck);  // Automatically hits once on creation
    }

    @Test
    // Test to make sure dealer drew until the had 17 cards.
    public void testDealerDrawsUntil17() {
        dealer = new Dealer(deck);
        dealer.play();
        int dealerValue = dealer.value();
        assertTrue("Dealer value is " + dealerValue, dealerValue >= 17);
    }

    @Test
    // Test to see if busted works as intended.
    public void testDealerBusted() {
        dealer = new Dealer(deck);
        dealer.play();
        if (dealer.value() > 21) {
            assertTrue(dealer.busted);
        } else {
            assertFalse(dealer.busted);
        }
    }
}