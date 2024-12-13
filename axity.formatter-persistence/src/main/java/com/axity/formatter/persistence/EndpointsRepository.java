package com.axity.formatter.persistence;

import com.axity.formatter.model.EndpointsDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EndpointsRepository extends JpaRepository<EndpointsDO, Long> {
    // Métodos personalizados se agregarán aquí.
}
