package com.app.quantitymeasurement.services;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.exception.QuantityMeasurementException;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurement.unit.IMeasurable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {
    private static final Logger logger = LoggerFactory.getLogger(QuantityMeasurementServiceImpl.class);

    private final IQuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repository) {
        this.repository = repository;
        logger.info("QuantityMeasurementService initialized with {}", repository.getClass().getSimpleName());
    }

    @Override
    public QuantityDTO convert(QuantityDTO input, String targetUnit) {
        IMeasurable fromUnit = IMeasurable.getUnitByName(input.getUnit());
        IMeasurable toUnit = IMeasurable.getUnitByName(targetUnit);
        validateMeasurementType(fromUnit, toUnit);

        double baseValue = fromUnit.convertToBaseUnit(input.getValue());
        double converted = toUnit.convertFromBaseUnit(baseValue);
        return new QuantityDTO(converted, targetUnit);
    }

    @Override
    public boolean compare(QuantityDTO q1, QuantityDTO q2) {
        IMeasurable unit1 = IMeasurable.getUnitByName(q1.getUnit());
        IMeasurable unit2 = IMeasurable.getUnitByName(q2.getUnit());
        validateMeasurementType(unit1, unit2);

        double base1 = unit1.convertToBaseUnit(q1.getValue());
        double base2 = unit2.convertToBaseUnit(q2.getValue());
        boolean result = Double.compare(base1, base2) == 0;

        repository.save(buildEntity(q1, q2, "COMPARE", result ? 1 : 0, unit1.getMeasurementType()));
        return result;
    }

    @Override
    public QuantityDTO add(QuantityDTO q1, QuantityDTO q2) {
        IMeasurable unit1 = IMeasurable.getUnitByName(q1.getUnit());
        IMeasurable unit2 = IMeasurable.getUnitByName(q2.getUnit());
        validateMeasurementType(unit1, unit2);
        validateArithmetic(unit1, unit2);

        double base1 = unit1.convertToBaseUnit(q1.getValue());
        double base2 = unit2.convertToBaseUnit(q2.getValue());
        double baseSum = base1 + base2;
        double result = unit1.convertFromBaseUnit(baseSum);

        repository.save(buildEntity(q1, q2, "ADD", result, unit1.getMeasurementType()));
        return new QuantityDTO(result, q1.getUnit());
    }

    @Override
    public QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2) {
        IMeasurable unit1 = IMeasurable.getUnitByName(q1.getUnit());
        IMeasurable unit2 = IMeasurable.getUnitByName(q2.getUnit());
        validateMeasurementType(unit1, unit2);
        validateArithmetic(unit1, unit2);

        double base1 = unit1.convertToBaseUnit(q1.getValue());
        double base2 = unit2.convertToBaseUnit(q2.getValue());
        double baseDiff = base1 - base2;
        double result = unit1.convertFromBaseUnit(baseDiff);

        repository.save(buildEntity(q1, q2, "SUBTRACT", result, unit1.getMeasurementType()));
        return new QuantityDTO(result, q1.getUnit());
    }

    @Override
    public double divide(QuantityDTO q1, QuantityDTO q2) {
        IMeasurable unit1 = IMeasurable.getUnitByName(q1.getUnit());
        IMeasurable unit2 = IMeasurable.getUnitByName(q2.getUnit());
        validateMeasurementType(unit1, unit2);
        validateArithmetic(unit1, unit2);

        double base1 = unit1.convertToBaseUnit(q1.getValue());
        double base2 = unit2.convertToBaseUnit(q2.getValue());
        if (base2 == 0) {
            throw new QuantityMeasurementException("Division by zero");
        }
        double result = base1 / base2;
        repository.save(buildEntity(q1, q2, "DIVIDE", result, unit1.getMeasurementType()));
        return result;
    }

    @Override
    public List<QuantityMeasurementEntity> getAllMeasurements() {
        return repository.getAllMeasurements();
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation) {
        return repository.getMeasurementsByOperation(operation);
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByType(String measurementType) {
        return repository.getMeasurementsByType(measurementType);
    }

    @Override
    public long getTotalCount() {
        return repository.getTotalCount();
    }

    @Override
    public void deleteAllMeasurements() {
        repository.deleteAll();
    }

    @Override
    public String getPoolStatistics() {
        return repository.getPoolStatistics();
    }

    @Override
    public void releaseResources() {
        repository.releaseResources();
    }

    private void validateMeasurementType(IMeasurable unit1, IMeasurable unit2) {
        if (!unit1.getMeasurementType().equals(unit2.getMeasurementType())) {
            throw new QuantityMeasurementException("Units mismatch");
        }
    }

    private void validateArithmetic(IMeasurable unit1, IMeasurable unit2) {
        if (!unit1.supportsArithmetic() || !unit2.supportsArithmetic()) {
            throw new QuantityMeasurementException("Arithmetic not supported for these units");
        }
    }

    private QuantityMeasurementEntity buildEntity(QuantityDTO q1, QuantityDTO q2, String operation, double result, String measurementType) {
        return new QuantityMeasurementEntity(
                q1.getValue(),
                q2.getValue(),
                q1.getUnit(),
                q2.getUnit(),
                operation,
                result,
                measurementType
        );
    }
}
