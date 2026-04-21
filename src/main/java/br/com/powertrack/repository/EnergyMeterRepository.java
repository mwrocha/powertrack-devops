package br.com.powertrack.repository;

import br.com.powertrack.model.EnergyMeter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnergyMeterRepository extends MongoRepository<EnergyMeter, String> {

    EnergyMeter findBySerialNumber(String serialNumber);

}