package com.sergioramirezme.pinapp.service;

import com.sergioramirezme.pinapp.model.entities.AppParameter;
import com.sergioramirezme.pinapp.model.enums.AppParameterEnum;
import com.sergioramirezme.pinapp.repository.IAppParameterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AppParameterServiceCache {

    private final Logger logger = LoggerFactory.getLogger(ClientService.class);

    private static final Integer GLOBAL_LOGEVITY_DEFAULT = 73;

    private final IAppParameterRepository appParameterRepository;

    private final Map<String, Integer> cache = new ConcurrentHashMap<>();

    public Integer getLongevity() {
        return cache.getOrDefault(AppParameterEnum.GLOBAL_LONGEVITY_NAME.name(), GLOBAL_LOGEVITY_DEFAULT);
    }

    @Scheduled(cron = "0 */1 * * * *")
    private void refreshCache() {
        logger.info("Updating cache...");
        AppParameter appParameterLongevity = appParameterRepository.findByName(AppParameterEnum.GLOBAL_LONGEVITY_NAME.getValue());
        cache.put(AppParameterEnum.GLOBAL_LONGEVITY_NAME.name(), Integer.parseInt(appParameterLongevity.getValue()));
    }

    @Bean
    public ApplicationRunner initAppParameterCache() {
        return args -> {
            logger.info("Initializing cache...");
            refreshCache();
        };
    }

    @Autowired
    public AppParameterServiceCache(IAppParameterRepository appParameterRepository) {
        this.appParameterRepository = appParameterRepository;
    }
}
