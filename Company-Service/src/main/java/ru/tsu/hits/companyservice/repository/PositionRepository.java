package ru.tsu.hits.companyservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.tsu.hits.companyservice.model.PositionEntity;

public interface PositionRepository extends JpaRepository<PositionEntity, String> {
    Page<PositionEntity> findAllByCompanyId(String companyId, Pageable pageable);
    Page<PositionEntity> findAllBySearchPeriodId(String searchPeriodId, Pageable pageable);
}
