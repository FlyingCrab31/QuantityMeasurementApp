package com.app.quantitymeasurement.unit;

public enum LengthUnit implements IMeasurable {
    INCHES(1),
    FEET(12),
    YARD(36);

    private final double conversionFactor;

    LengthUnit(double factor) {
        this.conversionFactor = factor;
    }

    @Override
    public double getConversionFactor() {
        return conversionFactor;
    }

    @Override
    public String getUnitName() {
        return this.name();
    }

    @Override
    public String getMeasurementType() {
        return "LENGTH";
    }
}
