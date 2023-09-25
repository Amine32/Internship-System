package ru.tsu.hits.stackservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.stackservice.dto.CreateUpdateStackDto;
import ru.tsu.hits.stackservice.dto.StackDto;
import ru.tsu.hits.stackservice.service.StackService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stacks")
public class StackController {

    private final StackService stackService;

    @GetMapping("/")
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

    @PostMapping
    public StackDto createStack(@RequestBody CreateUpdateStackDto dto) {
        return stackService.createStack(dto);
    }
}

