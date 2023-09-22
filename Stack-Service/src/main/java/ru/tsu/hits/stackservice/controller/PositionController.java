package ru.tsu.hits.stackservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.stackservice.model.PositionEntity;
import ru.tsu.hits.stackservice.service.PositionService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/positions")
public class PositionController {

    private final PositionService positionService;

    @GetMapping("/")
    public List<PositionEntity> getAllPositions() {
        return positionService.getAllPositions();
    }

    @PostMapping("/byIds")
    public List<PositionEntity> getAllPositionsByIds(@RequestBody List<String> ids) {
        return positionService.getAllPositionsByIds(ids);
    }
}

