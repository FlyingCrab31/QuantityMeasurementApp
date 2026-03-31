package com.quantity.QuantityMeasurementApp;

import com.quantity.QuantityMeasurementApp.enums.LengthUnit;
import com.quantity.QuantityMeasurementApp.model.Quantity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
public class LengthTest {

    @Test
    void sameFeetShouldBeEqual() {
        Quantity l1 = new Quantity(2, LengthUnit.FEET);
        Quantity l2 = new Quantity(2, LengthUnit.FEET);

        Assertions.assertEquals(l1, l2);
    }

    @Test
    void differentFeetShouldNotBeEqual() {
        Quantity l1 = new Quantity(2, LengthUnit.FEET);
        Quantity l2 = new Quantity(3, LengthUnit.FEET);

        Assertions.assertNotEquals(l1, l2);
    }

    @Test
    void oneFeetShouldEqualTwelveInch() {
        Quantity feet = new Quantity(1, LengthUnit.FEET);
        Quantity inch = new Quantity(12, LengthUnit.INCH);

        Assertions.assertEquals(feet, inch);
    }

    @Test
    void oneFeetShouldNotEqualElevenInch() {
        Quantity feet = new Quantity(1, LengthUnit.FEET);
        Quantity inch = new Quantity(11, LengthUnit.INCH);

        Assertions.assertNotEquals(feet, inch);
    }

    @Test
    void zeroLengthShouldBeEqual() {
        Quantity l1 = new Quantity(0, LengthUnit.FEET);
        Quantity l2 = new Quantity(0, LengthUnit.INCH);

        Assertions.assertEquals(l1, l2);
    }

    @Test
    void yardToFeetEquality() {
        Quantity yard = new Quantity(1, LengthUnit.YARDS);
        Quantity feet = new Quantity(3, LengthUnit.FEET);

        Assertions.assertEquals(yard, feet);
    }

    @Test
    void yardToInchEquality() {
        Quantity yard = new Quantity(1, LengthUnit.YARDS);
        Quantity inch = new Quantity(36, LengthUnit.INCH);

        Assertions.assertEquals(yard, inch);
    }

    @Test
    void cmToInchEquality() {
        Quantity cm = new Quantity(1, LengthUnit.CENTIMETERS);
        Quantity inch = new Quantity(0.393701, LengthUnit.INCH);

        Assertions.assertEquals(cm, inch);
    }

    @Test
    void convertFeetToInch() {
        Quantity feet = new Quantity(1, LengthUnit.FEET);
        Quantity result = feet.convertTo(LengthUnit.INCH);

        Assertions.assertEquals(new Quantity(12, LengthUnit.INCH), result);
    }

    @Test
    void convertYardToFeet() {
        Quantity yard = new Quantity(1, LengthUnit.YARDS);
        Quantity result = yard.convertTo(LengthUnit.FEET);

        Assertions.assertEquals(new Quantity(3, LengthUnit.FEET), result);
    }

    @Test
    void instanceConversion() {
        Quantity yard = new Quantity(1, LengthUnit.YARDS);
        Quantity feet = yard.convertTo(LengthUnit.FEET);

        Assertions.assertEquals(new Quantity(3, LengthUnit.FEET), feet);
    }

    @Test
    void additionFeetPlusFeet() {
        Quantity l1 = new Quantity(1, LengthUnit.FEET);
        Quantity l2 = new Quantity(2, LengthUnit.FEET);

        Assertions.assertEquals(new Quantity(3, LengthUnit.FEET), l1.add(l2));
    }

    @Test
    void additionFeetPlusInch() {
        Quantity feet = new Quantity(1, LengthUnit.FEET);
        Quantity inch = new Quantity(12, LengthUnit.INCH);

        Assertions.assertEquals(new Quantity(2, LengthUnit.FEET), feet.add(inch));
    }

    @Test
    void additionInchPlusFeet() {
        Quantity inch = new Quantity(12, LengthUnit.INCH);
        Quantity feet = new Quantity(1, LengthUnit.FEET);

        Assertions.assertEquals(new Quantity(24, LengthUnit.INCH), inch.add(feet));
    }

    @Test
    void additionYardPlusFeet() {
        Quantity yard = new Quantity(1, LengthUnit.YARDS);
        Quantity feet = new Quantity(3, LengthUnit.FEET);

        Assertions.assertEquals(new Quantity(2, LengthUnit.YARDS), yard.add(feet));
    }

    @Test
    void additionWithZero() {
        Quantity l1 = new Quantity(5, LengthUnit.FEET);
        Quantity zero = new Quantity(0, LengthUnit.INCH);

        Assertions.assertEquals(l1, l1.add(zero));
    }

    @Test
    void additionNegativeValues() {
        Quantity l1 = new Quantity(5, LengthUnit.FEET);
        Quantity l2 = new Quantity(-2, LengthUnit.FEET);

        Assertions.assertEquals(new Quantity(3, LengthUnit.FEET), l1.add(l2));
    }

    @Test
    void additionCommutativeCheck() {
        Quantity a = new Quantity(1, LengthUnit.FEET);
        Quantity b = new Quantity(12, LengthUnit.INCH);

        Assertions.assertEquals(
                a.add(b).toBaseUnit(),
                b.add(a).toBaseUnit(),
                0.0001
        );
    }

}