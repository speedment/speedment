package com.speedment.runtime.typemapper.bytes;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;
import com.speedment.runtime.typemapper.TypeMapper;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.speedment.runtime.typemapper.TypeMapper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

final class ByteZeroOneToBooleanMapperTest extends AbstractTypeMapperTest<Byte, Boolean, ByteZeroOneToBooleanMapper> {

    ByteZeroOneToBooleanMapperTest() {
        super(Byte.class,
            Boolean.class,
            Category.BOOLEAN,
            ByteZeroOneToBooleanMapper::new);
    }

    @Override
    protected Map<Byte, Boolean> testVector() {
        Map<Byte, Boolean> map = new HashMap<>();
        map.put((byte) 1, true);
        map.put((byte) 0, false);
        map.put(null, null);
        return map;
    }

    @Override
    protected void getJavaType() {
        when(column().isNullable()).thenReturn(true);
        assertEquals(Boolean.class, typeMapper().getJavaType(column()));
    }
}