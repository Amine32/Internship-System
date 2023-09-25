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

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TechnologyService {

    private final TechnologyRepository technologyRepository;
    private final LanguageRepository languageRepository;
    private final StackRepository stackRepository;

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

    public TechnologyDto createTechnology(CreateUpdateTechnologyDto dto) {
        TechnologyEntity entity = TechnologyDtoConverter.dtoToEntity(dto);

        // Fetch and set related stack and language
        StackEntity relatedStack = stackRepository.findById(dto.getRelatedStackId())
                .orElseThrow(() -> new EntityNotFoundException("Stack not found"));
        LanguageEntity relatedLanguage = languageRepository.findById(dto.getRelatedLanguageId())
                .orElseThrow(() -> new EntityNotFoundException("Language not found"));

        entity.setRelatedStack(relatedStack);
        entity.setRelatedLanguage(relatedLanguage);

        entity = technologyRepository.save(entity);
        return TechnologyDtoConverter.entityToDto(entity);
    }
}
