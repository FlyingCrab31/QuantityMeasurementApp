package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;

import java.util.List;

public interface IQuantityMeasurementRepository {
    void save(QuantityMeasurementEntity entity);

    List<QuantityMeasurementEntity> getAllMeasurements();

    List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation);

    List<QuantityMeasurementEntity> getMeasurementsByType(String measurementType);

    long getTotalCount();

    void deleteAll();

    default String getPoolStatistics() {
        return "Pool statistics not available";
    }

    default void releaseResources() {
        // No-op by default.
    }
}
