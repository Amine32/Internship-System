package ru.tsu.hits.companyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tsu.hits.companyservice.model.PositionEntity;

import java.util.List;

public interface PositionRepository extends JpaRepository<PositionEntity, String> {
    List<PositionEntity> findByCompanyId(String companyId);
}
