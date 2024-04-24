package ru.tsu.hits.userservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.tsu.hits.userservice.model.GroupEntity;

public interface GroupRepository extends JpaRepository<GroupEntity, String> {
    Page<GroupEntity> findAll(Pageable pageable);
}
