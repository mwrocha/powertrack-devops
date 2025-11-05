package br.com.powertrack.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "ALERTS")
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ALERT_ID")
    private Long alertId;

    @ManyToOne
    @JoinColumn(name = "EQUIPMENT_ID")
    private Equipment equipment;

    @Column(name = "ALERT_TYPE")
    private String alertType;

    @Column(name = "ALERT_MESSAGE")
    private String alertMessage;

    @Column(name = "ALERT_TIMESTAMP")
    private OffsetDateTime alertTimestamp;

    // Getters e Setters
    public Long getAlertId() {
        return alertId;
    }

    public void setAlertId(Long alertId) {
        this.alertId = alertId;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
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
