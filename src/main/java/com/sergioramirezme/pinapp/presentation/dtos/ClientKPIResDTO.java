package com.sergioramirezme.pinapp.presentation.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Modelo KPI de clientes")
public class ClientKPIResDTO {

    @JsonProperty("ageAverage")
    @Schema(description = "Edad promedio de clientes", example = "26.7")
    private Double ageAverage;

    @JsonProperty("standardDeviation")
    @Schema(description = "Desviación estandar de clientes", example = "3.0347981810987097")
    private Double standardDeviation;

}
