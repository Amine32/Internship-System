package ru.tsu.hits.practice_service.model;

import lombok.Getter;

@Getter
public enum Semester {
    FIFTH(5),
    SIXTH(6),
    SEVENTH(7),
    EIGHTH(8);

    private final int semesterNumber;

    // Constructor
    Semester(int semesterNumber) {
        this.semesterNumber = semesterNumber;
    }

    // Static method to get Semester by number
    public static Semester fromNumber(int number) {
        for (Semester s : values()) {
            if (s.getSemesterNumber() == number) {
                return s;
            }
        }
        throw new IllegalArgumentException("No matching semester for number: " + number);
    }

    @Override
    public String toString() {
        return "Semester " + semesterNumber;
    }
}

