package com.bridgelabz.QuantityMeasurementApp.service;

import com.bridgelabz.quantityMeasurementApp.dto.QuantityDTO;
import com.bridgelabz.quantityMeasurementApp.repository.QuantityMeasurementCacheRepository;
import com.bridgelabz.quantityMeasurementApp.service.IQuantityMeasurementService;
import com.bridgelabz.quantityMeasurementApp.service.QuantityMeasurementServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementServiceImplTest {
    private IQuantityMeasurementService service;

    @BeforeEach
    void setUp() {
        service = new QuantityMeasurementServiceImpl(
                QuantityMeasurementCacheRepository.getInstance()
        );
    }

    @Test
    void givenTwoEqualLengths_WhenCompared_ShouldReturnTrue() {
        QuantityDTO q1 = new QuantityDTO(10, "FEET");
        QuantityDTO q2 = new QuantityDTO(120, "INCH");

        boolean result = service.compare(q1, q2);

        assertTrue(result);
    }

    @Test
    void givenTwoLengths_WhenAdded_ShouldReturnResult() {
        QuantityDTO q1 = new QuantityDTO(10, "FEET");
        QuantityDTO q2 = new QuantityDTO(24, "INCH");

        QuantityDTO result = service.add(q1, q2);

        assertNotNull(result);
    }
}
