package com.sergioramirezme.pinapp.service.mappers;

import com.sergioramirezme.pinapp.presentation.dtos.ClientCreateReqDTO;
import com.sergioramirezme.pinapp.presentation.dtos.ClientFullResDTO;
import com.sergioramirezme.pinapp.presentation.dtos.ClientKPIResDTO;
import com.sergioramirezme.pinapp.model.entities.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "lastname", source = "lastname")
    @Mapping(target = "age", source = "age")
    @Mapping(target = "birthdate", source = "birthdate")
    Client toEntity(ClientCreateReqDTO clientCreateReqDTO);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "lastname", source = "lastname")
    @Mapping(target = "age", source = "age")
    @Mapping(target = "birthdate", source = "birthdate")
    @Mapping(target = "estimatedDateOfDeath", source = "estimatedDateOfDeath")
    ClientFullResDTO toDTO(Client client);


    @Mapping(target = "ageAverage", source = "ageAverage")
    @Mapping(target = "standardDeviation", source = "standardDeviation")
    ClientKPIResDTO toDTO(Double ageAverage, Double standardDeviation);

}
