package com.quantity.QuantityMeasurementApp.enums;

import com.quantity.QuantityMeasurementApp.model.SupportsArithmetic;
import com.quantity.QuantityMeasurementApp.model.Unit;

import static java.lang.Math.round;

import java.util.function.Function;

public enum TemperatureUnit implements Unit {

    CELSIUS(c -> c, c -> c),
    FAHRENHEIT(f -> (f - 32) * 5 / 9, c -> (c * 9 / 5) + 32),
    KELVIN(k -> k - 273.15, c -> c + 273.15);

    private final Function<Double, Double> toCelsius;
    private final Function<Double, Double> fromCelsius;
    private final SupportsArithmetic supportsArithmetic = () -> false;

    TemperatureUnit(Function<Double, Double> toCelsius, Function<Double, Double> fromCelsius) {
        this.toCelsius = toCelsius;
        this.fromCelsius = fromCelsius;
    }

    @Override
    public double convertToBaseUnit(double value) {
        return toCelsius.apply(value);
    }

    @Override
    public double convertFromBaseUnit(double baseValue) {
        return fromCelsius.apply(baseValue);
    }

    @Override
    public boolean supportsArithmetic() {
        return supportsArithmetic.isSupported();
    }

    @Override
    public void validateOperationSupport(String operation) {
        throw new UnsupportedOperationException(
                "Temperature does not support operation: " + operation
        );
    }
}