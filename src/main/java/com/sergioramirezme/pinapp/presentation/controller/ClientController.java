package com.sergioramirezme.pinapp.presentation.controller;

import com.sergioramirezme.pinapp.service.IClientService;
import com.sergioramirezme.pinapp.presentation.dtos.ClientCreateReqDTO;
import com.sergioramirezme.pinapp.presentation.dtos.ClientFullResDTO;
import com.sergioramirezme.pinapp.presentation.dtos.ClientKPIResDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/clients")
@Validated
public class ClientController {

    private final IClientService clientService;

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody ClientCreateReqDTO clientCreateReqDTO) {
        clientService.create(clientCreateReqDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ClientFullResDTO>> list(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<ClientFullResDTO> clientsDTO = clientService.list(pageable);
        return ResponseEntity.ok(clientsDTO);
    }

    @GetMapping("/kpi")
    public ResponseEntity<ClientKPIResDTO> kpi() {
        ClientKPIResDTO clientDTO = clientService.kpi();
        return ResponseEntity.ok(clientDTO);
    }

    @Autowired
    public ClientController(IClientService clientService) {
        this.clientService = clientService;
    }
}
