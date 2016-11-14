package com.speedment.example;

/**
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class PersonImpl implements Person {

    private final long id;
    private final String firstname;
    private final String lastname;

    private PersonImpl(long id, String firstname, String lastname) {
        this.id        = id;
        this.firstname = firstname;
        this.lastname  = lastname;
    }
    
    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getFirstname() {
        return firstname;
    }

    @Override
    public String getLastname() {
        return lastname;
    }
    
    public final static class Builder implements Person.Builder {
        
        private long id;
        private String firstname;
        private String lastname;

        @Override
        public Builder withId(long id) {
            this.id = id;
            return this;
        }
        
        @Override
        public Builder withFirstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        @Override
        public Builder withLastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        @Override
        public Person build() {
            return new PersonImpl(id, firstname, lastname);
        }
    }
}