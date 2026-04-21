package br.com.powertrack.service;

import br.com.powertrack.model.Alert;
import br.com.powertrack.repository.AlertRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class AlertService {

    private final AlertRepository repo;

    public AlertService(AlertRepository repo) {
        this.repo = repo;
    }

    public Alert create(Alert alert) {

        // Garante que o timestamp sempre será preenchido
        if (alert.getAlertTimestamp() == null) {
            alert.setAlertTimestamp(OffsetDateTime.now());
        }

        return repo.save(alert);
    }

    public List<Alert> listAll() {
        return repo.findAll();
    }

    public List<Alert> findByEquipment(String equipmentId) {
        return repo.findByEquipmentId(equipmentId);
    }

    public void delete(String id) {
        repo.deleteById(id);
    }
}