package ru.tsu.hits.stackservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tsu.hits.stackservice.model.TechnologyEntity;
import ru.tsu.hits.stackservice.repository.TechnologyRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TechnologyService {

    private final TechnologyRepository technologyRepository;

    public List<TechnologyEntity> getAllTechnologies() {
        return technologyRepository.findAll();
    }

    public List<TechnologyEntity> getAllTechnologiesByIds(List<String> ids) {
        return technologyRepository.findAllById(ids);
    }
}
