package com.sergioramirezme.pinapp.service;

import com.sergioramirezme.pinapp.presentation.dtos.ClientCreateReqDTO;
import com.sergioramirezme.pinapp.presentation.dtos.ClientFullResDTO;
import com.sergioramirezme.pinapp.presentation.dtos.ClientKPIResDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IClientService {

    void create(ClientCreateReqDTO clientCreateReqDTO);
    Page<ClientFullResDTO> list(Pageable pageable);
    ClientKPIResDTO kpi();
}
