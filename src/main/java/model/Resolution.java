package model;

/**
 * Created by Hayk on 21.07.2021.
 */
public enum Resolution {
    HD(1),
    FHD(2),
    _4K(4);
    private final int coefficient;

    Resolution(int coefficient) {
        this.coefficient = coefficient;
    }

    public int getCoefficient(){
        return this.coefficient;
    }

}
