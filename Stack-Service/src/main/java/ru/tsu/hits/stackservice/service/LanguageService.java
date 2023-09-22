package ru.tsu.hits.stackservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.tsu.hits.stackservice.model.LanguageEntity;
import ru.tsu.hits.stackservice.repository.LanguageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguageService {

    private final LanguageRepository languageRepository;

    public List<LanguageEntity> getAllLanguages() {
        return languageRepository.findAll();
    }

    public List<LanguageEntity> getAllLanguagesByIds(@RequestBody List<String> ids) {
        return languageRepository.findAllById(ids);
    }

    public List<LanguageEntity> getAllLanguagesRelatedToPosition(String positionName) {
        return languageRepository.findAllByPositions_Name(positionName);
    }
}
