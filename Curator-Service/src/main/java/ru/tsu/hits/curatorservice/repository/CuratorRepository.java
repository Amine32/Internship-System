package ru.tsu.hits.curatorservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tsu.hits.curatorservice.model.CuratorEntity;

public interface CuratorRepository extends JpaRepository<CuratorEntity, String> {
}
