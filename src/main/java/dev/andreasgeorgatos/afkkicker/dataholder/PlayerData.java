package dev.andreasgeorgatos.afkkicker.dataholder;

import org.bukkit.Location;

public class PlayerData {
    private Location lastKnownLocation;
    private int count;

    public PlayerData(Location location) {
        this.lastKnownLocation = location;
        count = 0;
    }

    public Location getLastKnownLocation() {
        return lastKnownLocation;
    }

    public void setLastKnownLocation(Location lastKnownLocation) {
        this.lastKnownLocation = lastKnownLocation;
    }

    public int getCount() {
        return count;
    }

    public void resetCount() {
        count = 0;
    }

    public void increaseCount() {
        ++count;
    }
}
