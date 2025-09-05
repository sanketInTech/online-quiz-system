package model;

import java.time.LocalDateTime;

public class Result {
    private int id;
    private int userId;
    private String username;
    private int score;
    private LocalDateTime takenOn;

    public Result() {}

    public Result(int id, int userId, String username, int score, LocalDateTime takenOn) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.score = score;
        this.takenOn = takenOn;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public LocalDateTime getTakenOn() {
        return takenOn;
    }

    public void setTakenOn(LocalDateTime takenOn) {
        this.takenOn = takenOn;
    }

    @Override
    public String toString() {
        return String.format("Result{id=%d, userId=%d, username='%s', score=%d, takenOn=%s}",
                id, userId, username, score, takenOn);
    }
}
