package com.bridgelabz.QuantityMeasurementApp.uc1_feet_equality_test;
import com.bridgelabz.quantityMeasurementApp.uc1_feet_equality_measurement.FeetEqualityMeasurement;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class FeetEqualityMeasurementTest {
    @Test
    void testEquality_SameValue() {
        FeetEqualityMeasurement.Feet feet1 =
                new FeetEqualityMeasurement.Feet(1.0);
        FeetEqualityMeasurement.Feet feet2 =
                new FeetEqualityMeasurement.Feet(1.0);

        assertEquals(feet1, feet2);
    }

    @Test
    void testEquality_DifferentValue() {
        FeetEqualityMeasurement.Feet feet1 =
                new FeetEqualityMeasurement.Feet(1.0);
        FeetEqualityMeasurement.Feet feet2 =
                new FeetEqualityMeasurement.Feet(2.0);

        assertNotEquals(feet1, feet2);
    }

    @Test
    void testEquality_NullComparison() {
        FeetEqualityMeasurement.Feet feet =
                new FeetEqualityMeasurement.Feet(1.0);

        assertNotEquals(null, feet);
    }

    @Test
    void testEquality_SameReference() {
        FeetEqualityMeasurement.Feet feet =
                new FeetEqualityMeasurement.Feet(1.0);

        assertEquals(feet, feet);
    }

    @Test
    void testEquality_SymmetricProperty() {
        FeetEqualityMeasurement.Feet feet1 =
                new FeetEqualityMeasurement.Feet(1.0);
        FeetEqualityMeasurement.Feet feet2 =
                new FeetEqualityMeasurement.Feet(1.0);

        assertEquals(feet1, feet2);
        assertEquals(feet2, feet1);
    }

    @org.testng.annotations.Test
    void testValidateInput_ValidNumber() {
        double result = FeetEqualityMeasurement.validateInput("5.5");
        assertEquals(5.5, result);
    }

    @Test
    void testValidateInput_InvalidNumber() {
        assertThrows(IllegalArgumentException.class, () -> {
            FeetEqualityMeasurement.validateInput("abc");
        });
    }
}
