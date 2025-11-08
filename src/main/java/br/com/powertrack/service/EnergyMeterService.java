package br.com.powertrack.service;

import br.com.powertrack.model.EnergyMeter;
import br.com.powertrack.repository.EnergyMeterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnergyMeterService {

    @Autowired
    private EnergyMeterRepository repository;

    public List<EnergyMeter> getAll() {
        return repository.findAll();
    }

    public EnergyMeter getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public EnergyMeter create(EnergyMeter meter) {
        return repository.save(meter);
    }

    public EnergyMeter update(Long id, EnergyMeter updated) {
        EnergyMeter existing = getById(id);
        if (existing != null) {
            existing.setSerialNumber(updated.getSerialNumber());
            existing.setInstallationDate(updated.getInstallationDate());
            return repository.save(existing);
        }
        return null;
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
