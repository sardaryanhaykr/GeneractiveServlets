package model;

/**
 * Created by Hayk on 21.07.2021.
 */

public class Configuration {
    private final Resolution resolution;


    public Configuration(Resolution resolution) {
        this.resolution = resolution;

    }

    Resolution getResolution() {
        return resolution;
    }
}
