package com.axity.formatter.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "logs")
public class LogsDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Timestamp", length = 100)
    private String timestamp;

    @Column(name = "Level", length = 15)
    private String level;

    @Column(name = "Template", columnDefinition = "TEXT")
    private String template;

    @Column(name = "Message", columnDefinition = "TEXT")
    private String message;

    @Column(name = "Exception", columnDefinition = "TEXT")
    private String exception;

    @Column(name = "Properties", columnDefinition = "TEXT")
    private String properties;

    @Column(name = "_ts", columnDefinition = "TIMESTAMP")
    private java.sql.Timestamp ts;
}
