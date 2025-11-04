package br.com.powertrack.controller;

import br.com.powertrack.model.Equipment;
import br.com.powertrack.service.EquipmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {
    private final EquipmentService service;
    public EquipmentController(EquipmentService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<List<Equipment>> list() {
        return ResponseEntity.ok(service.listAll());
    }

    @PostMapping
    public ResponseEntity<Equipment> create(@RequestBody Equipment eq) {
        Equipment created = service.create(eq);
        return ResponseEntity.status(201).body(created);
    }
}
