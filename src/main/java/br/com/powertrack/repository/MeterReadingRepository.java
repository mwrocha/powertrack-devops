package br.com.powertrack.repository;

import br.com.powertrack.model.MeterReading;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeterReadingRepository extends MongoRepository<MeterReading, String> {

    // Busca por equipamento
    List<MeterReading> findByEquipmentId(String equipmentId);

    // Opcional: busca por medidor
    List<MeterReading> findByMeterId(String meterId);

}