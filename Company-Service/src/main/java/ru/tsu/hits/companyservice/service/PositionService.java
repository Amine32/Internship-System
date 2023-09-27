package ru.tsu.hits.companyservice.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import ru.tsu.hits.companyservice.dto.CreatePositionDto;
import ru.tsu.hits.companyservice.dto.PositionDto;
import ru.tsu.hits.companyservice.dto.UpdatePositionDto;
import ru.tsu.hits.companyservice.dto.converter.PositionDtoConverter;
import ru.tsu.hits.companyservice.exception.PositionNotFoundException;
import ru.tsu.hits.companyservice.model.PositionEntity;
import ru.tsu.hits.companyservice.repository.PositionRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PositionService {

    private final PositionRepository positionRepository;

    private final PositionDtoConverter dtoConverter;

    private static final Logger logger = LoggerFactory.getLogger(PositionService.class);

    public PositionDto createPosition(CreatePositionDto createPositionDto) {
        PositionEntity newPosition = dtoConverter.convertToEntity(createPositionDto);
        positionRepository.save(newPosition);
        return dtoConverter.convertToDto(newPosition);
    }

    public PositionDto getPositionById(String id, HttpServletRequest request) {
        PositionEntity position = positionRepository.findById(id).orElseThrow(() -> new PositionNotFoundException(id));

        // Create HttpHeaders instance and set the Authorization header
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", request.getHeader("Authorization"));

        // Call the Application microservice to get the current number of applications
        WebClient webClient = WebClient.builder().build();
        try {
            Integer currentNumberOfApplications = webClient
                    .get()
                    .uri("http://localhost:8080/application-service/api/applications/position/{positionId}/count", id)
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .retrieve()
                    .bodyToMono(Integer.class)
                    .block();

            // Safely unbox Integer to int, using 0 as default value
            int numberOfApplications = Optional.ofNullable(currentNumberOfApplications).orElse(0);

            // Update and save the numberOfApplications
            position.setNumberOfApplications(numberOfApplications);
            positionRepository.save(position);

        } catch (WebClientResponseException e) {
            // Log the status code and the reason
            logger.error("WebClientResponseException while fetching application count. Status code: {}, Reason: {}", e.getStatusCode(), e.getResponseBodyAsString());

        } catch (WebClientException e) {
            // Log WebClient related exceptions
            logger.error("WebClientException while fetching application count: {}", e.getMessage());

        } catch (Exception e) {
            // Log other exceptions
            logger.error("An unexpected error occurred while fetching application count: {}", e.getMessage());
        }

        return dtoConverter.convertToDto(position);
    }

    public List<PositionDto> getAllPositions() {
        return positionRepository.findAll()
                .stream()
                .map(dtoConverter::convertToDto)
                .collect(Collectors.toList());
    }

    public PositionDto updatePosition(String id, UpdatePositionDto dto) {
        PositionEntity existingPosition = positionRepository.findById(id).orElseThrow(() -> new PositionNotFoundException(id));

        existingPosition.setTitle(dto.getTitle());
        existingPosition.setDescription(dto.getDescription());
        existingPosition.setNumberOfPlaces(dto.getNumberOfPlaces());
        existingPosition.setSalaryRange(dto.getSalaryRange());
        existingPosition.setLanguageId(dto.getLanguageId());
        existingPosition.setStackId(dto.getStackId());
        existingPosition.setTechnologiesIds(dto.getTechnologiesIds());

        positionRepository.save(existingPosition);
        return dtoConverter.convertToDto(existingPosition);
    }

    public void deletePosition(String id) {
        PositionEntity existingPosition = positionRepository.findById(id).orElseThrow(() -> new PositionNotFoundException(id));
        positionRepository.delete(existingPosition);
    }

    public void decrementPlacesLeft(String id) {
        PositionEntity position = positionRepository.findById(id).orElseThrow(() -> new PositionNotFoundException(id));
        position.setNumberOfPlacesLeft(Math.max(0, position.getNumberOfPlacesLeft() - 1));
        positionRepository.save(position);
    }
}

