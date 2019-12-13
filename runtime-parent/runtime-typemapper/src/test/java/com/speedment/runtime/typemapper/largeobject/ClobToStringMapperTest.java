package com.speedment.runtime.typemapper.largeobject;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class ClobToStringMapperTest {

    private ClobToStringMapper mapper;

    @BeforeEach
    void createMapper(){
        mapper = new ClobToStringMapper();
    }

    @Test
    void toJavaType() {
        assertEquals(String.class,mapper.getJavaType(null));
    }

}