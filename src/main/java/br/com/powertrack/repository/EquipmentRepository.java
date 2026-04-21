package br.com.powertrack.repository;

import br.com.powertrack.model.Equipment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends MongoRepository<Equipment, String> {

    List<Equipment> findByLocation(String location);

    List<Equipment> findByNameContainingIgnoreCase(String name);
}