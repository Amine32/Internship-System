package ru.tsu.hits.userservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.tsu.hits.userservice.model.Role;
import ru.tsu.hits.userservice.model.UserEntity;


public interface UserRepository extends JpaRepository<UserEntity, String> {

    UserEntity findByEmail(String email);

    Page<UserEntity> findAllByRolesContains(Role role, Pageable pageable);
}
