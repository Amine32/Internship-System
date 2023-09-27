package ru.tsu.hits.stackservice.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
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
