package com.a65apps.kalimullinilnazrafilovich.interactors.time;

public class CurrentTimeModel implements CurrentTime {
    @Override
    public long now() {
        return System.currentTimeMillis();
    }
}
