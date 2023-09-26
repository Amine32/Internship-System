package ru.tsu.hits.stackservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tsu.hits.stackservice.dto.CreateUpdateTechnologyDto;
import ru.tsu.hits.stackservice.dto.TechnologyDto;
import ru.tsu.hits.stackservice.dto.converter.TechnologyDtoConverter;
import ru.tsu.hits.stackservice.model.LanguageEntity;
import ru.tsu.hits.stackservice.model.StackEntity;
import ru.tsu.hits.stackservice.model.TechnologyEntity;
import ru.tsu.hits.stackservice.repository.LanguageRepository;
import ru.tsu.hits.stackservice.repository.StackRepository;
import ru.tsu.hits.stackservice.repository.TechnologyRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TechnologyService {

    private final TechnologyRepository technologyRepository;
    private final LanguageRepository languageRepository;
    private final StackRepository stackRepository;

    public TechnologyDto createOrUpdateTechnology(CreateUpdateTechnologyDto dto) {
        TechnologyEntity entity = TechnologyDtoConverter.dtoToEntity(dto);
        List<StackEntity> relatedStacks = stackRepository.findAllById(dto.getRelatedStackIds());
        List<LanguageEntity> relatedLanguages = languageRepository.findAllById(dto.getRelatedLanguageIds());
        entity.setRelatedStacks(relatedStacks);
        entity.setRelatedLanguages(relatedLanguages);
        entity = technologyRepository.save(entity);
        return TechnologyDtoConverter.entityToDto(entity);
    }

    public List<TechnologyDto> getAllTechnologies() {
        return technologyRepository.findAll().stream()
                .map(TechnologyDtoConverter::entityToDto)
                .collect(Collectors.toList());
    }

    public List<TechnologyDto> getAllTechnologiesByIds(List<Long> ids) {
        return technologyRepository.findAllById(ids).stream()
                .map(TechnologyDtoConverter::entityToDto)
                .collect(Collectors.toList());
    }

    public List<String> getTechnologyNamesByIds(List<Long> ids) {
        return technologyRepository.findAllById(ids).stream()
                .map(TechnologyEntity::getName)
                .collect(Collectors.toList());
    }

    public List<TechnologyDto> getAllTechnologiesByLanguageName(String languageName) {
        return technologyRepository.findAllByRelatedLanguages_Name(languageName).stream()
                .map(TechnologyDtoConverter::entityToDto)
                .collect(Collectors.toList());
    }

    public List<TechnologyDto> getAllTechnologiesByStackName(String stackName) {
        return technologyRepository.findAllByRelatedStacks_Name(stackName).stream()
                .map(TechnologyDtoConverter::entityToDto)
                .collect(Collectors.toList());
    }

    public List<TechnologyDto> getAllTechnologiesByStackAndLanguageName(String stackName, String languageName) {
        return technologyRepository.findAllByRelatedStacks_NameAndRelatedLanguages_Name(stackName, languageName).stream()
                .map(TechnologyDtoConverter::entityToDto)
                .collect(Collectors.toList());
    }
}
