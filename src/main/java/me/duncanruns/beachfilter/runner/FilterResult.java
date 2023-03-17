package me.duncanruns.beachfilter.runner;

public class FilterResult {

    private final long worldSeed;
    private final long startTime;
    private final int checks;
    private final int round;

    public FilterResult(long worldSeed, long startTime, int checks, int round) {
        this.worldSeed = worldSeed;
        this.startTime = startTime;
        this.checks = checks;
        this.round = round;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getWorldSeed() {
        return worldSeed;
    }

    public int getChecks() {
        return checks;
    }

    public String toToken() {
        return Long.toHexString(startTime) + "g" + Long.toHexString(worldSeed) + "g" + Integer.toHexString(checks) + "g" + Integer.toHexString(round);
    }

    @Override
    public String toString() {
        return "FilterResult{" +
                "worldSeed=" + worldSeed +
                ", initialSeed=" + startTime +
                ", checks=" + checks +
                '}';
    }
}
