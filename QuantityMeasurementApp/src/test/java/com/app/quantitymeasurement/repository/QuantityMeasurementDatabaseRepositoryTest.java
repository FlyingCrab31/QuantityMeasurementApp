package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.util.ApplicationConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementDatabaseRepositoryTest {
    private QuantityMeasurementDatabaseRepository repository;

    @BeforeAll
    static void setupEnv() {
        System.setProperty("app.env", "test");
    }

    @BeforeEach
    void setUp() {
        repository = new QuantityMeasurementDatabaseRepository(ApplicationConfig.getInstance());
        repository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
        repository.releaseResources();
    }

    @Test
    void saveAndRetrieveMeasurements() {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
                10,
                20,
                "FEET",
                "FEET",
                "ADD",
                30,
                "LENGTH"
        );

        repository.save(entity);

        List<QuantityMeasurementEntity> all = repository.getAllMeasurements();
        assertEquals(1, all.size());
        assertEquals("ADD", all.get(0).getOperation());
    }

    @Test
    void queryByOperationAndType() {
        repository.save(new QuantityMeasurementEntity(10, 20, "FEET", "FEET", "ADD", 30, "LENGTH"));
        repository.save(new QuantityMeasurementEntity(5, 5, "LITRE", "LITRE", "ADD", 10, "VOLUME"));

        List<QuantityMeasurementEntity> addOps = repository.getMeasurementsByOperation("ADD");
        List<QuantityMeasurementEntity> lengthOps = repository.getMeasurementsByType("LENGTH");

        assertEquals(2, addOps.size());
        assertEquals(1, lengthOps.size());
        assertEquals("LENGTH", lengthOps.get(0).getMeasurementType());
    }

    @Test
    void countAndDeleteMeasurements() {
        repository.save(new QuantityMeasurementEntity(1, 1, "GRAM", "GRAM", "ADD", 2, "WEIGHT"));
        repository.save(new QuantityMeasurementEntity(2, 1, "GRAM", "GRAM", "SUBTRACT", 1, "WEIGHT"));

        assertEquals(2, repository.getTotalCount());
        repository.deleteAll();
        assertEquals(0, repository.getTotalCount());
    }

    @Test
    void poolStatisticsAvailable() {
        String stats = repository.getPoolStatistics();
        assertNotNull(stats);
        assertTrue(stats.contains("PoolStats"));
    }
}
