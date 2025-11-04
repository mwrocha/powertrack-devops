package br.com.powertrack.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "METER_READING")
public class MeterReading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "METER_ID")
    private EnergyMeter meter;

    @ManyToOne
    @JoinColumn(name = "EQUIPMENT_ID")
    private Equipment equipment;

    @Column(name = "READING_DATE")
    private OffsetDateTime readingDate;

    @Column(name = "VALUE_KWH")
    private Double valueKwh;

    // getters / setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public EnergyMeter getMeter() { return meter; }
    public void setMeter(EnergyMeter meter) { this.meter = meter; }
    public Equipment getEquipment() { return equipment; }
    public void setEquipment(Equipment equipment) { this.equipment = equipment; }
    public OffsetDateTime getReadingDate() { return readingDate; }
    public void setReadingDate(OffsetDateTime readingDate) { this.readingDate = readingDate; }
    public Double getValueKwh() { return valueKwh; }
    public void setValueKwh(Double valueKwh) { this.valueKwh = valueKwh; }
}
