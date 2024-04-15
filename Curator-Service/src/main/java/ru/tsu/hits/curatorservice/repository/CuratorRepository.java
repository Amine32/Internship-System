package ru.tsu.hits.curatorservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tsu.hits.curatorservice.model.CuratorEntity;

import java.util.List;

public interface CuratorRepository extends JpaRepository<CuratorEntity, String> {

    List<CuratorEntity> findCuratorEntitiesByCompanyIdsContaining(String companyId);
}
