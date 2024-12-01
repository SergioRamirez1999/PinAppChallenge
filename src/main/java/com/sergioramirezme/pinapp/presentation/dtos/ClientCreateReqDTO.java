package com.sergioramirezme.pinapp.presentation.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sergioramirezme.pinapp.presentation.validators.ClientBirthdateValidation;
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
public class ClientCreateReqDTO {

    @JsonProperty("name")
    @NotBlank(message = "{client.name.notblank}")
    @Size(min = 3, max = 128, message = "{client.name.size_outbounds}")
    private String name;

    @JsonProperty("lastname")
    @NotBlank(message = "{client.lastname.notblank}")
    @Size(min = 3, max = 128, message = "{client.lastname.size_outbounds}")
    private String lastname;

    @JsonProperty("age")
    @NotNull(message = "{client.age.required}")
    @Positive(message = "{client.age.positive}")
    @Max(value = 150, message = "{client.age.max_outbounds}")
    private Integer age;

    @JsonProperty("birthdate")
    @NotNull(message = "{client.birthdate.required}")
    private LocalDate birthdate;
}
