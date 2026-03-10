package com.bridgelabz.quantityMeasurementApp.standalone_unit;
import java.util.Objects;
public class QuantityLength {
    private final double value;
    private final LengthUnit unit;

    private static final double EPSILON = 0.0001;

    public QuantityLength(double value,  LengthUnit unit) {
        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");

        if (Double.isNaN(value) || Double.isInfinite(value))
            throw new IllegalArgumentException("Invalid numeric value");

        this.value = value ;
        this.unit = unit ;
    }

    public double getValue() {
        return value;
    }

    public LengthUnit getUnit() {
        return unit;
    }

    // Convert to another unit
    public QuantityLength convertTo(LengthUnit targetUnit) {
        double baseValue = unit.convertToBaseUnit(this.value);
        double convertedValue = targetUnit.convertFromBaseUnit(baseValue);
        return new QuantityLength( round(convertedValue), targetUnit);
    }

    // Add with explicit target unit (UC7 compatible)
    public QuantityLength add(QuantityLength other, LengthUnit targetUnit) {

        double baseValue1 = this.unit.convertToBaseUnit(this.value);
        double baseValue2 = other.unit.convertToBaseUnit(other.value);

        double sumBase = baseValue1 + baseValue2;

        double finalValue = targetUnit.convertFromBaseUnit(sumBase);

        return new QuantityLength( round(finalValue), targetUnit);
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof QuantityLength )) return false;
        QuantityLength other = (QuantityLength) obj;
        double baseValue1 = this.unit.convertToBaseUnit(this.value);
        double baseValue2 = other.unit.convertToBaseUnit(other.value);
        return Math.abs(baseValue1 - baseValue2) < EPSILON;
    }

    @Override
    public int hashCode() {
        return Objects.hash( round(unit.convertToBaseUnit(value)));
    }

    private double round(double value ) {
        return Math.round(value * 100.0)  / 100.0;
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }
}
