package ru.tsu.hits.stackservice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Relationship {
    private String fromType;
    private Long fromId;
    private String toType;
    private Long toId;

    // Parsing method
    public static Relationship fromString(String str) {
        Relationship relationship = new Relationship();
        String cleanedStr = str.replaceAll("[\"']", ""); // Remove quotes and single quotes
        String[] parts = cleanedStr.replaceAll("[\\[\\]]", "").split(",");
        if (parts.length == 4) {
            relationship.setFromType(parts[0].trim());
            relationship.setFromId(Long.parseLong(parts[1].trim()));
            relationship.setToType(parts[2].trim());
            relationship.setToId(Long.parseLong(parts[3].trim()));
        }
        return relationship;
    }

}
