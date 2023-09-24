package ru.tsu.hits.companyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tsu.hits.companyservice.model.PositionEntity;

@Repository
public interface PositionRepository extends JpaRepository<PositionEntity, String> {
    // Additional query methods can be defined here
}
