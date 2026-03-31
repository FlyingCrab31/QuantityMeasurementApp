package com.quantity.QuantityMeasurementApp.model;

public interface Unit {

    // Mandatory conversion
    double convertToBaseUnit(double value);
    double convertFromBaseUnit(double baseValue);

    // Optional arithmetic support (default = true)
    SupportsArithmetic supportsArithmetic = () -> true;

    default boolean supportsArithmetic() {
        return supportsArithmetic.isSupported();
    }

    default void validateOperationSupport(String operation) {
        // Default: do nothing, all units support operations
    }
}