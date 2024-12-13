package com.axity.formatter.commons.grafana;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "endpoint_negocio")
@IdClass(EndpointNegocioIdClass.class) // Usamos la clase de ID compuesta
public class EndpointNegocioDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne
    @JoinColumn(name = "pk_endpoint", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_endpoint"), nullable = false)
    private EndpointsDO endpoint;

    @Id
    @ManyToOne
    @JoinColumn(name = "pk_negocio", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_negocio"), nullable = false)
    private NegociosDO negocio;
}
