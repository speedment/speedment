package com.speedment.common.codegen.constant;

import com.speedment.common.codegen.model.ClassOrInterface;
import com.speedment.common.codegen.model.File;
import java.lang.reflect.Type;
import java.util.Objects;
import static java.util.Objects.requireNonNull;

/**
 * A very simple implementation of the java {@link Type} interface.
 * 
 * @author  Emil Forslund
 * @since   2.4.1
 */
public final class SimpleType implements Type {
    
    /**
     * Creates a new {@code SimpleType} with the specified absolute class name.
     * 
     * @param typeName  the absolute type name
     * @return          the created simple type
     */
    public static SimpleType create(String typeName) {
        return new SimpleType(typeName);
    }
    
    /**
     * Creates a new {@code SimpleType} referencing the specified class in the
     * specified file. These do not have to exist yet.
     * 
     * @param file   the file to reference
     * @param clazz  the class to reference
     * @return       the new simple type
     */
    public static SimpleType create(File file, ClassOrInterface<?> clazz) {
        return create(SimpleTypeUtil.nameOf(file, clazz));
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.typeName);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        else if (obj == null) { return false; }
        else if (!(obj instanceof Type)) { return false; }
        
        final Type other = (Type) obj;
        return Objects.equals(typeName, other.getTypeName());
    }
    
    @Override
    public String toString() {
        return getTypeName();
    }
    
    private SimpleType(String typeName) {
        this.typeName = requireNonNull(typeName);
    }
    
    private final String typeName;
}
