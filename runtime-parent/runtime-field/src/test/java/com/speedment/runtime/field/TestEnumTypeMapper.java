package com.speedment.runtime.field;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.typemapper.TypeMapper;

import java.lang.reflect.Type;

final class TestEnumTypeMapper implements TypeMapper<String, TestEntity.TestEnum> {

    @Override
    public String getLabel() {
        return TestEnumTypeMapper.class.getName();
    }

    @Override
    public Type getJavaType(Column column) {
        return TestEntity.TestEnum.class;
    }

    @Override
    public TestEntity.TestEnum toJavaType(Column column, Class<?> entityType, String value) {
        try {
            return TestEntity.TestEnum.valueOf(value);
        } catch (Exception e) {
            return  null;
        }
    }

    @Override
    public String toDatabaseType(TestEntity.TestEnum value) {
        return value.toString();
    }
}
