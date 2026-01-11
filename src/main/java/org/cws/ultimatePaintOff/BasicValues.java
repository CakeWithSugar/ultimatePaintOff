package org.cws.ultimatePaintOff;

public class BasicValues {
    public final int tick = 20;
    public final int maxGameTime = 5; // Minuten
    public final int maxGameTimeTicks = maxGameTime * 60 * tick; // Ticks
    public final int maxFuel = 200;
    public final int refuelTicks = 5;

    public final int maxQueues = 10;
    public final int maxQueueSize = 8;

    public final int minQueueSize = 3;
    public final int countDown = 10;

    public final int spawnHight = 64;

    public final boolean monochrome = false;
    public final String[] colors = {"ORANGE", "MAGENTA", "LIGHT_BLUE", "YELLOW", "LIME", "CYAN", "PURPLE", "BLUE", "GREEN", "RED"};
    public final String[] colorsMonochrome = {"WHITE", "ORANGE", "MAGENTA", "LIGHT_BLUE", "YELLOW", "LIME", "GRAY", "LIGHT_GRAY", "CYAN", "PURPLE", "BLUE", "GREEN", "RED", "BLACK"};

}
