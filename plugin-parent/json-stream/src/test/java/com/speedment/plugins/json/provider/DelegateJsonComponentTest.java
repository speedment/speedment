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
package com.speedment.plugins.json.provider;

import com.speedment.plugins.json.*;
import com.speedment.plugins.json.datamodel.User;
import com.speedment.plugins.json.datamodel.UserImpl;
import com.speedment.plugins.json.datamodel.UserManager;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.provider.DelegateProjectComponent;
import com.speedment.runtime.field.Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.*;

final class DelegateJsonComponentTest {

    private static final int ID = 42;
    private static final String NAME = "John";

    private Project project;
    private DelegateJsonComponent instance;
    private UserManager userManager;
    private User user;

    @BeforeEach
    void setup() {
        project = TestUtil.project();
        userManager = new UserManager();
        user = new UserImpl();
        user.setId(ID);
        user.setName(NAME);
        ProjectComponent projectComponent = new DelegateProjectComponent();
        projectComponent.setProject(project);
        instance = new DelegateJsonComponent(projectComponent);
        final Set<Field<User>> fields = userManager.fields().collect(toSet());
        System.out.println("fields = " + fields);
    }

    @Test
    void a() {
        User user = null;
        UserManager userManager = new UserManager();
        userManager.fields();
    }

    @Test
    void noneOf() {
        final JsonEncoder<User> encoder = instance.noneOf(userManager);
        final String actual = encoder.apply(user);
        assertEquals("{}", actual);
    }

    @Test
    void allOf() {
        final JsonEncoder<User> encoder = instance.allOf(userManager);
        final String actual = encoder.apply(user);
        assertTrue(actual.contains("id"));
        assertTrue(actual.contains(Integer.toString(ID)));
        assertTrue(actual.contains("name"));
        assertTrue(actual.contains(NAME));
    }

    @Test
    void of() {
        final JsonEncoder<User> encoder = instance.of(userManager, User.NAME);
        final String actual = encoder.apply(user);
        assertFalse(actual.contains("id"));
        assertFalse(actual.contains(Integer.toString(ID)));
        assertTrue(actual.contains("name"));
        assertTrue(actual.contains(NAME));
    }
}