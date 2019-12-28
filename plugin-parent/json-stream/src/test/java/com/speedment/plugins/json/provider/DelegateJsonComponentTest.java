package com.speedment.plugins.json.provider;

import com.speedment.plugins.json.*;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.provider.DelegateProjectComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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