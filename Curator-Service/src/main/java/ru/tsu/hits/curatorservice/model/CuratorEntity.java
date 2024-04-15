package ru.tsu.hits.curatorservice.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "curators")
public class CuratorEntity {

    //curator id and user id will be the same
    @Id
    private String id;

    @ElementCollection
    private List<String> companyIds;
}
