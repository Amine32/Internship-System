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

    @GetMapping("/")
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

    @PostMapping
    public TechnologyDto createTechnology(@RequestBody CreateUpdateTechnologyDto dto) {
        return technologyService.createTechnology(dto);
    }
}

