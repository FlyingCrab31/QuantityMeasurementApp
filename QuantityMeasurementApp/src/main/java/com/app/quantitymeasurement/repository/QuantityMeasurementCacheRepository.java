package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class QuantityMeasurementCacheRepository implements IQuantityMeasurementRepository {
    private static QuantityMeasurementCacheRepository instance;
    private final List<QuantityMeasurementEntity> cache = new CopyOnWriteArrayList<>();

    private QuantityMeasurementCacheRepository() {
    }

    public static synchronized QuantityMeasurementCacheRepository getInstance() {
        if (instance == null) {
            instance = new QuantityMeasurementCacheRepository();
        }
        return instance;
    }

    @Override
    public void save(QuantityMeasurementEntity entity) {
        cache.add(entity);
    }

    @Override
    public List<QuantityMeasurementEntity> getAllMeasurements() {
        return new ArrayList<>(cache);
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation) {
        if (operation == null) {
            return new ArrayList<>();
        }
        return cache.stream()
                .filter(entity -> operation.equalsIgnoreCase(entity.getOperation()))
                .collect(Collectors.toList());
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByType(String measurementType) {
        if (measurementType == null) {
            return new ArrayList<>();
        }
        return cache.stream()
                .filter(entity -> measurementType.equalsIgnoreCase(entity.getMeasurementType()))
                .collect(Collectors.toList());
    }

    @Override
    public long getTotalCount() {
        return cache.size();
    }

    @Override
    public void deleteAll() {
        cache.clear();
    }

    @Override
    public String getPoolStatistics() {
        return "CacheRepository{size=" + cache.size() + "}";
    }
}
