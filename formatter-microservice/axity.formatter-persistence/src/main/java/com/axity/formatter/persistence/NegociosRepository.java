package com.axity.formatter.persistence;

import com.axity.formatter.model.NegociosDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NegociosRepository extends JpaRepository<NegociosDO, Long> {
	// Métodos personalizados se agregarán aquí.
}
