package com.bridgelabz.QuantityMeasurementApp.repository;

import com.bridgelabz.quantityMeasurementApp.entity.QuantityMeasurementEntity;
import com.bridgelabz.quantityMeasurementApp.repository.QuantityMeasurementCacheRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementCacheRepositoryTest {
    @Test
    void givenEntity_WhenSaved_ShouldStoreInRepository() {

        QuantityMeasurementCacheRepository repo =
                QuantityMeasurementCacheRepository.getInstance();

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(10,20,"ADD",30);

        repo.save(entity);

        assertFalse(repo.findAll().isEmpty());
    }
}
