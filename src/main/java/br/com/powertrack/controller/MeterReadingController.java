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
    @PreAuthorize("hasAnyRole('USER','OPERATOR')")
    public ResponseEntity<?> create(@Valid @RequestBody Map<String, Object> payload) {
        // Espera payload: { "meterId": 1, "equipmentId": 1, "energyKwh": 2.5 }
        Long meterId = Long.valueOf(payload.get("meterId").toString());
        Long equipmentId = Long.valueOf(payload.get("equipmentId").toString());
        Double energyKwh = Double.valueOf(payload.get("energyKwh").toString());

        EnergyMeter meter = meterRepo.findById(meterId)
                .orElseThrow(() -> new RuntimeException("Meter not found: " + meterId));
        Equipment eq = equipmentRepo.findById(equipmentId)
                .orElseThrow(() -> new RuntimeException("Equipment not found: " + equipmentId));

        MeterReading mr = new MeterReading();
        mr.setMeter(meter);
        mr.setEquipment(eq);
        mr.setEnergyKwh(energyKwh);
        mr.setReadingTimestamp(OffsetDateTime.now());

        MeterReading created = service.create(mr);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping("/by-equipment/{equipmentId}")
    public ResponseEntity<List<MeterReading>> byEquipment(@PathVariable Long equipmentId) {
        return ResponseEntity.ok(service.findByEquipment(equipmentId));
    }
}
