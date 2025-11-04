package br.com.powertrack.service;

import br.com.powertrack.model.Equipment;
import br.com.powertrack.repository.EquipmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentService {
    private final EquipmentRepository repo;
    public EquipmentService(EquipmentRepository repo) { this.repo = repo; }

    public List<Equipment> listAll() { return repo.findAll(); }
    public Equipment create(Equipment e) { return repo.save(e); }
    public Equipment findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Equipment not found: " + id));
    }
}
