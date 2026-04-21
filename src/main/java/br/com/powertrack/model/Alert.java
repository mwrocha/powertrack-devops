package br.com.powertrack.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;

@Document(collection = "alerts")
public class Alert {

    @Id
    private String id; // MongoDB  String

    private String equipmentId; // referência ao Equipment (id do equipamento)

    private String alertType;
    private String alertMessage;
    private OffsetDateTime alertTimestamp;

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public String getAlertMessage() {
        return alertMessage;
    }

    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
    }

    public OffsetDateTime getAlertTimestamp() {
        return alertTimestamp;
    }

    public void setAlertTimestamp(OffsetDateTime alertTimestamp) {
        this.alertTimestamp = alertTimestamp;
    }
}