package com.speedment.runtime.config.mapper.time;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.mapper.TypeMapper;
import com.speedment.runtime.config.typetoken.TypeToken;
import com.speedment.runtime.util.TypeTokenFactory;
import java.sql.Time;
import java.time.LocalTime;

/**
 * A mapping from SQL's Time to Java's LocalTime.
 * <p>
 * The mapping is naive, and will not include or consider timezone or the similar.
 * Instead, the mapping will be direct: what is written in the database will
 * be directly mapped into Java.
 * <p>
 * Example: <br>
 * In database:<t> 19:22:10<br>
 * In Java:<t> 19:22:10
 *
 * @author Simon Jonasson
 */
public class TimeToLocalTimeMapper implements TypeMapper<Time, LocalTime> {

    @Override
    public String getLabel() {
        return "Time to LocalTime";
    }

    @Override
    public <ENTITY> TypeToken getJavaType(Column column) {
        return TypeTokenFactory.create(LocalTime.class);
    }

    @Override
    public <ENTITY> LocalTime toJavaType(Column column, Class<ENTITY> entityType, Time value) {
        return value.toLocalTime();
    }

    @Override
    public Time toDatabaseType(LocalTime value) {
        return Time.valueOf(value);
    }

}
