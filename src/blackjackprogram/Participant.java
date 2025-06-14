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
    void hit(Card[] otherHandsCards);
    int value();
    boolean isBust();
}
