package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.app.quantitymeasurement.services.IQuantityMeasurementService;
import com.app.quantitymeasurement.services.QuantityMeasurementServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementServiceTest {
    private IQuantityMeasurementService service;
    private IQuantityMeasurementRepository repository;

    @BeforeEach
    void setUp() {
        repository = QuantityMeasurementCacheRepository.getInstance();
        repository.deleteAll();
        service = new QuantityMeasurementServiceImpl(repository);
    }

    @Test
    void addDifferentUnitsSameType() {
        QuantityDTO q1 = new QuantityDTO(1, "FEET");
        QuantityDTO q2 = new QuantityDTO(12, "INCHES");

        QuantityDTO result = service.add(q1, q2);

        assertEquals(2.0, result.getValue(), 0.0001);
        assertEquals("FEET", result.getUnit());
        assertEquals(1, repository.getTotalCount());
    }

    @Test
    void compareStoresMeasurement() {
        QuantityDTO q1 = new QuantityDTO(10, "FEET");
        QuantityDTO q2 = new QuantityDTO(120, "INCHES");

        boolean result = service.compare(q1, q2);

        assertTrue(result);
        assertEquals(1, repository.getTotalCount());
    }
}
