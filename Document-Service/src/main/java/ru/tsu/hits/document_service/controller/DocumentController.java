package ru.tsu.hits.document_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.tsu.hits.document_service.model.Document;
import ru.tsu.hits.document_service.service.DocumentService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("/upload")
    public Document uploadDocument(@RequestParam("file") MultipartFile file,
                                   @RequestParam("entityId") String entityId,
                                   @RequestParam("type") String type,
                                   @RequestParam("title") String title) throws IOException {
        return documentService.storeDocument(file, entityId, type, title);
    }

    @GetMapping("/download/{documentId}")
    public Resource downloadDocument(@PathVariable String documentId) throws MalformedURLException {
        Path file = documentService.loadFile(documentId);
        Resource resource = new UrlResource(file.toUri());
        if (resource.exists() || resource.isReadable()) {
            return resource;
        }
        else
            throw new MalformedURLException("File not found");
    }
}
