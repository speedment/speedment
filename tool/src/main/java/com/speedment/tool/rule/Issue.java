package com.speedment.tool.rule;

import com.speedment.runtime.annotation.Api;

/**
 * Represents an issue that has been encountered during validation of the installed
 * {@link Rule}. 
 *
 * @author Simon Jonasson
 * @since 3.0.0
 */
@Api(version="3.0")
public interface Issue {
    /**
     * A measure of severity of the Issue
     */
    enum Level{ 
        /**
         * Indicates something the user should be aware of, but will not prevent
         * code generation
         */
        WARNING("[Warning]"),
        
        /**
         * Indicates a problem in the current configuration that the user has
         * to fix before code generation can be performed
         */
        ERROR("[Error]");
    
        private final String value;
        private Level(String value){
            this.value = value;
        }
    };
    
    /**
     * The title of the issue. Consider this the Issue summary
     * 
     * @return  the title
     */
    String getTitle();
    
    /**
     * The description of the issue. This should contain instructions for 
     * the user on how to fix the issue at hand.
     * 
     * @return  the description
     */
    String getDescription();
    
    /**
     * The severity of this issue. A {@link Level.WARNING} does not hinder code
     * generation, whereas a {@link Level.ERROR} will hinder code generation.
     * 
     * @return  the level
     */
    Level getLevel();
}
