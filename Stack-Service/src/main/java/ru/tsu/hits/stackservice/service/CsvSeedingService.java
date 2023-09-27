package ru.tsu.hits.stackservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsu.hits.stackservice.model.LanguageEntity;
import ru.tsu.hits.stackservice.model.Relationship;
import ru.tsu.hits.stackservice.model.StackEntity;
import ru.tsu.hits.stackservice.model.TechnologyEntity;
import ru.tsu.hits.stackservice.repository.LanguageRepository;
import ru.tsu.hits.stackservice.repository.StackRepository;
import ru.tsu.hits.stackservice.repository.TechnologyRepository;
import ru.tsu.hits.stackservice.util.CsvReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CsvSeedingService {

    private final LanguageRepository languageRepository;
    private final StackRepository stackRepository;
    private final TechnologyRepository technologyRepository;

    @Transactional
    public void seedDatabase() throws IOException {
        // Read CSV files
        InputStream languageStream = getClass().getResourceAsStream("/languages_first_step.csv");
        InputStream stackStream = getClass().getResourceAsStream("/stacks_first_step.csv");
        InputStream technologyStream = getClass().getResourceAsStream("/technologies_first_step.csv");

        List<String> languageNames = CsvReader.readCsv(languageStream);
        List<String> stackNames = CsvReader.readCsv(stackStream);
        List<String> technologyNames = CsvReader.readCsv(technologyStream);

        // Convert to entities
        List<LanguageEntity> languages = languageNames.stream()
                .map(name -> {
                    LanguageEntity entity = new LanguageEntity();
                    entity.setName(name);
                    return entity;
                })
                .collect(Collectors.toList());

        List<StackEntity> stacks = stackNames.stream()
                .map(name -> {
                    StackEntity entity = new StackEntity();
                    entity.setName(name);
                    return entity;
                })
                .collect(Collectors.toList());

        List<TechnologyEntity> technologies = technologyNames.stream()
                .map(name -> {
                    TechnologyEntity entity = new TechnologyEntity();
                    entity.setName(name);
                    return entity;
                })
                .collect(Collectors.toList());

        // Seed entities
        languageRepository.saveAll(languages);
        stackRepository.saveAll(stacks);
        technologyRepository.saveAll(technologies);

        // Read and seed relationships
        InputStream relationshipStream = getClass().getResourceAsStream("/relationships_second_step.csv");
        List<Relationship> relationships = CsvReader.readCsvForRelationships(relationshipStream);
        for (Relationship relationship : relationships) {
            LanguageEntity language = null;
            StackEntity stack = null;
            TechnologyEntity technology = null;

            if ("Language".equals(relationship.getFromType()) || "Language".equals(relationship.getToType())) {
                language = languageRepository.findById(
                        "Language".equals(relationship.getFromType()) ? relationship.getFromId() : relationship.getToId()
                ).orElse(null);
            }
            if ("Stack".equals(relationship.getFromType()) || "Stack".equals(relationship.getToType())) {
                stack = stackRepository.findById(
                        "Stack".equals(relationship.getFromType()) ? relationship.getFromId() : relationship.getToId()
                ).orElse(null);
            }
            if ("Technology".equals(relationship.getFromType()) || "Technology".equals(relationship.getToType())) {
                technology = technologyRepository.findById(
                        "Technology".equals(relationship.getFromType()) ? relationship.getFromId() : relationship.getToId()
                ).orElse(null);
            }

            // Update relationships
            if (language != null && stack != null) {
                language.getRelatedStacks().add(stack);
                stack.getRelatedLanguages().add(language);
            }
            if (language != null && technology != null) {
                language.getRelatedTechnologies().add(technology);
                technology.getRelatedLanguages().add(language);
            }
            if (stack != null && technology != null) {
                stack.getRelatedTechnologies().add(technology);
                technology.getRelatedStacks().add(stack);
            }

            // Save updated entities
            if (language != null) {
                languageRepository.save(language);
            }
            if (stack != null) {
                stackRepository.save(stack);
            }
            if (technology != null) {
                technologyRepository.save(technology);
            }
        }

    }
}

