package com.sergioramirezme.pinapp.service;

import com.sergioramirezme.pinapp.presentation.dtos.ClientCreateReqDTO;
import com.sergioramirezme.pinapp.presentation.dtos.ClientFullResDTO;
import com.sergioramirezme.pinapp.presentation.dtos.ClientKPIResDTO;
import com.sergioramirezme.pinapp.model.entities.Client;
import com.sergioramirezme.pinapp.repository.IClientRepository;
import com.sergioramirezme.pinapp.service.mappers.ClientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class ClientService implements IClientService {

    private final IClientRepository clientRepository;
    private final AppParameterServiceCache appParameterServiceCache;
    private final KPIServiceCache kpiServiceCache;

    @Override
    public void create(ClientCreateReqDTO clientCreateReqDTO) {
        Client clientEntity = ClientMapper.INSTANCE.toEntity(clientCreateReqDTO);
        clientRepository.save(clientEntity);
    }

    @Override
    public Page<ClientFullResDTO> list(Pageable pageable) {
        Page<Client> clients = clientRepository.findAll(pageable);
        fillEstimatedDateOfDeath(clients);
        return clients.map(ClientMapper.INSTANCE::toDTO);
    }

    @Override
    public ClientKPIResDTO kpi() {
        Double clientsAgeAverage = kpiServiceCache.getAgeAverage();
        Double clientsStandardDesviation = kpiServiceCache.getStandardDesviation();
        return ClientMapper.INSTANCE.toDTO(clientsAgeAverage, clientsStandardDesviation);
    }

    private void fillEstimatedDateOfDeath(Page<Client> clients) {
        clients.forEach(
                c -> c.setEstimatedDateOfDeath(
                        c.getBirthdate().plusYears(
                                (long) appParameterServiceCache.getLongevity())
                ));
    }

    @Autowired
    public ClientService(IClientRepository clientRepository, AppParameterServiceCache appParameterServiceCache, KPIServiceCache kpiServiceCache) {
        this.clientRepository = clientRepository;
        this.appParameterServiceCache = appParameterServiceCache;
        this.kpiServiceCache = kpiServiceCache;
    }

}
