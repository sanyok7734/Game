package com.odintsov.game.utils;

public class Screen {
    private static Screen ourInstance = new Screen();

    public static Screen getInstance() {
        return ourInstance;
    }

    private Screen() {
    }
}
