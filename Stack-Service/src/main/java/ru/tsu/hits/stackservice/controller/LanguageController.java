package ru.tsu.hits.stackservice.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.stackservice.model.LanguageEntity;
import ru.tsu.hits.stackservice.service.LanguageService;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/languages")
public class LanguageController {

    private LanguageService languageService;

    @GetMapping("/")
    public List<LanguageEntity> getAllLanguages() {
        return languageService.getAllLanguages();
    }

    @GetMapping("/byPosition/{positionName}")
    public List<LanguageEntity> getAllLanguagesByPosition(@PathVariable String positionName) {
        return languageService.getAllLanguagesRelatedToPosition(positionName);
    }

    @PostMapping("/byIds")
    public List<LanguageEntity> getAllLanguagesByIds(@RequestBody List<String> ids) {
        return languageService.getAllLanguagesByIds(ids);
    }
}

