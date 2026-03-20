package com.app.quantitymeasurement.services;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;

import java.util.List;

public interface IQuantityMeasurementService {
    QuantityDTO convert(QuantityDTO input, String targetUnit);

    boolean compare(QuantityDTO q1, QuantityDTO q2);

    QuantityDTO add(QuantityDTO q1, QuantityDTO q2);

    QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2);

    double divide(QuantityDTO q1, QuantityDTO q2);

    List<QuantityMeasurementEntity> getAllMeasurements();

    List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation);

    List<QuantityMeasurementEntity> getMeasurementsByType(String measurementType);

    long getTotalCount();

    void deleteAllMeasurements();

    String getPoolStatistics();

    void releaseResources();
}
