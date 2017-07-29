package com.example.jiahang.skateapp.database;

/**
 * Class only exists to define table constants
 */

public class ModelDbSchema {
    public static final class ModelTable {
        public static final String NAME = "models";
        // class exists to define table columns
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String PLAYER_1 = "player1";
            public static final String PLAYER_2 = "player2";
            public static final String PLAYER_1_SKATE = "player1skate";
            public static final String PLAYER_2_SKATE = "player2skate";
        }
    }
}
