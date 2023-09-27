package ru.tsu.hits.stackservice.util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import ru.tsu.hits.stackservice.model.Relationship;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    public static List<String> readCsv(InputStream inputStream) throws IOException {
        List<String> items = new ArrayList<>();
        try (
                Reader reader = new InputStreamReader(inputStream);
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
        ) {
            for (CSVRecord csvRecord : csvParser) {
                String itemName = csvRecord.get(0);
                items.add(itemName);
            }
        }
        return items;
    }

    public static List<Relationship> readCsvForRelationships(InputStream inputStream) throws IOException {
        List<Relationship> relationships = new ArrayList<>();
        try (
                Reader reader = new InputStreamReader(inputStream);
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
        ) {
            for (CSVRecord csvRecord : csvParser) {
                String relationshipStr = csvRecord.get(0);  // Assuming the CSV column is named 'relationship'
                Relationship relationship = Relationship.fromString(relationshipStr);
                relationships.add(relationship);
            }
        }
        return relationships;
    }
}

