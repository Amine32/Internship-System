package ru.tsu.hits.stackservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tsu.hits.stackservice.dto.CreateUpdateStackDto;
import ru.tsu.hits.stackservice.dto.StackDto;
import ru.tsu.hits.stackservice.dto.converter.StackDtoConverter;
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
public class StackService {

    private final StackRepository stackRepository;
    private final LanguageRepository languageRepository;
    private final TechnologyRepository technologyRepository;

    public StackDto createOrUpdateStack(CreateUpdateStackDto dto) {
        StackEntity entity = StackDtoConverter.dtoToEntity(dto);
        List<LanguageEntity> relatedLanguages = languageRepository.findAllById(dto.getRelatedLanguageIds());
        List<TechnologyEntity> relatedTechnologies = technologyRepository.findAllById(dto.getRelatedTechnologyIds());
        entity.setRelatedLanguages(relatedLanguages);
        entity.setRelatedTechnologies(relatedTechnologies);
        entity = stackRepository.save(entity);
        return StackDtoConverter.entityToDto(entity);
    }

    public List<StackDto> getAllStacks() {
        return stackRepository.findAll().stream()
                .map(StackDtoConverter::entityToDto)
                .collect(Collectors.toList());
    }

    public List<StackDto> getAllStacksByIds(List<Long> ids) {
        return stackRepository.findAllById(ids).stream()
                .map(StackDtoConverter::entityToDto)
                .collect(Collectors.toList());
    }

    public List<String> getStackNamesByIds(List<Long> ids) {
        return stackRepository.findAllById(ids).stream()
                .map(StackEntity::getName)
                .collect(Collectors.toList());
    }

    public List<StackDto> getAllStacksByLanguageName(String languageName) {
        return stackRepository.findAllByRelatedLanguages_Name(languageName).stream()
                .map(StackDtoConverter::entityToDto)
                .collect(Collectors.toList());
    }

    public List<StackDto> getAllStacksByTechnologyName(String technologyName) {
        return stackRepository.findAllByRelatedTechnologies_Name(technologyName).stream()
                .map(StackDtoConverter::entityToDto)
                .collect(Collectors.toList());
    }
}
