package ru.tsu.hits.stackservice.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.stackservice.dto.CreateUpdateStackDto;
import ru.tsu.hits.stackservice.dto.StackDto;
import ru.tsu.hits.stackservice.service.StackService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stacks")
@Tag(name = "Stack API")
public class StackController {

    private final StackService stackService;

    @PostMapping
    public StackDto createOrUpdateStack(@RequestBody CreateUpdateStackDto dto) {
        return stackService.createOrUpdateStack(dto);
    }

    @GetMapping
    public List<StackDto> getAllStacks() {
        return stackService.getAllStacks();
    }

    @PostMapping("/byIds")
    public List<StackDto> getAllStacksByIds(@RequestBody List<Long> ids) {
        return stackService.getAllStacksByIds(ids);
    }

    @PostMapping("/namesByIds")
    public List<String> getStackNamesByIds(@RequestBody List<Long> ids) {
        return stackService.getStackNamesByIds(ids);
    }

    @GetMapping("/byLanguage/{languageName}")
    public List<StackDto> getAllStacksByLanguageName(@PathVariable String languageName) {
        return stackService.getAllStacksByLanguageName(languageName);
    }

    @GetMapping("/byTechnology/{technologyName}")
    public List<StackDto> getAllStacksByTechnologyName(@PathVariable String technologyName) {
        return stackService.getAllStacksByTechnologyName(technologyName);
    }
}

