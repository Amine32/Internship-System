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

    @PostMapping
    public LanguageDto createOrUpdateLanguage(@RequestBody CreateUpdateLanguageDto dto) {
        return languageService.createOrUpdateLanguage(dto);
    }

    @GetMapping
    public List<LanguageDto> getAllLanguages() {
        return languageService.getAllLanguages();
    }

    @PostMapping("/byIds")
    public List<LanguageDto> getAllLanguagesByIds(@RequestBody List<Long> ids) {
        return languageService.getAllLanguagesByIds(ids);
    }

    @PostMapping("/namesByIds")
    public List<String> getLanguageNamesByIds(@RequestBody List<Long> ids) {
        return languageService.getLanguageNamesByIds(ids);
    }

    @GetMapping("/byStack/{stackName}")
    public List<LanguageDto> getAllLanguagesByStackName(@PathVariable String stackName) {
        return languageService.getAllLanguagesByStackName(stackName);
    }

    @GetMapping("/byTechnology/{technologyName}")
    public List<LanguageDto> getAllLanguagesByTechnologyName(@PathVariable String technologyName) {
        return languageService.getAllLanguagesByTechnologyName(technologyName);
    }

}

