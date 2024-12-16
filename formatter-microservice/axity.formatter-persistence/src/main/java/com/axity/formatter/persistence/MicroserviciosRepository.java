package com.axity.formatter.persistence;

import com.axity.formatter.model.MicroserviciosDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MicroserviciosRepository extends JpaRepository<MicroserviciosDO, Long> {
	// Métodos personalizados se agregarán aquí.
}
