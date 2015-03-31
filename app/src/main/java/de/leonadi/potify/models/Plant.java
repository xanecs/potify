package de.leonadi.potify.models;

import com.orm.SugarRecord;

import org.joda.time.DateTime;
import org.joda.time.Duration;

/**
 * Created by leon on 30.03.15.
 */
public class Plant extends SugarRecord {
    private String name;
    private String species;

    private long lastWatered;
    private long waterInterval;

    public Plant() {

    }

    public Plant (String name, String species, DateTime lastWatered, Duration waterInterval) {
        this.name = name;
        this.species = species;
        this.lastWatered = lastWatered.getMillis();
        this.waterInterval = waterInterval.getMillis();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public DateTime getLastWatered() {
        return new DateTime(lastWatered);
    }

    public void setLastWatered() {
        this.lastWatered = new DateTime().getMillis();
    }

    public void setLastWatered(DateTime lastWatered) {
        this.lastWatered = lastWatered.getMillis();
    }

    public Duration getWaterInterval() {
        return new Duration(waterInterval);
    }

    public void setWaterInterval(Duration waterInterval) {
        this.waterInterval = waterInterval.getMillis();
    }

    public DateTime getNextWaterNeeded() {
        return getLastWatered().withDurationAdded(getWaterInterval(), 1);
    }
}
