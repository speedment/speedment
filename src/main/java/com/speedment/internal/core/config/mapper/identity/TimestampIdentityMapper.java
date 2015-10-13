package com.speedment.internal.core.config.mapper.identity;

import java.sql.Timestamp;

/**
 *
 * @author Emil Forslund
 */
public final class TimestampIdentityMapper extends AbstractIdentityMapper<Timestamp> {

    public TimestampIdentityMapper() {
        super(Timestamp.class);
    }
}