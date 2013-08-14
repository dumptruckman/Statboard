package com.dumptruckman.statboard;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class TicksPerSecondTask extends BukkitRunnable {

    private static final long NUM_TICKS = 20L;

    private final Plugin plugin;
    private final int maxLength;
    private final LinkedList<Long> history;
    private long lastFallOff = 0L;

    public TicksPerSecondTask(Plugin plugin, int maxLength) {
        this.plugin = plugin;
        this.maxLength = maxLength;
        this.history = new LinkedList<Long>();
    }

    public TicksPerSecondTask(Plugin plugin) {
        this(plugin, 360);
    }

    @Override
    public void run() {
        history.add(getCurrentTime());
        if (history.size() > maxLength) {
            lastFallOff = history.removeFirst();
        }
    }

    public int getTicksPerSecond(int averageLength) {
        return (int) Math.round(getExactTicksPerSecond(averageLength));
    }

    public int getTicksPerSecond() {
        return getTicksPerSecond(maxLength);
    }

    public double getExactTicksPerSecond() {
        return getExactTicksPerSecond(maxLength);
    }

    public double getExactTicksPerSecond(int averageLength) {
        List<Long> l = new ArrayList<Long>(history);
        int i;
        if (averageLength > l.size()) {
            i = 0;
        } else {
            i = l.size() - averageLength;
        }
        ListIterator<Long> it = l.listIterator();
        long previous;
        if (i == 0) {
            previous = lastFallOff;
        } else {
            previous = l.get(i - 1);
        }
        long total = 0L;
        while (it.hasNext()) {
            long current = it.next();
            long delta = current - previous;
            total += delta;
            previous = current;
            i++;
        }
        if (total == 0L) {
            return 0D;
        }

        return (i * NUM_TICKS) / ((double) total / 1000000000D);
    }

    private DecimalFormat getDecimalFormat(int precision) {
        StringBuilder format = new StringBuilder("#");
        if (precision > 0) {
            format.append('.');
            for (int i = 0; i < precision; i++) {
                format.append('#');
            }
        }
        return new DecimalFormat(format.toString());
    }

    public String getFormattedTicksPerSecond(int precision) {
        return getFormattedTicksPerSecond(precision, maxLength);
    }

    public String getFormattedTicksPerSecond(int precision, int averageLength) {
        return getDecimalFormat(precision).format(getExactTicksPerSecond(averageLength));
    }

    public void start() {
        lastFallOff = getCurrentTime();
        history.add(lastFallOff);
        runTaskTimer(plugin, NUM_TICKS, NUM_TICKS);
    }

    private static long getCurrentTime() {
        return System.nanoTime();
    }
}
