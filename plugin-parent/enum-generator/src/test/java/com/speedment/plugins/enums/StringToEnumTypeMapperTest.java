package com.speedment.plugins.enums;

import com.speedment.common.injector.Injector;
import com.speedment.common.json.Json;
import com.speedment.generator.translator.provider.StandardJavaLanguageNamer;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.util.DocumentDbUtil;
import com.speedment.runtime.config.util.DocumentTranscoder;
import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.typemapper.TypeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.nio.file.Paths;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class StringToEnumTypeMapperTest {

    private static final String COLUMN_NAME = "name";

    private Column column;
    private Injector injector;
    private Project project;

    private StringToEnumTypeMapper<TestEnum> instance;

    public enum TestEnum {ONE {
        public String toDatabase() { return "One";};
    }, TWO, THREE}

    @BeforeEach
    void setup() throws InstantiationException {
        project = projectHelper();
        column = DocumentDbUtil.referencedColumn(project,"speedment_test","speedment_test", "user", COLUMN_NAME);

        injector = Injector.builder().withComponent(StandardJavaLanguageNamer.class).build();
        instance = new StringToEnumTypeMapper<>();
        injector.inject(instance);
    }

    @Test
    void getLabel() {
        assertTrue(instance.getLabel().toLowerCase().contains("enum"));
    }

    @Test
    void isToolApplicable() {
        assertTrue(instance.isToolApplicable());
    }

    @Test
    void getJavaType() {
        injector.inject(instance);
    }

    @Test
    void getJavaTypeCategory() {
        assertEquals(TypeMapper.Category.ENUM, instance.getJavaTypeCategory(column));
    }

    @Test
    void toJavaType() {
        final Type type = instance.getJavaType(column);
        assertTrue(type.getTypeName().toLowerCase().contains(COLUMN_NAME.toLowerCase()));
    }

    @Test
    void toDatabaseType() {
        assertEquals("One", instance.toDatabaseType(TestEnum.ONE));
    }

    private Project projectHelper() throws SpeedmentException {
        final Project p = DocumentTranscoder.load(Paths.get("src", "test", "resources", "speedment.json"), this::fromJson);
        return p;
    }

    private Map<String, Object> fromJson(String json) {
        @SuppressWarnings("unchecked")
        final Map<String, Object> parsed = (Map<String, Object>) Json.fromJson(json);
        return parsed;
    }

}