package br.com.powertrack.controller;

import br.com.powertrack.model.EnergyMeter;
import br.com.powertrack.model.Equipment;
import br.com.powertrack.model.MeterReading;
import br.com.powertrack.repository.EnergyMeterRepository;
import br.com.powertrack.repository.EquipmentRepository;
import br.com.powertrack.service.MeterReadingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/meter-readings")
public class MeterReadingController {

    private final MeterReadingService service;
    private final EquipmentRepository equipmentRepo;
    private final EnergyMeterRepository meterRepo;

    public MeterReadingController(MeterReadingService service,
                                  EquipmentRepository equipmentRepo,
                                  EnergyMeterRepository meterRepo) {
        this.service = service;
        this.equipmentRepo = equipmentRepo;
        this.meterRepo = meterRepo;
    }

    @GetMapping
    public ResponseEntity<List<MeterReading>> list() {
        return ResponseEntity.ok(service.listAll());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> create(@Valid @RequestBody Map<String, Object> payload) {

        // Agora ID é String (Mongo ObjectId)
        String meterId = payload.get("meterId").toString();
        String equipmentId = payload.get("equipmentId").toString();
        Double energyKwh = Double.valueOf(payload.get("energyKwh").toString());

        EnergyMeter meter = meterRepo.findById(meterId)
                .orElseThrow(() -> new RuntimeException("Meter not found: " + meterId));

        Equipment eq = equipmentRepo.findById(equipmentId)
                .orElseThrow(() -> new RuntimeException("Equipment not found: " + equipmentId));

        MeterReading mr = new MeterReading();
        mr.setMeterId(meter.getId());
        mr.setEquipmentId(eq.getId());
        mr.setEnergyKwh(energyKwh);
        mr.setReadingTimestamp(OffsetDateTime.now());
        mr.setSource("MANUAL"); // opcional (bom pro trabalho)

        MeterReading created = service.create(mr);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping("/by-equipment/{equipmentId}")
    public ResponseEntity<List<MeterReading>> byEquipment(@PathVariable String equipmentId) {
        return ResponseEntity.ok(service.findByEquipment(equipmentId));
    }
}