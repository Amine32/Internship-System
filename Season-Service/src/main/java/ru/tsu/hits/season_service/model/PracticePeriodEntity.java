package ru.tsu.hits.season_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "practice_periods")
public class PracticePeriodEntity {

    @Id
    @UuidGenerator
    private String id;

    private String seasonId;

    private String title;

    private LocalDate startDate;

    private LocalDate endDate;

    private boolean isActive;
}
