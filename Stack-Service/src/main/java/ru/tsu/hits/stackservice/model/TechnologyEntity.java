package ru.tsu.hits.stackservice.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "technologies")
public class TechnologyEntity {

    @Id
    private String id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "position_id", nullable = false)
    private PositionEntity relatedPosition;

    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    private LanguageEntity relatedLanguages;
}
