package com.example;

import com.speedment.example.PersonImpl;

public final class PersonFactory {
    
    private final com.speedment.example.Person.Builder builder;
    
    public PersonFactory() {
        this.builder = new PersonImpl.Builder();
    }
    
    public com.speedment.example.Person create(long id, String firstname, String lastname) {
        return builder
            .withId(id)
            .withFirstname(firstname)
            .withLastname(lastname)
            .build();
    }
}