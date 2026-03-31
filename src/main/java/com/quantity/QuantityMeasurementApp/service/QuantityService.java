package com.quantity.QuantityMeasurementApp.service;

import com.quantity.QuantityMeasurementApp.dto.QuantityDTO;
import com.quantity.QuantityMeasurementApp.entity.QuantityMeasurementEntity;
import com.quantity.QuantityMeasurementApp.enums.*;
import com.quantity.QuantityMeasurementApp.model.Quantity;
import com.quantity.QuantityMeasurementApp.model.Unit;
import com.quantity.QuantityMeasurementApp.repository.QuantityRepository;
import org.springframework.stereotype.Service;

@Service
public class QuantityService {

    private final QuantityRepository repository;

    public QuantityService(QuantityRepository repository) {
        this.repository = repository;
    }

    // ✅ String → Actual Enum Unit
    private Unit toUnit(String unitName) {
        try { return LengthUnit.valueOf(unitName); } catch (Exception ignored) {}
        try { return WeightUnit.valueOf(unitName); } catch (Exception ignored) {}
        try { return VolumeUnit.valueOf(unitName); } catch (Exception ignored) {}
        try { return TemperatureUnit.valueOf(unitName); } catch (Exception ignored) {}
        throw new IllegalArgumentException("Invalid unit: " + unitName);
    }

    public QuantityDTO add(QuantityDTO q1, QuantityDTO q2) {

        Unit u1 = toUnit(q1.getUnit());
        Unit u2 = toUnit(q2.getUnit());

        Quantity<Unit> quantity1 = new Quantity<>(q1.getValue(), u1);
        Quantity<Unit> quantity2 = new Quantity<>(q2.getValue(), u2);

        Quantity<Unit> result = quantity1.add(quantity2);

        repository.save(new QuantityMeasurementEntity(
                null,
                q1.getValue(),
                q2.getValue(),
                result.getValue(),
                "ADD"
        ));

        return new QuantityDTO(result.getValue(), result.getUnit().toString());
    }

    public boolean compare(QuantityDTO q1, QuantityDTO q2) {

        Unit u1 = toUnit(q1.getUnit());
        Unit u2 = toUnit(q2.getUnit());

        Quantity<Unit> quantity1 = new Quantity<>(q1.getValue(), u1);
        Quantity<Unit> quantity2 = new Quantity<>(q2.getValue(), u2);

        return quantity1.equals(quantity2);
    }

    public QuantityDTO convert(QuantityDTO q, String targetUnit) {

        Unit source = toUnit(q.getUnit());
        Unit target = toUnit(targetUnit);

        Quantity<Unit> quantity = new Quantity<>(q.getValue(), source);
        Quantity<Unit> converted = quantity.convertTo(target);

        return new QuantityDTO(converted.getValue(), converted.getUnit().toString());
    }
}