package ru.tsu.hits.stackservice.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.stackservice.dto.CreateUpdateLanguageDto;
import ru.tsu.hits.stackservice.dto.LanguageDto;
import ru.tsu.hits.stackservice.service.LanguageService;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/languages")
public class LanguageController {

    private final LanguageService languageService;

    @GetMapping("/")
    public List<LanguageDto> getAllLanguages() {
        return languageService.getAllLanguages();
    }

    @GetMapping("/byPosition/{positionName}")
    public List<LanguageDto> getAllLanguagesByPosition(@PathVariable String positionName) {
        return languageService.getAllLanguagesRelatedToStack(positionName);
    }

    @PostMapping("/byIds")
    public List<LanguageDto> getAllLanguagesByIds(@RequestBody List<Long> ids) {
        return languageService.getAllLanguagesByIds(ids);
    }

    @PostMapping("/namesByIds")
    public List<String> getLanguageNamesByIds(@RequestBody List<Long> ids) {
        return languageService.getLanguageNamesByIds(ids);
    }

    @PostMapping
    public LanguageDto createLanguage(@RequestBody CreateUpdateLanguageDto dto) {
        return languageService.createLanguage(dto);
    }
}

