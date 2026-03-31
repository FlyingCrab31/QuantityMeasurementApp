package com.quantity.QuantityMeasurementApp.repository;

import com.quantity.QuantityMeasurementApp.entity.QuantityMeasurementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuantityRepository
        extends JpaRepository<QuantityMeasurementEntity, Long> {
}