package com.sergioramirezme.pinapp.presentation.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Modelo listar cliente completo")
public class ClientFullResDTO {

    @JsonProperty("id")
    @Schema(description = "ID de cliente", example = "1")
    private Integer id;

    @JsonProperty("name")
    @Schema(description = "Nombre de cliente", example = "Sergio")
    private String name;

    @JsonProperty("lastname")
    @Schema(description = "Apellido de cliente", example = "Ramírez")
    private String lastname;

    @JsonProperty("age")
    @Schema(description = "Edad de cliente", example = "25")
    private Integer age;

    @JsonProperty("birthdate")
    @Schema(description = "Fecha de nacimiento de cliente", example = "1999-09-11")
    private LocalDate birthdate;

    @JsonProperty("estimatedDateOfDeath")
    @Schema(description = "Fecha estimada de muerte de cliente", example = "2069-09-10")
    private LocalDate estimatedDateOfDeath;
}
