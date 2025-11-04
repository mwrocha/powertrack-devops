package br.com.powertrack.repository;

import br.com.powertrack.model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Alert, Long> { }
