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
import java.sql.Time;
import java.time.LocalTime;

/**
 *
 * @author Simon
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
