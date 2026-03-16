package com.bridgelabz.quantityMeasurementApp.repository;
import com.bridgelabz.quantityMeasurementApp.entity.QuantityMeasurementEntity;
import java.util.List;
public interface IQuantityMeasurementRepository {
    void save(QuantityMeasurementEntity entity);

    List<QuantityMeasurementEntity> findAll();
}
