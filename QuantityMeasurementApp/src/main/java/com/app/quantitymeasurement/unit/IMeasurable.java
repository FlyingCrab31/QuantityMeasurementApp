package com.app.quantitymeasurement.unit;

public interface IMeasurable {
    double getConversionFactor();

    default double convertToBaseUnit(double value) {
        return value * getConversionFactor();
    }

    default double convertFromBaseUnit(double baseValue) {
        return baseValue / getConversionFactor();
    }

    String getUnitName();

    String getMeasurementType();

    default boolean supportsArithmetic() {
        return true;
    }

    static IMeasurable getUnitByName(String unitName) {
        if (unitName == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }

        String normalized = unitName.toUpperCase();
        if (normalized.equals("INCH")) {
            normalized = "INCHES";
        }

        for (LengthUnit unit : LengthUnit.values()) {
            if (unit.getUnitName().equalsIgnoreCase(normalized)) {
                return unit;
            }
        }

        for (WeightUnit unit : WeightUnit.values()) {
            if (unit.getUnitName().equalsIgnoreCase(normalized)) {
                return unit;
            }
        }

        for (VolumeUnit unit : VolumeUnit.values()) {
            if (unit.getUnitName().equalsIgnoreCase(normalized)) {
                return unit;
            }
        }

        for (TemperatureUnit unit : TemperatureUnit.values()) {
            if (unit.getUnitName().equalsIgnoreCase(normalized)) {
                return unit;
            }
        }

        throw new IllegalArgumentException("Invalid unit: " + unitName);
    }
}
