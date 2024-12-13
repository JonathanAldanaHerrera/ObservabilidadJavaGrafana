package com.axity.formatter.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "endpoints")
public class EndpointsDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false, length = 255)
    private String code;

    @Column(name = "method", nullable = false, length = 10)
    private String method;

    @Column(name = "path", nullable = false, length = 255)
    private String path;

    @ManyToOne
    @JoinColumn(name = "microservice_id", referencedColumnName = "id", 
                foreignKey = @ForeignKey(name = "fk_microservice_id"), 
                nullable = false)
    private MicroserviciosDO microservicios;
}
