package ru.tsu.hits.companyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tsu.hits.companyservice.model.PositionEntity;

public interface PositionRepository extends JpaRepository<PositionEntity, String> {
    // Additional query methods can be defined here
}
