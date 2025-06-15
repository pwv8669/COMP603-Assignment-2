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
public class MoneyStorageTest {

    private MoneyStorage storage;

    @Before
    public void setUp() {
        storage = new MoneyStorage();
        storage.saveMoney(100); // reset funds to baseline before each test
    }

    @Test
    // Check if money starts at $100.
    public void testStartingMoney() {
        int funds = storage.loadMoney();
        assertEquals("Initial funds should be $100", 100, funds);
    }

    @Test
    // Check if money is able to be updated.
    public void testSaveMoney() {
        storage.saveMoney(75);
        int funds = storage.loadMoney();
        assertEquals("Funds should be updated to $75", 75, funds);
    }

    @Test
    // Test to mae sure the database doesn't break after updating the money.
    public void testMultipleUpdates() {
        storage.saveMoney(50);
        assertEquals(50, storage.loadMoney());
        storage.saveMoney(120);
        assertEquals(120, storage.loadMoney());
    }

}
