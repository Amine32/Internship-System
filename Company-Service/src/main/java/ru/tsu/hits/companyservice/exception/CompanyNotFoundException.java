package ru.tsu.hits.companyservice.exception;

public class CompanyNotFoundException extends EntityNotFoundException {
    public CompanyNotFoundException(String id) {
        super("Company", id);
    }
}
