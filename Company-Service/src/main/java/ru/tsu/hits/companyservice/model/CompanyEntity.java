package ru.tsu.hits.companyservice.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
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

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PositionEntity> positions;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

