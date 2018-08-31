package com.speedment.runtime.config;

import com.speedment.common.json.Json;
import com.speedment.common.mapbuilder.MapBuilder;
import com.speedment.runtime.config.internal.resolver.NewDocumentResolverImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static com.speedment.common.mapbuilder.MapBuilder.mapBuilderTyped;
import static com.speedment.runtime.config.util.DocumentLoaders.jsonLoader;
import static java.util.Arrays.asList;

/**
 * @author Emil Forslund
 * @since  3.1.7
 */
public class LoadDefaultModelTest {

    private NewDocumentResolverImpl resolver;

    @Before
    public void setUp() {
        resolver = new NewDocumentResolverImpl(jsonLoader());
    }

    @Test
    public void loadCustomProject() {
        final Map<String, Object> project = resolver.resolve(newMap()
            .key("extends").value("/com/speedment/runtime/config/Project.json")
            .key("id").value("test_project")
            .key("dbmses").value(asList(
                newMap()
                    .key("id").value("test_dbms")
                    .key("schemas").value(asList(
                        newMap()
                            .key("id").value("test_schema")
                            .build()
                    ))
                    .build()
            ))
            .build()
        );

        System.out.println(Json.toJson(project));
        System.out.println("...");
        System.out.println(Json.toJson(resolver.normalize(project)));
    }


    private static MapBuilder<String, Object> newMap() {
        return mapBuilderTyped(String.class, Object.class);
    }
}
