package br.com.powertrack.service;

import br.com.powertrack.model.MeterReading;
import br.com.powertrack.repository.MeterReadingRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class MeterReadingService {

    private final MeterReadingRepository repo;

    public MeterReadingService(MeterReadingRepository repo) {
        this.repo = repo;
    }

    public MeterReading create(MeterReading reading) {

        // Garante timestamp caso não venha preenchido
        if (reading.getReadingTimestamp() == null) {
            reading.setReadingTimestamp(OffsetDateTime.now());
        }

        return repo.save(reading);
    }

    public List<MeterReading> listAll() {
        return repo.findAll();
    }

    public List<MeterReading> findByEquipment(String equipmentId) { // 🔥 era Long
        return repo.findByEquipmentId(equipmentId);
    }

    public void delete(String id) {
        repo.deleteById(id);
    }
}