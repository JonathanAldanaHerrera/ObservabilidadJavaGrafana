package com.axity.formatter.persistence;

import com.axity.formatter.model.EndpointNegocioDO;
import com.axity.formatter.model.EndpointNegocioIdClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EndpointNegocioRepository extends JpaRepository<EndpointNegocioDO, EndpointNegocioIdClass> {

    @Query(value = """
        SELECT en.pk_negocio AS negocioId, en.pk_endpoint AS endpointId
        FROM endpoint_negocio en
        """, nativeQuery = true)
    List<Object[]> findNegocioEndpointRelations();
    
    // Consulta para obtener el negocioId relacionado con un endpointId
    @Query("SELECT ne.negocio.id FROM EndpointNegocioDO ne WHERE ne.endpoint.id = :endpointId")
    List<Long> findNegocioIdsByEndpointId(@Param("endpointId") Long endpointId);

}
