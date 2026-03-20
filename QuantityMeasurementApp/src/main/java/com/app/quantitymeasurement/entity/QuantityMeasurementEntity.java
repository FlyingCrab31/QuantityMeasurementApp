package com.app.quantitymeasurement.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class QuantityMeasurementEntity implements Serializable {
    private final Long id;
    private final double operand1;
    private final double operand2;
    private final String unit1;
    private final String unit2;
    private final String operation;
    private final double result;
    private final String measurementType;
    private final Timestamp createdAt;

    public QuantityMeasurementEntity(double operand1,
                                     double operand2,
                                     String unit1,
                                     String unit2,
                                     String operation,
                                     double result,
                                     String measurementType) {
        this(null, operand1, operand2, unit1, unit2, operation, result, measurementType, null);
    }

    public QuantityMeasurementEntity(Long id,
                                     double operand1,
                                     double operand2,
                                     String unit1,
                                     String unit2,
                                     String operation,
                                     double result,
                                     String measurementType,
                                     Timestamp createdAt) {
        this.id = id;
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.unit1 = unit1;
        this.unit2 = unit2;
        this.operation = operation;
        this.result = result;
        this.measurementType = measurementType;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public double getOperand1() {
        return operand1;
    }

    public double getOperand2() {
        return operand2;
    }

    public String getUnit1() {
        return unit1;
    }

    public String getUnit2() {
        return unit2;
    }

    public String getOperation() {
        return operation;
    }

    public double getResult() {
        return result;
    }

    public String getMeasurementType() {
        return measurementType;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
}
