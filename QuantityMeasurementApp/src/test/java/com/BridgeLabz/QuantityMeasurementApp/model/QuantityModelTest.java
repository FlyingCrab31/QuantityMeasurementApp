package com.bridgelabz.QuantityMeasurementApp.model;
import com.bridgelabz.quantityMeasurementApp.generic_quantity.LengthUnit;
import com.bridgelabz.quantityMeasurementApp.model.QuantityModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class QuantityModelTest {
    @Test
    void givenValueAndUnit_WhenCreated_ShouldStoreValues() {

        QuantityModel<LengthUnit> model =
                new QuantityModel<>(10, LengthUnit.FEET);

        assertEquals(10, model.getValue());
        assertEquals(LengthUnit.FEET, model.getUnit());
    }
}
