package com.example.model;

/**
 * Created by Hayk on 21.07.2021.
 */

public class Configuration {
    private final Resolution resolution;


    public Configuration(Resolution resolution) {
        this.resolution = resolution;

    }

   public Resolution getResolution() {
        return resolution;
    }
}
