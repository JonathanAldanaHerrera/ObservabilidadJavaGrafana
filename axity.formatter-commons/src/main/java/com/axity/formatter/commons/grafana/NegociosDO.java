package com.axity.formatter.commons.grafana;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "negocios")
public class NegociosDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false, length = 255)
    private String code;

    @Column(name = "name", nullable = false, length = 255)
    private String name;
}
