package ru.tsu.hits.document_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "documents")
public class Document {

    @Id
    @UuidGenerator
    private String id;

    @Column(nullable = false)
    private String associatedEntityId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    private String documentType;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    //TODO add preCreate and preUpdate
}
