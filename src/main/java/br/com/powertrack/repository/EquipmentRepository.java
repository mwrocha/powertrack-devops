package br.com.powertrack.repository;

import br.com.powertrack.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    // Métodos findById, findAll, save, etc. também serão criados aqui
}