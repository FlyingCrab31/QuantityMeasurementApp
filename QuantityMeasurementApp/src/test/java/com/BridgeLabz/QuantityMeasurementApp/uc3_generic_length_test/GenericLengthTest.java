package com.bridgelabz.QuantityMeasurementApp.uc3_generic_length_test;
import com.bridgelabz.quantityMeasurementApp.uc3_generic_length.QuantityLength;
import com.bridgelabz.quantityMeasurementApp.uc3_generic_length.LengthUnit;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GenericLengthTest {
    @Test
    void testEquality_FeetToFeet_SameValue() {


        assertEquals(new QuantityLength(1.0, LengthUnit.FEET), new QuantityLength(1.0, LengthUnit.FEET));
    }



    @Test
    void testEquality_InchToInch_SameValue() {


        assertEquals(new QuantityLength(1.0, LengthUnit.INCH), new QuantityLength(1.0, LengthUnit.INCH));
    }

    @Test
    void testEquality_FeetToInch_EquivalentValue() {

        assertEquals(new QuantityLength(1.0, LengthUnit.FEET), new QuantityLength(12.0, LengthUnit.INCH));
    }

    @Test
    void testEquality_InchToFeet_EquivalentValue() {


        assertEquals(new QuantityLength(12.0, LengthUnit.INCH), new QuantityLength(1.0, LengthUnit.FEET));
    }

    @Test
    void testEquality_FeetToFeet_DifferentValue() {


        assertNotEquals(new QuantityLength(1.0, LengthUnit.FEET), new QuantityLength(2.0, LengthUnit.FEET));
    }

    @Test
    void testEquality_InchToInch_DifferentValue() {


        assertNotEquals(new QuantityLength(1.0, LengthUnit.INCH), new QuantityLength(2.0, LengthUnit.INCH));
    }

    @Test
    void testEquality_SameReference() {


        QuantityLength q = new QuantityLength(1.0, LengthUnit.FEET);
        assertEquals(q, q);
    }

    @Test
    void testEquality_NullComparison() {


        assertNotEquals(null, new QuantityLength(1.0, LengthUnit.FEET));
    }

    @Test
    void testEquality_InvalidUnit() {


        assertThrows(IllegalArgumentException.class, () -> {
            new QuantityLength(1.0, null);
        });
    }
}
