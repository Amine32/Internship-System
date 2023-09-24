package ru.tsu.hits.companyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tsu.hits.companyservice.model.CompanyEntity;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, String> {
    // Additional query methods can be defined here
}
