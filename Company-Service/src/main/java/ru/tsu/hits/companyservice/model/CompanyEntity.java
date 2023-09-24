package ru.tsu.hits.companyservice.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "companies")
public class CompanyEntity {

    @Id
    private String id = UUID.randomUUID().toString();

    private String name;

    private String description;

    private String address;

    private String contacts;

    private String websiteURL;

    private String logoURL;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

