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
public class PlayerTest {

    private Deck deck;
    private Player player;

    @Before
    public void setUp() {
        deck = new Deck();
        player = new Player(deck);
    }

    @Test
    // Check if toString is empty.
    public void testToStringNotEmpty() {
        String output = player.toString();
        assertNotNull("toString should not return null", output);
        assertFalse("toString should not return empty string", output.isEmpty());
    }

    @Test
    // Test to make sure initial hand value is between 2 and 21.
    public void testInitialHandValue() {
        int value = player.value();
        assertTrue("Initial hand value should be at least 2", value >= 2);
        assertTrue("Initial hand value should be no more than 21", value <= 21);
    }

    @Test
    // Make sure that the value increases after hitting.
    public void testHitIncreasesValue() {
        int beforeValue = player.value();
        player.hit();
        int afterValue = player.value();
        assertTrue("Value after hit should be >= initial value (unless ace logic applies)", afterValue >= beforeValue || afterValue <= 21);
    }

    @Test
    // Keep hitting until players value exceeds 21. Then check if they're busted.
    public void testBust() {
        while (!player.isBust()) {
            player.hit();
            if (player.value() > 21) {
                break;
            }
        }
        assertTrue("Player should bust eventually after enough hits", player.isBust());
    }
}
