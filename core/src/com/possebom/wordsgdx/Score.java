package com.possebom.wordsgdx;

public class Score {
    private static int hit = 0;
    private static int miss = 0;

    public static void hit() {
        hit++;
    }

    public static void miss() {
        miss++;
    }

    public static void clear() {
        miss = 0;
        hit = 0;
    }

    public static int getMiss() {
        return miss;
    }

    public static int getHit() {
        return hit;
    }

}
