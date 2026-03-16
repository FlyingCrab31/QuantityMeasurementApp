package com.bridgelabz.quantityMeasurementApp.service;
import com.bridgelabz.quantityMeasurementApp.dto.QuantityDTO;
import com.bridgelabz.quantityMeasurementApp.repository.IQuantityMeasurementRepository;
import com.bridgelabz.quantityMeasurementApp.entity.QuantityMeasurementEntity;
import com.bridgelabz.quantityMeasurementApp.generic_quantity.IMeasurable;
import com.bridgelabz.quantityMeasurementApp.exception.QuantityMeasurementException;

import java.util.List;
import java.util.Optional;

public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {
    private IQuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repository) {
        this.repository = repository;
    }
    @Override
    public QuantityDTO convert(QuantityDTO input, String targetUnit) {
        return null;
    }
    @Override
    public boolean compare(QuantityDTO q1, QuantityDTO q2) {

        IMeasurable unit1 = IMeasurable.getUnitByName(q1.getUnit());
        IMeasurable unit2 = IMeasurable.getUnitByName(q2.getUnit());

        if (!unit1.getMeasurementType().equals(unit2.getMeasurementType())) {
            throw new QuantityMeasurementException("Units mismatch");
        }

        double base1 = unit1.convertToBaseUnit(q1.getValue());
        double base2 = unit2.convertToBaseUnit(q2.getValue());

        return Double.compare(base1, base2) == 0;
    }
    @Override
    public QuantityDTO add(QuantityDTO q1, QuantityDTO q2) {
        double result = q1.getValue() + q2.getValue();

        repository.save(
                new QuantityMeasurementEntity(
                        q1.getValue(),
                        q2.getValue(),
                        "ADD",
                        result
                )
        );

        return new QuantityDTO(result, q1.getUnit());
    }
    @Override
    public QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2) {
        return null;
    }

    @Override
    public double divide(QuantityDTO q1, QuantityDTO q2) {
        return 0;
    }

}
