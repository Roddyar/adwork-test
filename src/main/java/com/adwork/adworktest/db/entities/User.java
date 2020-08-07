package com.adwork.adworktest.db.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class User {

    @Id
    @Column(updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false, length = 50)
    private String identificacion;
    @Column(nullable = false, length = 100)
    private String nombres;
    @Column(nullable = false, length = 100)
    private String apellidos;
    @Column(nullable = false)
    private Integer edad;
    @Column(nullable = false, length = 1)
    private String genero;
    @Column(nullable = false, length = 50)
    private String ciudad;
    @Column(nullable = false, length = 50)
    private String usuario;
    @Column(nullable = false, length = 50)
    private String password;
    @Column(nullable = false, length = 50)
    private String fecha_nacimiento;

}
