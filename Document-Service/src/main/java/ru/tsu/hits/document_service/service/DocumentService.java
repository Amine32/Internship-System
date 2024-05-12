package ru.tsu.hits.document_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.tsu.hits.document_service.model.Document;
import ru.tsu.hits.document_service.repository.DocumentRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;

    private final Path rootLocation = Paths.get("document_storage");

    public Document storeDocument(MultipartFile file, String entityId, String type, String title) throws IOException {
        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Files.copy(file.getInputStream(), this.rootLocation.resolve(filename));
        Document document = new Document();
        document.setAssociatedEntityId(entityId);
        document.setDocumentType(type);
        document.setTitle(title);
        document.setFilePath(this.rootLocation.resolve(filename).toString());
        documentRepository.save(document);
        return document;
    }

    public Path loadFile(String documentId) {
        Document document = documentRepository.findById(documentId).orElseThrow(() -> new RuntimeException("File not found"));
        return Paths.get(document.getFilePath());
    }
}
