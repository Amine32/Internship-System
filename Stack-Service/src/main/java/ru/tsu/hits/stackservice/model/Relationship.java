package ru.tsu.hits.stackservice.model;

import lombok.Data;
import lombok.Getter;

@Getter
public class Relationship {
    private String fromType;
    private Long fromId;
    private String toType;
    private Long toId;

    public void setFromType(String fromType) {
        this.fromType = fromType;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public void setToType(String toType) {
        this.toType = toType;
    }

    public void setToId(Long toId) {
        this.toId = toId;
    }

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
