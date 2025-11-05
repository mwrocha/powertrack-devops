package br.com.powertrack.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "METER_READING")
public class MeterReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "READING_ID")
    private Long readingId;

    @ManyToOne
    @JoinColumn(name = "METER_ID")
    private EnergyMeter meter;

    @ManyToOne
    @JoinColumn(name = "EQUIPMENT_ID")
    private Equipment equipment;

    @Column(name = "READING_TIMESTAMP")
    private OffsetDateTime readingTimestamp;

    @Column(name = "ENERGY_KWH")
    private Double energyKwh;

    @Column(name = "POWER_KW")
    private Double powerKw;

    // Getters e Setters
    public Long getReadingId() {
        return readingId;
    }

    public void setReadingId(Long readingId) {
        this.readingId = readingId;
    }

    public EnergyMeter getMeter() {
        return meter;
    }

    public void setMeter(EnergyMeter meter) {
        this.meter = meter;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public OffsetDateTime getReadingTimestamp() {
        return readingTimestamp;
    }

    public void setReadingTimestamp(OffsetDateTime readingTimestamp) {
        this.readingTimestamp = readingTimestamp;
    }

    public Double getEnergyKwh() {
        return energyKwh;
    }

    public void setEnergyKwh(Double energyKwh) {
        this.energyKwh = energyKwh;
    }

    public Double getPowerKw() {
        return powerKw;
    }

    public void setPowerKw(Double powerKw) {
        this.powerKw = powerKw;
    }
}
