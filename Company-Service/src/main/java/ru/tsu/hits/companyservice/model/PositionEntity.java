package ru.tsu.hits.companyservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "positions")
public class PositionEntity {

    @Id
    private String id = UUID.randomUUID().toString();

    private String title;
    private String description;

    private int numberOfPlaces;
    private int numberOfPlacesLeft;
    private String salaryRange;
    private PositionStatus status;

    private Long stackId;
    private Long languageId;
    @ElementCollection
    private List<Long> technologiesIds;


    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyEntity company;

    private String searchPeriodId;

    @PrePersist
    public void prePersist() {
        this.numberOfPlacesLeft = this.numberOfPlaces;
        this.status = PositionStatus.OPEN; //Set initial status to OPEN
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

