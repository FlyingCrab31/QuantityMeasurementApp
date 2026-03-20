package com.app.quantitymeasurement.integrationTests;

import com.app.quantitymeasurement.controller.QuantityMeasurementController;
import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurement.repository.QuantityMeasurementDatabaseRepository;
import com.app.quantitymeasurement.services.IQuantityMeasurementService;
import com.app.quantitymeasurement.services.QuantityMeasurementServiceImpl;
import com.app.quantitymeasurement.util.ApplicationConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementIntegrationTest {
    private IQuantityMeasurementRepository repository;
    private QuantityMeasurementController controller;

    @BeforeAll
    static void setupEnv() {
        System.setProperty("app.env", "test");
    }

    @BeforeEach
    void setUp() {
        repository = new QuantityMeasurementDatabaseRepository(ApplicationConfig.getInstance());
        repository.deleteAll();
        IQuantityMeasurementService service = new QuantityMeasurementServiceImpl(repository);
        controller = new QuantityMeasurementController(service);
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
        repository.releaseResources();
    }

    @Test
    void endToEndDatabasePersistence() {
        QuantityDTO q1 = new QuantityDTO(10, "FEET");
        QuantityDTO q2 = new QuantityDTO(120, "INCHES");

        assertTrue(controller.performComparison(q1, q2));
        controller.performAddition(q1, q2);

        assertEquals(2, controller.getTotalCount());
        assertEquals(2, controller.getMeasurementsByOperation("ADD").size() + controller.getMeasurementsByOperation("COMPARE").size());
        assertEquals(2, controller.getMeasurementsByType("LENGTH").size());
    }
}
