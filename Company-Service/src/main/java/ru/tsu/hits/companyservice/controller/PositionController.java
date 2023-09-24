package ru.tsu.hits.companyservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.companyservice.dto.CreatePositionDto;
import ru.tsu.hits.companyservice.dto.PositionDto;
import ru.tsu.hits.companyservice.dto.UpdatePositionDto;
import ru.tsu.hits.companyservice.service.PositionService;

import java.util.List;

@RestController
@RequestMapping("/positions")
@RequiredArgsConstructor
public class PositionController {

    private final PositionService positionService;

    @PostMapping
    public PositionDto createPosition(@RequestBody CreatePositionDto dto) {
        return positionService.createPosition(dto);
    }

    @GetMapping("/{id}")
    public PositionDto getPositionById(@PathVariable String id) {
        return positionService.getPositionById(id);
    }

    @GetMapping
    public List<PositionDto> getAllPositions() {
        return positionService.getAllPositions();
    }

    @PutMapping("/{id}")
    public PositionDto updatePosition(@PathVariable String id, @RequestBody UpdatePositionDto updatePositionDTO) {
        return positionService.updatePosition(id, updatePositionDTO);
    }

    @DeleteMapping("/{id}")
    public void deletePosition(@PathVariable String id) {
        positionService.deletePosition(id);
    }
}

