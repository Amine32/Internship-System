package ru.tsu.hits.curatorservice.model;

import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Entity
@Data
@Table(name = "curators")
public class CuratorEntity {

    //curator id and user id will be the same
    @Id
    private String id;

    @ElementCollection
    private List<String> company_ids;
}
