package ru.tsu.hits.applicationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tsu.hits.applicationservice.model.ApplicationEntity;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<ApplicationEntity, String> {
    List<ApplicationEntity> findAllByPositionId(String positionId);

    Integer countByPositionId(String positionId);
}
