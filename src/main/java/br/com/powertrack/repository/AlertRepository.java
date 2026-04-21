package br.com.powertrack.repository;

import br.com.powertrack.model.Alert;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AlertRepository extends MongoRepository<Alert, String> {

    // opcional (útil para seu projeto ESG)
    List<Alert> findByEquipmentId(String equipmentId);

    List<Alert> findByAlertType(String alertType);
}