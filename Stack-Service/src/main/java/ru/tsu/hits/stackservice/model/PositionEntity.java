package ru.tsu.hits.stackservice.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "positions")
public class PositionEntity {

    @Id
    private String id;

    private String name;

    @ManyToMany
    @JoinTable(
            name = "position_languages",
            joinColumns = @JoinColumn(name = "position_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id")
    )
    private List<LanguageEntity> relatedLanguages;
}
