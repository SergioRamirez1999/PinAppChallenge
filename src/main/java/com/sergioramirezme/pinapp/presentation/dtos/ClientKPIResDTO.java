package com.sergioramirezme.pinapp.presentation.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientKPIResDTO {

    @JsonProperty("ageAverage")
    private Double ageAverage;

    @JsonProperty("standardDeviation")
    private Double standardDeviation;

}
