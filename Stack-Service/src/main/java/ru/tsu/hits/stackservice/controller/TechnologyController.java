package ru.tsu.hits.stackservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.stackservice.model.TechnologyEntity;
import ru.tsu.hits.stackservice.service.TechnologyService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/technologies")
public class TechnologyController {

    private final TechnologyService technologyService;

    @GetMapping("/")
    public List<TechnologyEntity> getAllTechnologies() {
        return technologyService.getAllTechnologies();
    }

    @PostMapping("/byIds")
    public List<TechnologyEntity> getAllTechnologiesByIds(@RequestBody List<String> ids) {
        return technologyService.getAllTechnologiesByIds(ids);
    }
}

