package com.quantity.QuantityMeasurementApp.model;

public class QuantityModel {

    private double value;
    private Enum<?> unit;

    public QuantityModel(double value, Enum<?> unit) {
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public Enum<?> getUnit() {
        return unit;
    }
}