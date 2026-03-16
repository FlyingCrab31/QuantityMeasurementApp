package com.bridgelabz.QuantityMeasurementApp.generic_quantity_test;
import com.bridgelabz.quantityMeasurementApp.generic_quantity.LengthUnit;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LengthUnitTest {
    @Test
    void givenFeet_WhenConvertedToBaseUnit_ShouldReturnCorrectValue() {
        double result = LengthUnit.FEET.convertToBaseUnit(1);
        assertEquals(12, result, 0.001);
    }

    @Test
    void givenInch_WhenConvertedToBaseUnit_ShouldReturnCorrectValue() {
        double result = LengthUnit.INCHES.convertToBaseUnit(12);
        assertEquals(12, result, 0.001);
    }
}
