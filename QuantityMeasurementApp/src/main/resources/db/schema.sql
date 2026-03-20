-- Quantity Measurement schema

CREATE TABLE IF NOT EXISTS quantity_measurement_entity (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    operand1 DOUBLE NOT NULL,
    operand2 DOUBLE NOT NULL,
    unit1 VARCHAR(32) NOT NULL,
    unit2 VARCHAR(32) NOT NULL,
    operation VARCHAR(32) NOT NULL,
    result DOUBLE NOT NULL,
    measurement_type VARCHAR(32) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS quantity_measurement_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    entity_id BIGINT NOT NULL,
    operand1 DOUBLE NOT NULL,
    operand2 DOUBLE NOT NULL,
    unit1 VARCHAR(32) NOT NULL,
    unit2 VARCHAR(32) NOT NULL,
    operation VARCHAR(32) NOT NULL,
    result DOUBLE NOT NULL,
    measurement_type VARCHAR(32) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT fk_qm_history_entity FOREIGN KEY (entity_id)
        REFERENCES quantity_measurement_entity(id)
);

CREATE INDEX IF NOT EXISTS idx_qm_operation ON quantity_measurement_entity(operation);
CREATE INDEX IF NOT EXISTS idx_qm_measurement_type ON quantity_measurement_entity(measurement_type);
