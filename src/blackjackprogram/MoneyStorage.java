/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blackjackprogram;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author blake
 */
public class MoneyStorage {

    private DBManager database = new DBManager();
    private Connection conn = database.getConnection();
    private Statement statement;

    public MoneyStorage(){
        connectBlackjackDB();
    }
    
    public void connectBlackjackDB() {
        //use the conn, initialize database by creating BOOK Table and insert records
        try {
            statement = conn.createStatement();
            try{
                String sqlTableExists = "SELECT MONEY FROM MONEYTABLE WHERE USERID = 1";
                statement.executeQuery(sqlTableExists);
            }
            catch (SQLException ex) {
                String sqlCreate = "CREATE TABLE MONEYTABLE ("
                    + "USERID int, "
                    + "MONEY int)";
                statement.executeUpdate(sqlCreate);

                String sqlInsert = "INSERT INTO MONEYTABLE VALUES (1, 100)";
                statement.executeUpdate(sqlInsert);
                System.out.println("Money table created and set money to 100.");                
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int loadMoney() {
        try {
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT MONEY FROM MONEYTABLE WHERE USERID =1");
            if (rs.next()) {
                return rs.getInt("MONEY");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public void saveMoney(int amount) {
        try {
            statement = conn.createStatement();
            statement.executeUpdate("UPDATE MONEYTABLE SET MONEY = " + amount + " WHERE USERID =1");
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
