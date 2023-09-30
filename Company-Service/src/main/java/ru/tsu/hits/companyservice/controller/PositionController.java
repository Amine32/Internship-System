package ru.tsu.hits.companyservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.companyservice.dto.CreateUpdatePositionDto;
import ru.tsu.hits.companyservice.dto.PositionDto;
import ru.tsu.hits.companyservice.service.PositionService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/positions")
@RequiredArgsConstructor
@Api(tags = "Position API")
public class PositionController {

    private final PositionService positionService;

    private final Logger logger = LoggerFactory.getLogger(PositionController.class);


    @PostMapping
    @ApiOperation("Create a new position")
    public ResponseEntity<PositionDto> createPosition(@Valid @RequestBody CreateUpdatePositionDto dto, HttpServletRequest request) {
        logger.info("Creating new position with title {}", dto.getTitle());
        PositionDto newPosition = positionService.createPosition(dto, request);
        return new ResponseEntity<>(newPosition, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @ApiOperation("Get a position by ID")
    public ResponseEntity<PositionDto> getPositionById(@PathVariable String id, HttpServletRequest request) {
        logger.info("Fetching position with ID {}", id);
        PositionDto position = positionService.getPositionById(id, request);
        return new ResponseEntity<>(position, HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation("Get all positions")
    public ResponseEntity<List<PositionDto>> getAllPositions(HttpServletRequest request) {
        logger.info("Fetching all positions");
        List<PositionDto> positions = positionService.getAllPositions(request);
        return new ResponseEntity<>(positions, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update a position by ID")
    public ResponseEntity<PositionDto> updatePosition(@PathVariable String id, @Valid @RequestBody CreateUpdatePositionDto updatePositionDTO, HttpServletRequest request) {
        logger.info("Updating position with ID {}", id);
        PositionDto updatedPosition = positionService.updatePosition(id, updatePositionDTO, request);
        return new ResponseEntity<>(updatedPosition, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete a position by ID")
    public ResponseEntity<Void> deletePosition(@PathVariable String id) {
        logger.info("Deleting position with ID {}", id);
        positionService.deletePosition(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/decrementPlacesLeft")
    @ApiOperation("Decrement the number of places left for a position")
    public ResponseEntity<Void> decrementPlacesLeft(@PathVariable String id) {
        logger.info("Decrementing the number of places left for position with ID {}", id);
        positionService.decrementPlacesLeft(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

