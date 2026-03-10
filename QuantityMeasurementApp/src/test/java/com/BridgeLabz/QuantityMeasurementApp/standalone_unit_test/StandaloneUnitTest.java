package com.bridgelabz.QuantityMeasurementApp.standalone_unit_test;
import com.bridgelabz.quantityMeasurementApp.standalone_unit.QuantityLength;
import com.bridgelabz.quantityMeasurementApp.standalone_unit.LengthUnit;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class StandaloneUnitTest {

    private static final double EPSILON = 0.0001;


    @Test
    void testLengthUnitEnum_FeetConstant(){
        assertEquals(1.0, LengthUnit.FEET.getConversionFactor(), EPSILON);
    }

    @Test
    void testLengthUnitEnum_InchesConstant() {
        assertEquals(1.0 / 12.0, LengthUnit.INCHES.getConversionFactor(), EPSILON);
    }

    @Test
    void testLengthUnitEnum_YardsConstant() {
        assertEquals(3.0, LengthUnit.YARDS.getConversionFactor(), EPSILON);
    }

    @Test
    void testLengthUnitEnum_CentimetersConstant() {
        assertEquals(1.0 / 30.48, LengthUnit.CENTIMETERS.getConversionFactor(), EPSILON);
    }


    @Test
    void testConvertToBaseUnit_FeetToFeet() {
        assertEquals(5.0,
                LengthUnit.FEET.convertToBaseUnit(5.0),
                EPSILON);
    }

    @Test
    void testConvertToBaseUnit_InchesToFeet() {


        assertEquals(1.0,
                LengthUnit.INCHES.convertToBaseUnit(12.0),
                EPSILON);
    }

    @Test
    void testConvertToBaseUnit_YardsToFeet() {
        assertEquals(3.0,
                LengthUnit.YARDS.convertToBaseUnit(1.0),
                EPSILON);
    }

    @Test
    void testConvertToBaseUnit_CentimetersToFeet(){
        assertEquals(1.0,
                LengthUnit.CENTIMETERS.convertToBaseUnit( 30.48),
                EPSILON);
    }


    @Test
    void testConvertFromBaseUnit_FeetToFeet() {
        assertEquals(2.0,
                LengthUnit.FEET.convertFromBaseUnit( 2.0 ),
                EPSILON);
    }

    @Test
    void testConvertFromBaseUnit_FeetToInches() {
        assertEquals(12.0,
                LengthUnit.INCHES.convertFromBaseUnit(1.0),
                EPSILON);
    }

    @Test
    void testConvertFromBaseUnit_FeetToYards() {
        assertEquals(1.0,
                LengthUnit.YARDS.convertFromBaseUnit( 3.0 ),
                EPSILON);
    }

    @Test
    void testConvertFromBaseUnit_FeetToCentimeters() {
        assertEquals(30.48,
                LengthUnit.CENTIMETERS.convertFromBaseUnit(1.0),
                EPSILON);
    }


    @Test
    void testQuantityLengthRefactored_Equality(){

        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);

        assertEquals(q1, q2);
    }



    @Test
    void testQuantityLengthRefactored_ConvertTo() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength result = q1.convertTo(LengthUnit.INCHES);

        assertEquals(12.0, result.getValue(), EPSILON);
        assertEquals(LengthUnit.INCHES, result.getUnit());
    }



    @Test
    void testQuantityLengthRefactored_Add(){

        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);

        QuantityLength result = q1.add(q2, LengthUnit.FEET);

        assertEquals(2.0, result.getValue(), EPSILON);
        assertEquals(LengthUnit.FEET, result.getUnit());
    }

    @Test
    void testQuantityLengthRefactored_AddWithTargetUnit() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);

        QuantityLength result = q1.add(q2, LengthUnit.YARDS);

        assertEquals(0.67, result.getValue(), EPSILON);
        assertEquals(LengthUnit.YARDS, result.getUnit());
    }



    @Test
    void testQuantityLengthRefactored_NullUnit(){
        assertThrows(IllegalArgumentException.class,
                () -> new QuantityLength(1.0, null));
    }

    @Test
    void testQuantityLengthRefactored_InvalidValue() {
        assertThrows(IllegalArgumentException.class,
                () -> new QuantityLength(Double.NaN, LengthUnit.FEET));
    }


    @Test
    void testBackwardCompatibility_UC1EqualityTests() {
        QuantityLength q1 = new QuantityLength(3.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(36.0, LengthUnit.INCHES);

        assertEquals(q1, q2);
    }

    @Test
    void testRoundTripConversion_RefactoredDesign() {
        QuantityLength original =
                new QuantityLength(5.0, LengthUnit.FEET);

        QuantityLength converted =
                original.convertTo(LengthUnit.INCHES)
                        .convertTo(LengthUnit.FEET);

        assertEquals(original.getValue(),
                converted.getValue(),
                EPSILON);
    }
}
