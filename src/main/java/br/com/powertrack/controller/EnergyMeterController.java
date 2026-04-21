package br.com.powertrack.controller;

import br.com.powertrack.model.EnergyMeter;
import br.com.powertrack.service.EnergyMeterService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/energy-meter")
public class EnergyMeterController {

    private final EnergyMeterService energyMeterService;

    public EnergyMeterController(EnergyMeterService energyMeterService) {
        this.energyMeterService = energyMeterService;
    }

    @GetMapping
    public List<EnergyMeter> getAll() {
        return energyMeterService.getAll();
    }

    @GetMapping("/{id}")
    public EnergyMeter getById(@PathVariable String id) { // 🔥 agora String
        return energyMeterService.getById(id);
    }

    @PostMapping
    public EnergyMeter create(@RequestBody EnergyMeter energyMeter) {
        return energyMeterService.create(energyMeter);
    }

    @PutMapping("/{id}")
    public EnergyMeter update(@PathVariable String id, @RequestBody EnergyMeter energyMeter) { // 🔥 String
        return energyMeterService.update(id, energyMeter);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) { // 🔥 String
        energyMeterService.delete(id);
    }
}