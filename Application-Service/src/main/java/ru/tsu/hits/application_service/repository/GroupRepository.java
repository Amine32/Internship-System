package ru.tsu.hits.application_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tsu.hits.application_service.model.GroupEntity;

public interface GroupRepository extends JpaRepository<GroupEntity, String> {
}
