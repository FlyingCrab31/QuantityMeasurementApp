package com.bridgelabz.QuantityMeasurementApp.controller;
import com.bridgelabz.quantityMeasurementApp.controller.QuantityMeasurementController;
import com.bridgelabz.quantityMeasurementApp.dto.QuantityDTO;
import com.bridgelabz.quantityMeasurementApp.repository.QuantityMeasurementCacheRepository;
import com.bridgelabz.quantityMeasurementApp.service.IQuantityMeasurementService;
import com.bridgelabz.quantityMeasurementApp.service.QuantityMeasurementServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class QuantityMeasurementControllerTest {
    private QuantityMeasurementController controller;

    @BeforeEach
    void setup() {
        IQuantityMeasurementService service =
                new QuantityMeasurementServiceImpl(
                        QuantityMeasurementCacheRepository.getInstance());

        controller = new QuantityMeasurementController(service);
    }

    @Test
    void givenTwoQuantities_WhenCompared_ShouldReturnTrue() {

        QuantityDTO q1 = new QuantityDTO(10,"FEET");
        QuantityDTO q2 = new QuantityDTO(120,"INCHES");

        boolean result = controller.performComparison(q1,q2);

        assertTrue(result);
    }
}
