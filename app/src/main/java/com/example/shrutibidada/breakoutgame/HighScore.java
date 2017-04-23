package com.example.shrutibidada.breakoutgame;

public class HighScore implements Comparable<HighScore> {
    private String name;
    private String time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public int compareTo(HighScore highScore) {
        String[] time1Arr = this.getTime().split(":");
        String[] time2Arr = highScore.getTime().split(":");
        return (Integer.parseInt(time1Arr[0]) * 60 + Integer.parseInt(time1Arr[1])) - (Integer.parseInt(time2Arr[0]) * 60 + Integer.parseInt(time2Arr[1]));
    }
}
