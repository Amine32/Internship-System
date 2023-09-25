package ru.tsu.hits.stackservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tsu.hits.stackservice.model.StackEntity;

public interface StackRepository extends JpaRepository<StackEntity, Long> {
}
