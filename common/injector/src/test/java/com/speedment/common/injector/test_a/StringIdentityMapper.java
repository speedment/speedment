package com.speedment.common.injector.test_a;

import static com.speedment.common.injector.State.RESOLVED;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.Inject;

/**
 *
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class StringIdentityMapper implements TypeMapper<String, String> {

    @ExecuteBefore(RESOLVED)
    protected void install(@Inject(RESOLVED) TypeMapperComponent mappers) {
        mappers.install(String.class, String.class, this);
    }
    
    @Override
    public String toDatabase(String value) {
        return value;
    }

    @Override
    public String toJava(String value) {
        return value;
    }
    
}
