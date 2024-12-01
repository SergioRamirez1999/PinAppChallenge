package com.sergioramirezme.pinapp.service;

import com.sergioramirezme.pinapp.repository.IClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class KPIServiceCache {

    private static final String KPI_AGE_AVERAGE = "AGE_AVERAGE";
    private static final String KPI_STANDARD_DESVIATION = "STANDARD_DESVIATION";

    private static final long BATCH_SIZE = 2;

    private final Logger logger = LoggerFactory.getLogger(KPIServiceCache.class);

    private final IClientRepository clientRepository;

    private final Map<String, Double> cache = new ConcurrentHashMap<>();

    public Double getAgeAverage() {
        return cache.get(KPI_AGE_AVERAGE);
    }

    public Double getStandardDesviation() {
        return cache.get(KPI_STANDARD_DESVIATION);
    }

    @Scheduled(cron = "0 */1 * * * *")
    private void refreshCache() {
        logger.info("Updating cache...");

        long totalClients = clientRepository.count();
        long totalBatches = (totalClients + BATCH_SIZE - 1) / BATCH_SIZE;
        double sumAges = 0.0;
        double sumSquares = 0.0;
        long totalRowsProcessed = 0;

        for (long batch = 0; batch < totalBatches; batch++) {
            Page<Integer> agesPage = clientRepository.findAllAges(PageRequest.of((int) batch, (int) BATCH_SIZE));
            for (int age : agesPage) {
                sumAges += age;
                sumSquares += Math.pow(age, 2);
            }
            totalRowsProcessed += agesPage.getNumberOfElements();
        }

        Double ageAverage = calculeAgeAverage(totalRowsProcessed, sumAges);

        Double standardDeviation = calculeStandardDeviation(totalRowsProcessed, sumSquares, sumAges);

        cache.put(KPI_AGE_AVERAGE, ageAverage);
        cache.put(KPI_STANDARD_DESVIATION, standardDeviation);

    }
    private Double calculeAgeAverage(long totalRowsProcessed, double sumAges) {
        return totalRowsProcessed > 0 ? sumAges / totalRowsProcessed : 0.0;
    }

    private Double calculeStandardDeviation(long totalRowsProcessed, double sumSquares, double sumAges) {
        return totalRowsProcessed > 0
                ? Math.sqrt((sumSquares / totalRowsProcessed) - Math.pow((sumAges / totalRowsProcessed), 2))
                : 0.0;
    }

    @Bean
    public ApplicationRunner initKPICache() {
        return args -> {
            logger.info("Initializing cache...");
            refreshCache();
        };
    }

    @Autowired
    public KPIServiceCache(IClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
}
