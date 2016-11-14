package com.speedment.example;

/**
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
public interface Person {
    
    long getId();

    String getFirstname();
    
    String getLastname();
    
    interface Builder {
        
        Builder withId(long id);
        
        Builder withFirstname(String firstname);
        
        Builder withLastname(String lastname);
        
        Person build();
        
    }
    
}