package ru.tsu.hits.stackservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.tsu.hits.stackservice.dto.CreateUpdateLanguageDto;
import ru.tsu.hits.stackservice.dto.LanguageDto;
import ru.tsu.hits.stackservice.dto.converter.LanguageDtoConverter;
import ru.tsu.hits.stackservice.model.LanguageEntity;
import ru.tsu.hits.stackservice.model.StackEntity;
import ru.tsu.hits.stackservice.repository.LanguageRepository;
import ru.tsu.hits.stackservice.repository.StackRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LanguageService {

    private final LanguageRepository languageRepository;
    private final StackRepository stackRepository;

    public List<LanguageDto> getAllLanguages() {
        return languageRepository.findAll().stream()
                .map(LanguageDtoConverter::entityToDto)
                .collect(Collectors.toList());
    }

    public List<LanguageDto> getAllLanguagesByIds(@RequestBody List<Long> ids) {
        return languageRepository.findAllById(ids).stream()
                .map(LanguageDtoConverter::entityToDto)
                .collect(Collectors.toList());
    }

    public List<LanguageDto> getAllLanguagesRelatedToStack(String stackName) {
        return languageRepository.findAllByRelatedStacks_Name(stackName).stream()
                .map(LanguageDtoConverter::entityToDto)
                .collect(Collectors.toList());
    }

    public List<String> getLanguageNamesByIds(List<Long> ids) {
        return languageRepository.findAllById(ids).stream()
                .map(LanguageEntity::getName)
                .collect(Collectors.toList());
    }

    public LanguageDto createLanguage(CreateUpdateLanguageDto dto) {
        LanguageEntity entity = LanguageDtoConverter.dtoToEntity(dto);

        //Fetch and set related stacks
        List<StackEntity> relatedStacks = stackRepository.findAllById(dto.getRelatedStackIds());
        entity.setRelatedStacks(relatedStacks);

        entity = languageRepository.save(entity);
        return LanguageDtoConverter.entityToDto(entity);
    }
}
