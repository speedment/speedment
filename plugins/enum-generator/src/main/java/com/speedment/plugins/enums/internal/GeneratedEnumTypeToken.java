package com.speedment.plugins.enums.internal;

import com.speedment.runtime.config.typetoken.EnumTypeToken;
import java.util.ArrayList;
import static java.util.Collections.unmodifiableList;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author  Emil Forslund
 * @author  Simon Jonasson
 * @since   1.0.0
 */
public final class GeneratedEnumTypeToken implements EnumTypeToken {
    
    private final String typeName;
    private final List<String> constants;

    public GeneratedEnumTypeToken(String typeName, List<String> constants) {
        this.typeName  = requireNonNull(typeName);
        this.constants = unmodifiableList(new ArrayList<>(constants));
    }

    @Override
    public List<String> getEnumConstants() {
        return constants;
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    @Override
    public boolean isArray() {
        return false;
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public boolean isGeneric() {
        return false;
    }
}
