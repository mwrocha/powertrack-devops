package br.com.powertrack.repository;

import br.com.powertrack.model.MeterReading;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeterReadingRepository extends JpaRepository<MeterReading, Long> {
    List<MeterReading> findByEquipmentId(Long equipmentId);
}
