--------------------------------------------------------------------------------
-- Flyway Migration V4 - Create Sequences in Current Schema
-- Autor: Mayke
-- Data: 2025-11-07
-- Descrição: Criação das sequences no schema atual da conexão
--------------------------------------------------------------------------------

-- Sequence para tabela EQUIPMENT
BEGIN
EXECUTE IMMEDIATE 'CREATE SEQUENCE equipment_seq START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE = -955 THEN
            NULL; -- sequence já existe
ELSE
            RAISE;
END IF;
END;
/
--------------------------------------------------------------------------------

-- Sequence para tabela ENERGY_METER
BEGIN
EXECUTE IMMEDIATE 'CREATE SEQUENCE energy_meter_seq START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE = -955 THEN
            NULL;
ELSE
            RAISE;
END IF;
END;
/
--------------------------------------------------------------------------------

-- Sequence para tabela METER_READING
BEGIN
EXECUTE IMMEDIATE 'CREATE SEQUENCE meter_reading_seq START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE = -955 THEN
            NULL;
ELSE
            RAISE;
END IF;
END;
/
--------------------------------------------------------------------------------

-- Sequence para tabela ALERT
BEGIN
EXECUTE IMMEDIATE 'CREATE SEQUENCE alert_seq START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE = -955 THEN
            NULL;
ELSE
            RAISE;
END IF;
END;
/
--------------------------------------------------------------------------------

-- Log visual no console
BEGIN
    DBMS_OUTPUT.PUT_LINE('✅ Sequences criadas (ou já existentes): equipment_seq, energy_meter_seq, meter_reading_seq, alert_seq');
END;
/
--------------------------------------------------------------------------------
