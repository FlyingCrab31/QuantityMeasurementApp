package com.quantity.QuantityMeasurementApp;

import com.quantity.QuantityMeasurementApp.enums.WeightUnit;
import com.quantity.QuantityMeasurementApp.model.Quantity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WeightTest {

    @Test
    void oneKgShouldEqualThousandGram() {

        Quantity<WeightUnit> kg = new Quantity<>(1, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> gram = new Quantity<>(1000, WeightUnit.GRAM);

        Assertions.assertEquals(kg, gram);
    }

    @Test
    void oneKgShouldEqualTwoPointTwoPound() {

        Quantity<WeightUnit> kg = new Quantity<>(1, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> pound = new Quantity<>(2.20462, WeightUnit.POUND);

        Assertions.assertEquals(kg, pound);
    }

    @Test
    void additionKgAndGram() {

        Quantity<WeightUnit> kg = new Quantity<>(1, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> g = new Quantity<>(1000, WeightUnit.GRAM);

        Assertions.assertEquals(
                new Quantity<>(2, WeightUnit.KILOGRAM),
                kg.add(g)
        );
    }
}