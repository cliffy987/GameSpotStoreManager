/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gamespotstoremanager;

/**
 *
 * @author ebwhi
 */
public class UsedGame {
    private long gameId;
    private double price;
    private String condition;
    
    public UsedGame(long gameId, double price, String condition) {
        this.gameId = gameId;
        this.price = price;
        this.condition = condition;
    }

    /**
     * @return the gameId
     */
    public long getGameId() {
        return gameId;
    }

    /**
     * @param gameId the gameId to set
     */
    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the condition
     */
    public String getCondition() {
        return condition;
    }

    /**
     * @param condition the condition to set
     */
    public void setCondition(String condition) {
        this.condition = condition;
    }
}
