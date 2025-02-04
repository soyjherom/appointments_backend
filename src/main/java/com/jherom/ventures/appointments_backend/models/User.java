package com.jherom.ventures.appointments_backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column
    private String name;

    @Column(length = 64)
    private String phone;

    @Column(length = 64)
    private String phoneHash;

    @Column(length = 64)
    private String email;

    @Column(length = 64)
    private String emailHash;

    @Version
    @Column
    private int version;

}
