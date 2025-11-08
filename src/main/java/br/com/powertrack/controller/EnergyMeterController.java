package br.com.powertrack.controller;

import br.com.powertrack.model.EnergyMeter;
import br.com.powertrack.service.EnergyMeterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/energy-meter")
public class EnergyMeterController {

    @Autowired
    private EnergyMeterService energyMeterService;

    @GetMapping
    public List<EnergyMeter> getAll() {
        return energyMeterService.getAll();
    }

    @GetMapping("/{id}")
    public EnergyMeter getById(@PathVariable Long id) {
        return energyMeterService.getById(id);
    }

    @PostMapping
    public EnergyMeter create(@RequestBody EnergyMeter energyMeter) {
        return energyMeterService.create(energyMeter);
    }

    @PutMapping("/{id}")
    public EnergyMeter update(@PathVariable Long id, @RequestBody EnergyMeter energyMeter) {
        return energyMeterService.update(id, energyMeter);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        energyMeterService.delete(id);
    }
}
