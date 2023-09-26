package ru.tsu.hits.stackservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.stackservice.dto.CreateUpdateTechnologyDto;
import ru.tsu.hits.stackservice.dto.TechnologyDto;
import ru.tsu.hits.stackservice.service.TechnologyService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/technologies")
public class TechnologyController {

    private final TechnologyService technologyService;

    @PostMapping
    public TechnologyDto createOrUpdateTechnology(@RequestBody CreateUpdateTechnologyDto dto) {
        return technologyService.createOrUpdateTechnology(dto);
    }

    @GetMapping
    public List<TechnologyDto> getAllTechnologies() {
        return technologyService.getAllTechnologies();
    }

    @PostMapping("/byIds")
    public List<TechnologyDto> getAllTechnologiesByIds(@RequestBody List<Long> ids) {
        return technologyService.getAllTechnologiesByIds(ids);
    }

    @PostMapping("/namesByIds")
    public List<String> getTechnologyNamesByIds(@RequestBody List<Long> ids) {
        return technologyService.getTechnologyNamesByIds(ids);
    }

    @GetMapping("/byLanguage/{languageName}")
    public List<TechnologyDto> getAllTechnologiesByLanguageName(@PathVariable String languageName) {
        return technologyService.getAllTechnologiesByLanguageName(languageName);
    }

    @GetMapping("/byStack/{stackName}")
    public List<TechnologyDto> getAllTechnologiesByStackName(@PathVariable String stackName) {
        return technologyService.getAllTechnologiesByStackName(stackName);
    }

    @GetMapping("/byStackAndLanguage/{stackName}/{languageName}")
    public List<TechnologyDto> getAllTechnologiesByStackAndLanguageName(@PathVariable String stackName, @PathVariable String languageName) {
        return technologyService.getAllTechnologiesByStackAndLanguageName(stackName, languageName);
    }
}

