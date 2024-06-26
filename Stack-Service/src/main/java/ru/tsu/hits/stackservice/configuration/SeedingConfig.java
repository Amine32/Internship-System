package ru.tsu.hits.stackservice.configuration;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ru.tsu.hits.stackservice.service.CsvSeedingService;

import java.io.IOException;

@Profile("seeding")
@Configuration
@RequiredArgsConstructor
public class SeedingConfig {

    private final CsvSeedingService csvSeedingService;

    @PostConstruct
    public void init() throws IOException {
        csvSeedingService.seedDatabase();
    }
}
