package com.quantity.QuantityMeasurementApp.enums;

import com.quantity.QuantityMeasurementApp.model.Unit;

public enum VolumeUnit implements Unit {

    LITRE(1.0),
    MILLILITRE(0.001),
    GALLON(3.78);

    private final double toLitre;

    VolumeUnit(double toLitre) {
        this.toLitre = toLitre;
    }

    @Override
    public double convertToBaseUnit(double value) {
        return value * toLitre;
    }

    @Override
    public double convertFromBaseUnit(double baseValue) {
        return baseValue / toLitre;
    }
}