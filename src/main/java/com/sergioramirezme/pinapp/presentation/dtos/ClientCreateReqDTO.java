package com.sergioramirezme.pinapp.presentation.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sergioramirezme.pinapp.presentation.validators.ClientBirthdateValidation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ClientBirthdateValidation
@Schema(description = "Modelo creación de cliente")
public class ClientCreateReqDTO {

    @JsonProperty("name")
    @NotBlank(message = "{client.name.notblank}")
    @Size(min = 3, max = 128, message = "{client.name.size_outbounds}")
    @Schema(description = "Nombre de cliente", example = "Sergio")
    private String name;

    @JsonProperty("lastname")
    @NotBlank(message = "{client.lastname.notblank}")
    @Size(min = 3, max = 128, message = "{client.lastname.size_outbounds}")
    @Schema(description = "Apellido de cliente", example = "Ramírez")
    private String lastname;

    @JsonProperty("age")
    @NotNull(message = "{client.age.required}")
    @Positive(message = "{client.age.positive}")
    @Max(value = 150, message = "{client.age.max_outbounds}")
    @Schema(description = "Edad de cliente", example = "25")
    private Integer age;

    @JsonProperty("birthdate")
    @NotNull(message = "{client.birthdate.required}")
    @Schema(description = "Fecha de nacimiento de cliente", example = "1999-09-11")
    private LocalDate birthdate;
}
