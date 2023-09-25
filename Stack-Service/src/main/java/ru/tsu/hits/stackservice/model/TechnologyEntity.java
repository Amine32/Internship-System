package ru.tsu.hits.stackservice.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "technologies")
public class TechnologyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "stack_id", nullable = false)
    private StackEntity relatedStack;

    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    private LanguageEntity relatedLanguage;
}
