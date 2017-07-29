package com.example.jiahang.skateapp;

import java.util.UUID;

/**
 * Created by Jiahang on 7/23/2017.
 */

public class Model {
    private UUID uuid;

    private String player1;
    private int player1_skate;

    private String player2;
    private int player2_skate;

    // invoke the current class constructor
    public Model() {
        this(UUID.randomUUID());
    }

    public Model(UUID id) {
        uuid = id;

        player1_skate = 0;
        player2_skate = 0;
    }

    public UUID getId() {
        return uuid;
    }

    public void setId(UUID uuid) {
        this.uuid = uuid;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public int getPlayer1_skate() {
        return player1_skate;
    }

    public void setPlayer1_skate(int player1_skate) {
        this.player1_skate = player1_skate;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public int getPlayer2_skate() {
        return player2_skate;
    }

    public void setPlayer2_skate(int player2_skate) {
        this.player2_skate = player2_skate;
    }
}
