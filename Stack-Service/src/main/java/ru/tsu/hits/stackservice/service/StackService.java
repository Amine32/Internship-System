package ru.tsu.hits.stackservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tsu.hits.stackservice.dto.CreateUpdateStackDto;
import ru.tsu.hits.stackservice.dto.StackDto;
import ru.tsu.hits.stackservice.dto.converter.StackDtoConverter;
import ru.tsu.hits.stackservice.model.LanguageEntity;
import ru.tsu.hits.stackservice.model.StackEntity;
import ru.tsu.hits.stackservice.repository.LanguageRepository;
import ru.tsu.hits.stackservice.repository.StackRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StackService {

    private final StackRepository stackRepository;
    private final LanguageRepository languageRepository;

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

    public StackDto createStack(CreateUpdateStackDto dto) {
        StackEntity entity = StackDtoConverter.dtoToEntity(dto);

        // Fetch and set related languages
        List<LanguageEntity> relatedLanguages = languageRepository.findAllById(dto.getRelatedLanguageIds());
        entity.setRelatedLanguages(relatedLanguages);

        entity = stackRepository.save(entity);
        return StackDtoConverter.entityToDto(entity);
    }
}
