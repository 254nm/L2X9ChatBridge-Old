package org.l2x9.chatbridgebeta;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class GetTps implements Runnable {
    public static int TICK_COUNT = 0;
    public static long[] TICKS = new long[600];
    public static long LAST_TICK = 0L;

    public static double getTPS() {
        return getTPS(100);
    }

    public static double getTPS(int ticks) {
        if (TICK_COUNT < ticks) {
            return 20.0D;
        } else {
            int target = (TICK_COUNT - 1 - ticks) % TICKS.length;
            long elapsed = System.currentTimeMillis() - TICKS[target];
            return (double) ticks / ((double) elapsed / 1000.0D) > 20.0D ? 20.0D : round((double) ticks / ((double) elapsed / 1000.0D), 1);
        }
    }

    public static long getElapsed(int tickID) {
        if (TICK_COUNT - tickID >= TICKS.length) {
        }

        long time = TICKS[tickID % TICKS.length];
        return System.currentTimeMillis() - time;
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        } else {
            BigDecimal bd = BigDecimal.valueOf(value);
            bd = bd.setScale(places, RoundingMode.HALF_UP);
            return bd.doubleValue();
        }
    }

    public void run() {
        TICKS[TICK_COUNT % TICKS.length] = System.currentTimeMillis();
        ++TICK_COUNT;
    }
}
