package com.sergioramirezme.pinapp.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="TB_CLIENT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CLIENT")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "LASTNAME")
    private String lastname;

    @Column(name = "AGE")
    private Integer age;

    @Column(name = "BIRTHDATE")
    private LocalDate birthdate;

    @Column(name = "CREATION_DATE")
    private LocalDate creationDate;

    @Transient
    private LocalDate estimatedDateOfDeath;

    @PrePersist
    public void prePersist() {
        this.creationDate = LocalDate.now();
    }
}
