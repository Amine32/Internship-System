package ru.tsu.hits.curatorservice.exception;

public class CuratorNotFoundException extends RuntimeException {

    public CuratorNotFoundException(String id) {
        super("Curator not found with id " + id);
    }
}
