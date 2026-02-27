package com.bridgelabz.QuantityMeasurementApp.uc2_feet_and_inches_equality_measurement_test;
import com.bridgelabz.quantityMeasurementApp.uc2_feet_and_inches_equality_measurement.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class FeetAndInchesEqualityMeasurementTest {
    @Test
    void testFeetEquality_SameValue() {
        assertTrue(FeetAndInchesEqualityMeasurement.checkFeetEquality(1.0, 1.0));
    }

    @Test
    void testFeetEquality_DifferentValue() {
        assertFalse(FeetAndInchesEqualityMeasurement.checkFeetEquality(1.0, 2.0));
    }

    @Test
    void testFeetEquality_InvalidInput() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            FeetAndInchesEqualityMeasurement.validateInput("abc");
        });

        assertEquals("Invalid input", exception.getMessage());
    }



    @Test
    void testInchesEquality_SameValue() {
        assertTrue(FeetAndInchesEqualityMeasurement.checkInchesEquality(1.0, 1.0));
    }

    @Test
    void testInchesEquality_DifferentValue() {
        assertFalse(FeetAndInchesEqualityMeasurement.checkInchesEquality(1.0, 2.0));
    }
    @Test
    void testInchesEquality_SameReference() {
        Inches i1 = new Inches(1.0);
        assertEquals(i1, i1);
    }
    @Test
    void testFeetEquality_SameReference() {
        Feet f1 = new Feet(1.0);
        assertEquals(f1, f1);
    }




//    @Test
//    void testInchesEquality_SameReference() {
//        FeetAndInchesEqualityMeasurement.Inches i1 =
//                new FeetAndInchesEqualityMeasurement.Inches(1.0);
//
//        assertTrue(i1.equals(i1));
//    }
}
