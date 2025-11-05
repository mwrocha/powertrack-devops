package br.com.powertrack.controller;

import br.com.powertrack.model.Alert;
import br.com.powertrack.model.Equipment;
import br.com.powertrack.repository.EquipmentRepository;
import br.com.powertrack.service.AlertService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertService service;
    private final EquipmentRepository equipmentRepo;

    public AlertController(AlertService service, EquipmentRepository equipmentRepo) {
        this.service = service;
        this.equipmentRepo = equipmentRepo;
    }

    @GetMapping
    public ResponseEntity<List<Alert>> list() {
        return ResponseEntity.ok(service.listAll());
    }

    @PostMapping
    @PreAuthorize("hasRole('OPERATOR')")
    public ResponseEntity<?> create(@RequestBody Map<String, Object> payload) {
        Long equipmentId = Long.valueOf(payload.get("equipmentId").toString());
        String message = payload.get("message").toString();
        String type = payload.containsKey("type") ? payload.get("type").toString() : "INFO";

        Equipment eq = equipmentRepo.findById(equipmentId)
                .orElseThrow(() -> new RuntimeException("Equipment not found: " + equipmentId));

        Alert alert = new Alert();
        alert.setEquipment(eq);
        alert.setAlertMessage(message);
        alert.setAlertType(type);
        alert.setAlertTimestamp(OffsetDateTime.now());

        Alert created = service.create(alert);
        return ResponseEntity.status(201).body(created);
    }
}
