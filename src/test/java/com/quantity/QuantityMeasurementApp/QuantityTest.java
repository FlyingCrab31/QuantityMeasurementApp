package com.quantity.QuantityMeasurementApp;
import com.quantity.QuantityMeasurementApp.enums.LengthUnit;
import com.quantity.QuantityMeasurementApp.enums.WeightUnit;
import com.quantity.QuantityMeasurementApp.model.Quantity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
public class QuantityTest {
    @Test
    void oneFeetEqualsTwelveInch() {

        Quantity feet = new Quantity(1, LengthUnit.FEET);
        Quantity inch = new Quantity(12, LengthUnit.INCH);

        Assertions.assertEquals(feet, inch);
    }

    @Test
    void oneKgEqualsThousandGram() {

        Quantity kg = new Quantity(1, WeightUnit.KILOGRAM);
        Quantity gram = new Quantity(1000, WeightUnit.GRAM);

        Assertions.assertEquals(kg, gram);
    }
}
