package ru.tsu.hits.stackservice.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
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
    private List<LanguageEntity> relatedLanguages;

    @ManyToMany(mappedBy = "relatedStacks")
    private List<TechnologyEntity> relatedTechnologies;
}
