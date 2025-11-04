package br.com.powertrack.service;

import br.com.powertrack.model.MeterReading;
import br.com.powertrack.repository.MeterReadingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeterReadingService {
    private final MeterReadingRepository repo;

    public MeterReadingService(MeterReadingRepository repo) { this.repo = repo; }

    public MeterReading create(MeterReading reading) {
        return repo.save(reading);
    }

    public List<MeterReading> listAll() { return repo.findAll(); }

    public List<MeterReading> findByEquipment(Long equipmentId) {
        return repo.findByEquipmentId(equipmentId);
    }
}
