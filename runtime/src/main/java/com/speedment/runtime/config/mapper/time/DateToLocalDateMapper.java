package com.speedment.runtime.config.mapper.time;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.mapper.TypeMapper;
import java.lang.reflect.Type;
import java.sql.Date;
import java.time.LocalDate;

/**
 * A mapping from SQL's Date to Java's LocalDate.
 * <p>
 * The mapping is naive, and will not include or consider timezone or the similar.
 * Instead, the mapping will be direct: what is written in the database will
 * be directly mapped into Java.
 * <p>
 * Example: <br>
 * In database:<t> 2016-08-01<br>
 * In Java:<t> 2016-08-01
 *
 * @author Simon Jonasson
 */
public class DateToLocalDateMapper implements TypeMapper<Date, LocalDate>{

    @Override
    public String getLabel() {
        return "Date to LocalDate";
    }

    @Override
    public Type getJavaType(Column column) {
        return LocalDate.class;
    }

    @Override
    public <ENTITY> LocalDate toJavaType(Column column, Class<ENTITY> entityType, Date value) {
        return value.toLocalDate();
    }

    @Override
    public Date toDatabaseType(LocalDate value) {
        return Date.valueOf(value);
    }
    
}
