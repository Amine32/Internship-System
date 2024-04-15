package ru.tsu.hits.stackservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "stacks")
public class StackEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(
            name = "stack_languages",
            joinColumns = @JoinColumn(name = "stack_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id")
    )
    private List<LanguageEntity> relatedLanguages = new ArrayList<>();

    @ManyToMany(mappedBy = "relatedStacks")
    private List<TechnologyEntity> relatedTechnologies = new ArrayList<>();
}
