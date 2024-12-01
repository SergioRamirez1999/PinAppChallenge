package com.sergioramirezme.pinapp.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="TB_APPPARAMETERS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppParameter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_APPPARAMETER")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "VALUE_PARAM")
    private String value;
}
