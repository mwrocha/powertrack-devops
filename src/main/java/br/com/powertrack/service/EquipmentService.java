package br.com.powertrack.service;

import br.com.powertrack.model.Equipment;
import br.com.powertrack.repository.EquipmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentService {

    private final EquipmentRepository repo;

    public EquipmentService(EquipmentRepository repo) {
        this.repo = repo;
    }

    public List<Equipment> listAll() {
        return repo.findAll();
    }

    public Equipment create(Equipment equipment) {
        return repo.save(equipment);
    }

    public Equipment findById(String id) { // 🔥 era Long
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado: " + id));
    }

    public void delete(String id) {
        repo.deleteById(id);
    }
}