package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.app.quantitymeasurement.services.IQuantityMeasurementService;
import com.app.quantitymeasurement.services.QuantityMeasurementServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementControllerTest {
    private QuantityMeasurementController controller;

    @BeforeEach
    void setUp() {
        IQuantityMeasurementRepository repository = QuantityMeasurementCacheRepository.getInstance();
        repository.deleteAll();
        IQuantityMeasurementService service = new QuantityMeasurementServiceImpl(repository);
        controller = new QuantityMeasurementController(service);
    }

    @Test
    void performAdditionReturnsResult() {
        QuantityDTO q1 = new QuantityDTO(2, "FEET");
        QuantityDTO q2 = new QuantityDTO(24, "INCHES");

        QuantityDTO result = controller.performAddition(q1, q2);

        assertEquals(4.0, result.getValue(), 0.0001);
        assertEquals("FEET", result.getUnit());
    }

    @Test
    void performComparisonReturnsTrue() {
        QuantityDTO q1 = new QuantityDTO(1, "YARD");
        QuantityDTO q2 = new QuantityDTO(3, "FEET");

        assertTrue(controller.performComparison(q1, q2));
    }
}
