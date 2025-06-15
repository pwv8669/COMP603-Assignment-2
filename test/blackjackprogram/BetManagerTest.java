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
public class BetManagerTest {

    private BetManager manager;

    @Before
    public void setUp() {
        manager = new BetManager();
        manager.storage.saveMoney(100); // Reset storage to 100 before each test
        manager = new BetManager(); // Reload updated funds
    }

    @Test
    // Check if money and current bet is updated accordingly.
    public void testPlaceValidBet() {
        boolean result = manager.placeBet(50);
        assertTrue(result);
        assertEquals(50, manager.getCurrentBet());
        assertEquals(50, manager.getPlayerMoney());
    }

    @Test
    // Check if bets are refused to those who cant afford the bet.
    public void testPlaceInvalidBetTooExpensive() {
        boolean result = manager.placeBet(150);
        assertFalse(result);
        assertEquals(0, manager.getCurrentBet());
    }

    @Test
    // Make sure players cant put negative bets and intentionally lose to make money.
    public void testPlaceInvalidBetNegative() {
        boolean result = manager.placeBet(-10);
        assertFalse(result);
    }

    @Test
    // Test to check if standard payout math is working.
    public void testWinStandardPayout() {
        manager.placeBet(20);
        manager.win(false);
        assertEquals(120, manager.getPlayerMoney());
    }

    @Test
    // Test to check if natural blackjack payout math is correct.
    public void testWinNaturalBlackjackPayout() {
        manager.placeBet(20);
        manager.win(true);
        assertEquals(110, manager.getPlayerMoney());
    }

    @Test
    // Test to see if current bet is reset and money is deducted after losing.
    public void testLose() {
        manager.placeBet(30);
        manager.lose();
        assertEquals(70, manager.getPlayerMoney());
        assertEquals(0, manager.getCurrentBet());
    }

    @Test
    // Check if double down doubles the bet and subtracts from player's money again.
    public void testDoubleDownPossible() {
        manager.placeBet(20);
        boolean result = manager.doubleDown();
        assertTrue(result);
        assertEquals(40, manager.getCurrentBet());
        assertEquals(60, manager.getPlayerMoney());
    }

    @Test
    public void testDoubleDownImpossible() {
        manager.placeBet(60);
        boolean result = manager.doubleDown();
        assertFalse(result);
    }

    @Test
    // Check to see if refunds are given in a tie, and bet is reset.
    public void testTieRefundsBet() {
        manager.placeBet(25);
        manager.tie();
        assertEquals(100, manager.getPlayerMoney());
        assertEquals(0, manager.getCurrentBet());
    }

    @Test
    // Checks to make sure that new betManagers can make bets.
    public void testBetPossible() {
        assertTrue(manager.canPlaceBet());
    }

    @Test
    // Checks if double downs can be made if the player has bet less than half their money.
    public void testCanDoubleDown() {
        manager.placeBet(30);
        assertTrue(manager.canDoubleDown());
    }

    @Test
    // Checks if double downs can't be made if the player has spent over half their money on their bet.
    public void testCantDoubleDown() {
        manager.placeBet(70);
        assertFalse(manager.canDoubleDown());
    }
}