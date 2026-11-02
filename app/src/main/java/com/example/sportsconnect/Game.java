package com.example.sportsconnect;

public class Game {
    private String id;
    private String sport;
    private String title;
    private String locationTime;
    private int currentPlayers;
    private int maxPlayers;

    public Game(String id, String sport, String title, String locationTime, int currentPlayers, int maxPlayers) {
        this.id = id;
        this.sport = sport;
        this.title = title;
        this.locationTime = locationTime;
        this.currentPlayers = currentPlayers;
        this.maxPlayers = maxPlayers;
    }

    public String getSport() { return sport; }
    public String getTitle() { return title; }
    public String getLocationTime() { return locationTime; }
    public int getCurrentPlayers() { return currentPlayers; }
    public int getMaxPlayers() { return maxPlayers; }
}