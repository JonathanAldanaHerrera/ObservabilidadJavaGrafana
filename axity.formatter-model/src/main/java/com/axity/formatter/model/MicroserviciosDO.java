package com.axity.formatter.model;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "microservicios")
public class MicroserviciosDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false, length = 255)
    private String code;

    @Column(name = "name", length = 255)
    private String name;
}
