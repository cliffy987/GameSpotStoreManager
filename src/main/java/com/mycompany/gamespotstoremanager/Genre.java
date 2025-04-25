/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gamespotstoremanager;

/**
 *
 * @author ebwhi
 */
public class Genre {
    private long genreId;
    private String name;

    public Genre(long genreId, String name) {
        this.genreId = genreId;
        this.name = name;
    }
    
    /**
     * @return the genreId
     */
    public long getGenreId() {
        return genreId;
    }

    /**
     * @param genreId the genreId to set
     */
    public void setGenreId(long genreId) {
        this.genreId = genreId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    
}
