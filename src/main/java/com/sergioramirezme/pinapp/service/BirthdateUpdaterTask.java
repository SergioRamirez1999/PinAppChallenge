package com.sergioramirezme.pinapp.service;

import com.sergioramirezme.pinapp.model.entities.Client;
import com.sergioramirezme.pinapp.repository.IClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BirthdateUpdaterTask {

    private final Logger logger = LoggerFactory.getLogger(BirthdateUpdaterTask.class);

    private final IClientRepository clientRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void updateClientAges() {
        logger.info("Updating client age...");

        LocalDate today = LocalDate.now();
        int currentMonth = today.getMonthValue();
        int currentDay = today.getDayOfMonth();

        List<Client> clientsWithBirthday = clientRepository.findClientsByBirthday(currentMonth, currentDay);

        clientsWithBirthday.stream()
                .filter(c -> !c.getCreationDate().isEqual(today))
                .forEach(c -> c.setAge(c.getAge() + 1));

        clientRepository.saveAll(clientsWithBirthday);
    }

    @Autowired
    public BirthdateUpdaterTask(IClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
}
