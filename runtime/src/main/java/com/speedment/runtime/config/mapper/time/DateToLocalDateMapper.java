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
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author Simon
 */
public class DateToLocalDateMapper implements TypeMapper<Date, LocalDate>{

    @Override
    public String getLabel() {
        return "Date to LocalDate";
    }

    @Override
    public <ENTITY> TypeToken getJavaType(Column column) {
        return TypeTokenFactory.create(LocalDate.class);
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
