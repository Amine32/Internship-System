package ru.tsu.hits.companyservice.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
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

    private int numberOfApplications;

    private Long stackId;
    private Long languageId;
    @ElementCollection
    private List<Long> technologiesIds;


    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

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

    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyEntity company;
}

