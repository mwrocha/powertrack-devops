package br.com.powertrack.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "ENERGY_METER")
public class EnergyMeter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "SERIAL_NUMBER", nullable = false, unique = true)
    private String serialNumber;

    @Column(name = "INSTALLATION_DATE")
    private LocalDate installationDate;

    // getters / setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }
    public LocalDate getInstallationDate() { return installationDate; }
    public void setInstallationDate(LocalDate installationDate) { this.installationDate = installationDate; }
}
