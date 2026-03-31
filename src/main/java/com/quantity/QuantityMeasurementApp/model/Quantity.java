package com.quantity.QuantityMeasurementApp.model;

import java.util.Objects;

public class Quantity<U extends Unit> {

    private static final double EPSILON = 0.0001;

    private final double value;
    private final U unit;

    public Quantity(double value, U unit) {
        if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
        if (!Double.isFinite(value)) throw new IllegalArgumentException("Invalid value");
        this.value = value;
        this.unit = unit;
    }

    public double toBaseUnit() {
        return unit.convertToBaseUnit(value);
    }

    public Quantity<U> convertTo(U target) {
        double converted = target.convertFromBaseUnit(this.toBaseUnit());
        return new Quantity<>(converted, target);
    }

    private void checkArithmeticSupport(String operation) {
        unit.validateOperationSupport(operation);
    }

    public Quantity<U> add(Quantity<U> other) {
        checkArithmeticSupport("addition");
        double resultBase = this.toBaseUnit() + other.toBaseUnit();
        return new Quantity<>(unit.convertFromBaseUnit(resultBase), unit);
    }

    public Quantity<U> subtract(Quantity<U> other) {
        checkArithmeticSupport("subtraction");
        double resultBase = this.toBaseUnit() - other.toBaseUnit();
        return new Quantity<>(unit.convertFromBaseUnit(resultBase), unit);
    }

    public double divide(Quantity<U> other) {
        checkArithmeticSupport("division");
        return this.toBaseUnit() / other.toBaseUnit();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Quantity<?> other)) return false;

        // Ensure same unit category
        if (this.unit.getClass() != other.unit.getClass()) return false;

        return Math.abs(this.toBaseUnit() - other.toBaseUnit()) < EPSILON;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Math.round(toBaseUnit() / EPSILON));
    }

    public double getValue() { return value; }
    public U getUnit() { return unit; }
}