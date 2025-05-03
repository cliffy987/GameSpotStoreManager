/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gamespotstoremanager;

import java.sql.Date;
import java.util.ArrayList;

public class GamePurchaseData {
    private GameData gameData;
    private double gamePrice;
    private long gameQuantity;
    private long usedId;
    private String condition;
    
    public GamePurchaseData(GameData gameData, double gamePrice, long gameQuantity) {
        this.gameData = gameData;
        this.gamePrice = gamePrice;
        this.gameQuantity = gameQuantity;
        this.usedId = -1;
        this.condition = "NEW";
    }
    
    public GamePurchaseData(GameData gameData, double gamePrice, long usedId, String condition) {
        this.gameData = gameData;
        this.gamePrice = gamePrice;
        this.gameQuantity = -1;
        this.usedId = usedId;
        this.condition = condition;
    }
    
    public long getGameId() {
        return gameData.getGameId();
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
     * @return the gamePrice
     */
    public double getGamePrice() {
        return gamePrice;
    }

    /**
     * @param gamePrice the gamePrice to set
     */
    public void setGamePrice(double gamePrice) {
        this.gamePrice = gamePrice;
    }

    /**
     * @return the gameQuantity
     */
    public long getGameQuantity() {
        return gameQuantity;
    }

    /**
     * @param gameQuantity the gameQuantity to set
     */
    public void setGameQuantity(long gameQuantity) {
        this.gameQuantity = gameQuantity;
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
    
    public String getGameName() {
        return gameData.getGameName();
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
