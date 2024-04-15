package ru.tsu.hits.stackservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "languages")
public class LanguageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "relatedLanguages")
    private List<StackEntity> relatedStacks = new ArrayList<>();

    @ManyToMany(mappedBy = "relatedLanguages")
    private List<TechnologyEntity> relatedTechnologies = new ArrayList<>();
}
