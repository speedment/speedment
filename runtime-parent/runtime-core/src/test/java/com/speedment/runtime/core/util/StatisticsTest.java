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

package com.speedment.runtime.core.util;

import static com.speedment.runtime.core.util.StatisticsTest.MOCK_SERVER_PORT;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import com.speedment.runtime.config.Project;
import com.speedment.runtime.core.component.InfoComponent;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.util.Statistics.Event;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.mockserver.model.MediaType;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.stream.Stream;

@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = MOCK_SERVER_PORT)
final class StatisticsTest {

    static final int MOCK_SERVER_PORT = 8080;

    @TestFactory
    Stream<DynamicTest> report(MockServerClient client) throws ReflectiveOperationException {
        client
            .when(request()
                .withPath("/stats")
                .withMethod("POST")
                .withContentType(MediaType.APPLICATION_JSON_UTF_8))
            .respond(response()
                .withStatusCode(200));

        setStaticFinalField(Statistics.class, "PING_URL", "http://localhost:" + MOCK_SERVER_PORT + "/stats");

        final Project project = mock(Project.class);
        when(project.getAppId()).thenReturn("app-id");

        final InfoComponent infoComponent = mock(InfoComponent.class);
        when(infoComponent.getTitle()).thenReturn("title");
        when(infoComponent.getImplementationVersion()).thenReturn("3.2.9");

        final ProjectComponent projectComponent = mock(ProjectComponent.class);
        when(projectComponent.getProject()).thenReturn(project);

        return Arrays.stream(Event.values()).map(event -> dynamicTest(event.name(), () -> {
            when(project.dbmses()).thenReturn(Stream.empty());

            assertDoesNotThrow(() -> Statistics.report(infoComponent, projectComponent, event));
        }));
    }

    private static void setStaticFinalField(Class<?> clazz, String fieldName, Object value)
            throws ReflectiveOperationException {
        final Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);

        final Field modifiers = Field.class.getDeclaredField("modifiers");
        modifiers.setAccessible(true);
        modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, value);
    }

}
