package com.speedment.plugins.enums.internal;

import java.lang.reflect.Type;
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
public final class GeneratedEnumType implements Type {
    
    private final String typeName;
    private final List<String> constants;

    public GeneratedEnumType(String typeName, List<String> constants) {
        this.typeName  = requireNonNull(typeName);
        this.constants = unmodifiableList(new ArrayList<>(constants));
    }

    public List<String> getEnumConstants() {
        return constants;
    }

    @Override
    public String getTypeName() {
        return typeName;
    }
}
