package ru.tsu.hits.season_service.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "seasons")
public class InternshipSeasonEntity {

    @Id
    @UuidGenerator
    private String id;

    private String title;

    private SeasonPhase phase;

    @ElementCollection
    private Set<String> studentIds;
}
