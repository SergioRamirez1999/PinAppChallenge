package com.sergioramirezme.pinapp.presentation.controller;

import com.sergioramirezme.pinapp.service.IClientService;
import com.sergioramirezme.pinapp.presentation.dtos.ClientCreateReqDTO;
import com.sergioramirezme.pinapp.presentation.dtos.ClientFullResDTO;
import com.sergioramirezme.pinapp.presentation.dtos.ClientKPIResDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(
        origins = { "https://pinappchallenge-643178392609.us-central1.run.app", "https://pinappchallenge.onrender.com" },
        allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS}
)
@RestController
@RequestMapping("/v1/clients")
@Validated
public class ClientController {

    private final IClientService clientService;

    @Operation(summary = "Crear un cliente",
            description = "Crea un nuevo cliente en el servicio.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del cliente a crear",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ClientCreateReqDTO.class),
                            examples = @ExampleObject(
                                    name = "Ejemplo de entrada",
                                    value = "\"{\\\"name\\\": \\\"Sergio\\\", \\\"lastname\\\": \\\"Ramírez\\\", \\\"age\\\": 25, \\\"birthdate\\\": \\\"1999-11-30T00:00:00.00\\\"}\"\n"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Error en los datos enviados")
            }
    )
    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody ClientCreateReqDTO clientCreateReqDTO) {
        clientService.create(clientCreateReqDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Obtener lista de clientes con paginación",
            description = "Este endpoint permite obtener una lista de clientes paginada. Los parámetros `page` y `size` definen la página y el tamaño de la página, respectivamente.",
            parameters = {
                    @Parameter(
                            name = "page",
                            description = "Número de la página",
                            required = false,
                            schema = @Schema(type = "integer", defaultValue = "0")
                    ),
                    @Parameter(
                            name = "size",
                            description = "Tamaño de la página",
                            required = false,
                            schema = @Schema(type = "integer", defaultValue = "10")
                    ),
                    @Parameter(
                            name = "sort",
                            description = "Criterio de ordenamiento en formato `campo,asc` o `campo,desc`",
                            required = false,
                            schema = @Schema(type = "string", example = "id,asc")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de clientes obtenida exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ClientFullResDTO.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Parámetros inválidos"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            }
    )
    @GetMapping
    public ResponseEntity<Page<ClientFullResDTO>> list(
            @Parameter(hidden = true) @PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<ClientFullResDTO> clientsDTO = clientService.list(pageable);
        return ResponseEntity.ok(clientsDTO);
    }

    @Operation(
            summary = "Obtener KPI de clientes",
            description = "Este endpoint calcula y devuelve los KPI de los clientes: la edad promedio y la desviación estándar de las edades.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "KPI calculados exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ClientKPIResDTO.class)
                            )
                    ),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            }
    )
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
