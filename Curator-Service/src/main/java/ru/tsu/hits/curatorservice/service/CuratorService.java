package ru.tsu.hits.curatorservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsu.hits.curatorservice.dto.CuratorDto;
import ru.tsu.hits.curatorservice.dto.converter.CuratorDtoConverter;
import ru.tsu.hits.curatorservice.exception.CuratorNotFoundException;
import ru.tsu.hits.curatorservice.model.CuratorEntity;
import ru.tsu.hits.curatorservice.repository.CuratorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CuratorService {

    private final CuratorRepository curatorRepository;
    private final CuratorDtoConverter curatorDtoConverter;

    @Transactional
    public void handleCuratorCreatedEvent(String id) {
        CuratorEntity entity = new CuratorEntity();
        entity.setId(id);
        curatorRepository.save(entity);
    }

    @Transactional
    public void handleCuratorDeletedEvent(String id) {
        curatorRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public CuratorDto getCuratorById(String id) {
        CuratorEntity curatorEntity = curatorRepository.findById(id)
                .orElseThrow(() -> new CuratorNotFoundException(id));

        return curatorDtoConverter.convertEntityToDto(curatorEntity);
    }

    @Transactional(readOnly = true)
    public List<CuratorDto> getAllCurators() {
        List<CuratorEntity> curatorEntities = curatorRepository.findAll();
        return curatorEntities.stream()
                .map(curatorDtoConverter::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void addCompanyToCurator(String curatorId, String companyId) {
        CuratorEntity curatorEntity = curatorRepository.findById(curatorId)
                .orElseThrow(() -> new CuratorNotFoundException(curatorId));

        List<String> companyIds = curatorEntity.getCompanyIds();
        if (!companyIds.contains(companyId)) {
            companyIds.add(companyId);
            curatorEntity.setCompanyIds(companyIds);
            curatorRepository.save(curatorEntity);
        }
    }

    @Transactional
    public void removeCompanyFromCurator(String curatorId, String companyId) {
        CuratorEntity curatorEntity = curatorRepository.findById(curatorId)
                .orElseThrow(() -> new CuratorNotFoundException(curatorId));

        List<String> companyIds = curatorEntity.getCompanyIds();
        if (companyIds.contains(companyId)) {
            companyIds.remove(companyId);
            curatorEntity.setCompanyIds(companyIds);
            curatorRepository.save(curatorEntity);
        }
    }

    //Would require some refactoring later (make a method in the repository to find curators by companyId
    @Transactional(readOnly = true)
    public List<CuratorDto> getCuratorsByCompanyId(String companyId) {
        List<CuratorEntity> curatorEntities = curatorRepository.findCuratorEntitiesByCompanyIdsContaining(companyId);

        return curatorEntities.stream()
                .map(curatorDtoConverter::convertEntityToDto)
                .collect(Collectors.toList());
    }
}
