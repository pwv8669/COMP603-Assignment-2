/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package blackjackprogram;

/**
 *
 * @author blake
 */
public interface Participant {
    // Adds a card to the participant's card list.
    void hit();
    // Returns the value of the participant's hand.
    int value();
    // Check if the participant's hand is above 21.
    boolean isBust();
}
