package br.com.powertrack.service;

import br.com.powertrack.model.EnergyMeter;
import br.com.powertrack.repository.EnergyMeterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnergyMeterService {

    private final EnergyMeterRepository repository;

    public EnergyMeterService(EnergyMeterRepository repository) {
        this.repository = repository;
    }

    public List<EnergyMeter> getAll() {
        return repository.findAll();
    }

    public EnergyMeter getById(String id) { // 🔥 era Long
        return repository.findById(id).orElse(null);
    }

    public EnergyMeter create(EnergyMeter meter) {
        return repository.save(meter);
    }

    public EnergyMeter update(String id, EnergyMeter updated) { // 🔥 era Long
        EnergyMeter existing = getById(id);
        if (existing != null) {
            existing.setSerialNumber(updated.getSerialNumber());
            existing.setInstallationDate(updated.getInstallationDate());
            return repository.save(existing);
        }
        return null;
    }

    public void delete(String id) { // 🔥 era Long
        repository.deleteById(id);
    }
}