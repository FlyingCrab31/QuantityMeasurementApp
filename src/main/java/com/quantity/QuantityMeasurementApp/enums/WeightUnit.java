package com.quantity.QuantityMeasurementApp.enums;

import com.quantity.QuantityMeasurementApp.model.Unit;

public enum WeightUnit implements Unit {

    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(0.453592);

    private final double toKg;

    WeightUnit(double toKg) {
        this.toKg = toKg;
    }

    public double convertToBaseUnit(double value) {
        return value * toKg;
    }

    public double convertFromBaseUnit(double baseValue) {
        return baseValue / toKg;
    }
}