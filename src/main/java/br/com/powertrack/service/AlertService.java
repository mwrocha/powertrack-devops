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

    public Alert create(Alert a) {
        // Garante que o timestamp será preenchido
        if (a.getAlertTimestamp() == null) {
            a.setAlertTimestamp(OffsetDateTime.now());
        }
        return repo.save(a);
    }

    public List<Alert> listAll() {
        return repo.findAll();
    }


}
