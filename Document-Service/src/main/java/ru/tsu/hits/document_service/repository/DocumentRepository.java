package ru.tsu.hits.document_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tsu.hits.document_service.model.Document;

public interface DocumentRepository extends JpaRepository<Document, String> {
}
