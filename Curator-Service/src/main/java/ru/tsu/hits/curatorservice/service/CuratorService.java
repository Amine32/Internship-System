package ru.tsu.hits.curatorservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsu.hits.curatorservice.dto.CuratorDto;
import ru.tsu.hits.curatorservice.dto.converter.CuratorDtoConverter;
import ru.tsu.hits.curatorservice.exception.CuratorNotFoundException;
import ru.tsu.hits.curatorservice.model.CuratorEntity;
import ru.tsu.hits.curatorservice.repository.CuratorRepository;

import javax.servlet.http.HttpServletRequest;
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
    public CuratorDto getCuratorById(String id, HttpServletRequest request) {
        CuratorEntity curatorEntity = curatorRepository.findById(id)
                .orElseThrow(() -> new CuratorNotFoundException(id));

        return curatorDtoConverter.convertEntityToDto(curatorEntity, request);
    }

    @Transactional(readOnly = true)
    public List<CuratorDto> getAllCurators(HttpServletRequest request) {
        List<CuratorEntity> curatorEntities = curatorRepository.findAll();
        return curatorEntities.stream()
                .map(curatorEntity -> curatorDtoConverter.convertEntityToDto(curatorEntity, request))
                .collect(Collectors.toList());
    }

    @Transactional
    public void addCompanyToCurator(String curatorId, String companyId) {
        CuratorEntity curatorEntity = curatorRepository.findById(curatorId)
                .orElseThrow(() -> new CuratorNotFoundException(curatorId));

        List<String> companyIds = curatorEntity.getCompany_ids();
        if (!companyIds.contains(companyId)) {
            companyIds.add(companyId);
            curatorEntity.setCompany_ids(companyIds);
            curatorRepository.save(curatorEntity);
        }
    }

    //Would require some refactoring later (make a method in the repository to find curators by companyId
    public List<CuratorDto> getCuratorsByCompanyId(String companyId, HttpServletRequest request) {
        List<CuratorEntity> curatorEntities = curatorRepository.findAll()
                .stream()
                .filter(curator -> curator.getCompany_ids().contains(companyId))
                .collect(Collectors.toList());

        return curatorEntities.stream()
                .map(curatorEntity -> curatorDtoConverter.convertEntityToDto(curatorEntity, request))
                .collect(Collectors.toList());
    }
}
