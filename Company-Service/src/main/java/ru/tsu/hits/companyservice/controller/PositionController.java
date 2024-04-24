package ru.tsu.hits.companyservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.companyservice.dto.CreateUpdatePositionDto;
import ru.tsu.hits.companyservice.dto.PositionDto;
import ru.tsu.hits.companyservice.dto.UpdateSearchPeriodRequest;
import ru.tsu.hits.companyservice.service.PositionService;

import java.util.Optional;

@RestController
@RequestMapping("/api/positions")
@RequiredArgsConstructor
@Tag(name = "Position API")
public class PositionController {

    private final PositionService positionService;

    private final Logger logger = LoggerFactory.getLogger(PositionController.class);

    @PostMapping
    @Operation(summary = "Create a new position")
    public PositionDto createPosition(@Valid @RequestBody CreateUpdatePositionDto dto) {
        logger.info("Creating new position with title {}", dto.getTitle());
        return positionService.createPosition(dto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a position by ID")
    public PositionDto getPositionById(@PathVariable String id) {
        logger.info("Fetching position with ID {}", id);
        return positionService.getPositionById(id);
    }

    @GetMapping
    @Operation(summary = "Get all positions")
    public Page<PositionDto> getAllPositions(@RequestParam Optional<Integer> page,
                                             @RequestParam Optional<Integer> size,
                                             @RequestParam Optional<String> sort) {
        logger.info("Fetching all positions");
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(10), Sort.by(sort.orElse("createdAt")).descending());
        return positionService.getAllPositions(pageable);
    }

    @GetMapping("/byCompany/{companyId}")
    @Operation(summary = "Get positions by company ID")
    public Page<PositionDto> getPositionsByCompanyId(@PathVariable String companyId,
                                                     @RequestParam Optional<Integer> page,
                                                     @RequestParam Optional<Integer> size,
                                                     @RequestParam Optional<String> sort) {
        logger.info("Fetching positions by company ID {}", companyId);
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(10), Sort.by(sort.orElse("createdAt")).descending());
        return positionService.getPositionsByCompanyId(companyId, pageable);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a position by ID")
    public PositionDto updatePosition(@PathVariable String id, @Valid @RequestBody CreateUpdatePositionDto updatePositionDTO) {
        logger.info("Updating position with ID {}", id);
        return positionService.updatePosition(id, updatePositionDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a position by ID")
    public void deletePosition(@PathVariable String id) {
        logger.info("Deleting position with ID {}", id);
        positionService.deletePosition(id);
    }

    @PutMapping("/{id}/decrementPlacesLeft")
    @Operation(summary = "Decrement the number of places left for a position")
    @PreAuthorize("hasRole('TRUSTED_SERVICE')")
    public void decrementPlacesLeft(@PathVariable String id) {
        logger.info("Decrementing the number of places left for position with ID {}", id);
        positionService.decrementPlacesLeft(id);
    }

    @PatchMapping("/{id}/updateSearchPeriod")
    public PositionDto updatePositionSearchPeriod(@PathVariable String id, @RequestBody UpdateSearchPeriodRequest request) {
        logger.info("Adding search period to position with ID {}", id);
        return positionService.updateSearchPeriod(id, request.getSearchPeriodId());
    }

    @GetMapping("/bySearchPeriod/{searchPeriodId}")
    public Page<PositionDto> getPositionsBySearchPeriodId(@PathVariable String searchPeriodId,
                                                          @RequestParam Optional<Integer> page,
                                                          @RequestParam Optional<Integer> size,
                                                          @RequestParam Optional<String> sort) {
        logger.info("Fetching positions by search period ID {}", searchPeriodId);
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(25), Sort.by(sort.orElse("companyId")).descending());
        return positionService.getPositionsBySearchPeriodId(searchPeriodId, pageable);
    }
}

