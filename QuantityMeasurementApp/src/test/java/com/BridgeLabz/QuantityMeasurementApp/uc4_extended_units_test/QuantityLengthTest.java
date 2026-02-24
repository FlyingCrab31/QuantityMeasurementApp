package com.bridgelabz.QuantityMeasurementApp.uc4_extended_units_test;
import com.bridgelabz.quantityMeasurementApp.uc4_extended_units.QuantityLength;
import com.bridgelabz.quantityMeasurementApp.uc4_extended_units.LengthUnit;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class QuantityLengthTest {
    @Test
    void testEquality_YardToYard_SameValue() {


        assertEquals(new QuantityLength(1.0, LengthUnit.YARDS), new QuantityLength(1.0, LengthUnit.YARDS));
    }



    @Test
    void testEquality_YardToFeet_EquivalentValue() {


        assertEquals(new QuantityLength(1.0, LengthUnit.YARDS), new QuantityLength(3.0, LengthUnit.FEET));
    }

    @Test
    void testEquality_YardToInches_EquivalentValue() {

        assertEquals(new QuantityLength(1.0, LengthUnit.YARDS), new QuantityLength(36.0, LengthUnit.INCH));
    }

    @Test
    void testEquality_CentimeterToInch_EquivalentValue() {

        assertEquals(new QuantityLength(1.0, LengthUnit.CENTIMETERS), new QuantityLength(0.393701, LengthUnit.INCH));
    }

    @Test
    void testEquality_YardToFeet_NonEquivalentValue() {

        assertNotEquals(new QuantityLength(1.0, LengthUnit.YARDS), new QuantityLength(2.0, LengthUnit.FEET));
    }

    @Test
    void testEquality_MultiUnit_TransitiveProperty() {


        QuantityLength yard = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength feet = new QuantityLength(3.0, LengthUnit.FEET);
        QuantityLength inches = new QuantityLength(36.0, LengthUnit.INCH);

        assertEquals(yard, feet);
        assertEquals(feet, inches);
        assertEquals(yard, inches);
    }

    @Test
    void testEquality_NullComparison() {

        assertNotEquals(null, new QuantityLength(1.0, LengthUnit.YARDS));
    }

    @Test
    void testEquality_InvalidUnit() {
        assertThrows(IllegalArgumentException.class, () -> {

            new QuantityLength(1.0, null);
        });
    }
}
