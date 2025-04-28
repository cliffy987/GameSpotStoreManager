/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gamespotstoremanager;

/**
 *
 * @author ebwhi
 */
public class GameSearchData {
    private long gameId;
    private String gameName;
    private String gameGenres;
    private String gamePublishers;
    private String gameRating;

    GameSearchData(long gameId, String gameName, String gameGenres, String gamePublishers, String gameRating) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.gameGenres = gameGenres;
        this.gamePublishers = gamePublishers;
        this.gameRating = gameRating;
    }
    
    /**
     * @return the gameName
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * @param gameName the gameName to set
     */
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    /**
     * @return the gameGenres
     */
    public String getGameGenres() {
        return gameGenres;
    }

    /**
     * @param gameGenres the gameGenres to set
     */
    public void setGameGenres(String gameGenres) {
        this.gameGenres = gameGenres;
    }

    /**
     * @return the gamePublishers
     */
    public String getGamePublishers() {
        return gamePublishers;
    }

    /**
     * @param gamePublishers the gamePublishers to set
     */
    public void setGamePublishers(String gamePublishers) {
        this.gamePublishers = gamePublishers;
    }

    /**
     * @return the gameRating
     */
    public String getGameRating() {
        return gameRating;
    }

    /**
     * @param gameRating the gameRating to set
     */
    public void setGameRating(String gameRating) {
        this.gameRating = gameRating;
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
}
