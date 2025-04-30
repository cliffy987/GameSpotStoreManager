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
    private long usedId;
    private GameData gameData;
    private double price;
    private String condition;
    
    public UsedGame(long usedId, GameData gameData, double price, String condition) {
        this.usedId = usedId;
        this.gameData = gameData;
        this.price = price;
        this.condition = condition;
    }

    /**
     * @return the usedId
     */
    public long getUsedId() {
        return usedId;
    }

    /**
     * @param usedId the usedId to set
     */
    public void setUsedId(long usedId) {
        this.usedId = usedId;
    }

    /**
     * @return the gameData
     */
    public GameData getGameData() {
        return gameData;
    }

    /**
     * @param gameData the gameData to set
     */
    public void setGameData(GameData gameData) {
        this.gameData = gameData;
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
