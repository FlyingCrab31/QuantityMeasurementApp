package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.services.IQuantityMeasurementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class QuantityMeasurementController {
    private static final Logger logger = LoggerFactory.getLogger(QuantityMeasurementController.class);

    private final IQuantityMeasurementService service;

    public QuantityMeasurementController(IQuantityMeasurementService service) {
        this.service = service;
        logger.info("QuantityMeasurementController initialized");
    }

    public boolean performComparison(QuantityDTO q1, QuantityDTO q2) {
        return service.compare(q1, q2);
    }

    public QuantityDTO performAddition(QuantityDTO q1, QuantityDTO q2) {
        return service.add(q1, q2);
    }

    public QuantityDTO performSubtraction(QuantityDTO q1, QuantityDTO q2) {
        return service.subtract(q1, q2);
    }

    public double performDivision(QuantityDTO q1, QuantityDTO q2) {
        return service.divide(q1, q2);
    }

    public QuantityDTO convert(QuantityDTO input, String targetUnit) {
        return service.convert(input, targetUnit);
    }

    public List<QuantityMeasurementEntity> getAllMeasurements() {
        return service.getAllMeasurements();
    }

    public List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation) {
        return service.getMeasurementsByOperation(operation);
    }

    public List<QuantityMeasurementEntity> getMeasurementsByType(String measurementType) {
        return service.getMeasurementsByType(measurementType);
    }

    public long getTotalCount() {
        return service.getTotalCount();
    }

    public void deleteAllMeasurements() {
        service.deleteAllMeasurements();
    }

    public String getPoolStatistics() {
        return service.getPoolStatistics();
    }

    public void releaseResources() {
        service.releaseResources();
    }
}
