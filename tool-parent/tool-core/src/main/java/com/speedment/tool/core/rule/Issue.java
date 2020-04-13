/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.tool.core.rule;



/**
 * Represents an issue that has been encountered during validation of the installed
 * {@link Rule}. 
 *
 * @author Simon Jonasson
 * @since 3.0.0
 */
public interface Issue {
    /**
     * A measure of severity of the Issue
     */
    enum Level{ 
        /**
         * Indicates something the user should be aware of, but will not prevent
         * code generation
         */
        WARNING("Warning"),
        
        /**
         * Indicates a problem in the current configuration that the user has
         * to fix before code generation can be performed
         */
        ERROR("Error");
    
        private final String value;

        Level(String value){
            this.value = value;
        }
        
        @Override
        public String toString(){ return "["+value+"]"; }
    }

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
     * The severity of this issue. A {@link Level#WARNING} does not hinder code
     * generation, whereas a {@link Level#ERROR} will hinder code generation.
     * 
     * @return  the level
     */
    Level getLevel();
}
