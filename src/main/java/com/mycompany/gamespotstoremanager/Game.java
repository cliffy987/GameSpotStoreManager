/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gamespotstoremanager;

import java.sql.Date;
import java.util.ArrayList;

public class Game {
    private long gameId;
    private String gameName;
    private Date releaseDate;
    private int esrbId;
    private String esrb;
    public ArrayList<Genre> genreList;
    public ArrayList<Publisher> publisherList;
    
    public Game(long gameId, String gameName, Date releaseDate, int esrbId, String esrb, ArrayList<Genre> genreList, ArrayList<Publisher> publisherList) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.releaseDate = releaseDate;
        this.esrbId = esrbId;
        this.esrb = esrb;
        this.genreList = genreList;
        this.publisherList = publisherList;
    }
    
    public Game (String gameName, Date releaseDate, int esrbId, ArrayList<Genre> genreList, ArrayList<Publisher> publisherList) {
        this.gameId = -1;
        this.gameName = gameName;
        this.releaseDate = releaseDate;
        this.esrbId = esrbId;
        this.esrb = GameDAO.getEsrbFromId(esrbId);
        this.genreList = genreList;
        this.publisherList = publisherList;
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
     * @return the releaseDate
     */
    public Date getReleaseDate() {
        return releaseDate;
    }

    /**
     * @param releaseDate the releaseDate to set
     */
    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * @return the esrb
     */
    public String getEsrb() {
        return esrb;
    }

    /**
     * @param esrb the esrb to set
     */
    public void setEsrb(String esrb) {
        this.esrb = esrb;
    }

    /**
     * @return the esrbId
     */
    public int getEsrbId() {
        return esrbId;
    }

    /**
     * @param esrbId the esrbId to set
     */
    public void setEsrbId(int esrbId) {
        this.esrbId = esrbId;
    }
}
