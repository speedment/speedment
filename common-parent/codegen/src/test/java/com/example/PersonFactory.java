/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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