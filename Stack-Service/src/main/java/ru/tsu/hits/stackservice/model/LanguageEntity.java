package ru.tsu.hits.stackservice.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Data
@Table(name = "languages")
public class LanguageEntity {
    @Id
    private String id;

    private String name;

    @ManyToMany(mappedBy = "relatedLanguages")
    private List<PositionEntity> positions;
}
