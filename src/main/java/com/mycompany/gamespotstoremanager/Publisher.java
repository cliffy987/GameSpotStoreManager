/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gamespotstoremanager;

/**
 *
 * @author ebwhi
 */
public class Publisher {
    private long publisherId;
    private String name;

    public Publisher(long publisherId, String name) {
        this.publisherId = publisherId;
        this.name = name;
    }
    
    /**
     * @return the publisherId
     */
    public long getPublisherId() {
        return publisherId;
    }

    /**
     * @param publisherId the publisherId to set
     */
    public void setPublisherId(long publisherId) {
        this.publisherId = publisherId;
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
