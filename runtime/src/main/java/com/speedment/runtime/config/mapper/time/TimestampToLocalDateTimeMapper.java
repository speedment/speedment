/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.runtime.config.mapper.time;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.mapper.TypeMapper;
import com.speedment.runtime.config.typetoken.TypeToken;
import com.speedment.runtime.util.TypeTokenFactory;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author Simon
 */
public class TimestampToLocalDateTimeMapper implements TypeMapper<Timestamp, LocalDateTime>{

    @Override
    public String getLabel() {
        return "Timestamp to LocalDateTime";
    }

    @Override
    public <ENTITY> TypeToken getJavaType(Column column) {
        return TypeTokenFactory.create(LocalDateTime.class);
    }

    @Override
    public <ENTITY> LocalDateTime toJavaType(Column column, Class<ENTITY> entityType, Timestamp value) {
        return value == null ? null : value.toLocalDateTime();
    }

    @Override
    public Timestamp toDatabaseType(LocalDateTime value) {
        return value == null ? null : Timestamp.valueOf(value);
    }
    
}
