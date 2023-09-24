package ru.tsu.hits.companyservice.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "positions")
public class PositionEntity {

    @Id
    private String id = UUID.randomUUID().toString();

    private String title;
    private String description;

    @ElementCollection
    @CollectionTable(name = "requirements", joinColumns = @JoinColumn(name = "position_id"))
    private List<String> requirements;

    private int numberOfPlaces;
    private int numberOfPlacesLeft;
    private String salaryRange;
    private String status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.numberOfPlacesLeft = this.numberOfPlaces;
        this.status = "OPEN";
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyEntity company;
}

