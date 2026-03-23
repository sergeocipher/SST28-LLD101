package com.example.snakeladder;

public class Player {
    private final String id;
    private int position;
    private boolean active;

    public Player(String id) {
        this.id = id;
        this.position = 0;
        this.active = true;
    }

    public String getId() {
        return id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
