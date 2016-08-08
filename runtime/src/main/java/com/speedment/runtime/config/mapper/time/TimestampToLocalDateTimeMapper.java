package com.speedment.runtime.config.mapper.time;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.mapper.TypeMapper;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * A mapping from SQL's Timestamp to Java's LocalDateTime.
 * <p>
 * The mapping is naive, and will not include or consider timezone or the similar.
 * Instead, the mapping will be direct: what is written in the database will
 * be directly mapped into Java.
 * <p>
 * Example: <br>
 * In database:<t> '2016-08-01 09:39:45'<br>
 * In Java:<t> 2016-08-01T09:39:45
 *
 * @author Simon Jonasson
 */
public class TimestampToLocalDateTimeMapper implements TypeMapper<Timestamp, LocalDateTime>{

    @Override
    public String getLabel() {
        return "Timestamp to LocalDateTime";
    }

    @Override
    public Type getJavaType(Column column) {
        return LocalDateTime.class;
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
