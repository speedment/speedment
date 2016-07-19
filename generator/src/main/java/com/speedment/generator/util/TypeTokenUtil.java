package com.speedment.generator.util;

import com.speedment.common.codegen.model.Generic;
import com.speedment.common.codegen.model.Generic.BoundType;
import com.speedment.common.codegen.model.Type;
import com.speedment.runtime.config.typetoken.ArrayTypeToken;
import com.speedment.runtime.config.typetoken.GenericTypeToken;
import com.speedment.runtime.config.typetoken.TypeToken;
import static com.speedment.runtime.util.StaticClassUtil.instanceNotAllowed;

/**
 * 
 * @author  Emil Forslund
 * @author  Simon Jonasson
 * @since   3.0.0
 */
public final class TypeTokenUtil {
    
    public static Type toType(TypeToken token) {
        final Type type = Type.of(token.getTypeName());
        
        if (token.isArray()) {
            @SuppressWarnings("unchecked")
            final ArrayTypeToken array = (ArrayTypeToken) token;
            type.setArrayDimension(array.getArrayDimension());
        }
        
        if (token.isGeneric()) {
            @SuppressWarnings("unchecked")
            final GenericTypeToken gen = (GenericTypeToken) token;
            gen.getGenericTokens().stream()
                .map(TypeTokenUtil::toGeneric)
                .forEachOrdered(type::add);
        }
        
        return type;
    }
    
    public static Generic toGeneric(GenericTypeToken.GenericToken gen) {
        final Generic generic = Generic.of();
        
        if (gen.isBound()) {
            @SuppressWarnings("unchecked")
            final GenericTypeToken.BoundGenericToken bound = 
                (GenericTypeToken.BoundGenericToken) gen;
            
            generic.setBoundType(
                bound.getBoundType() == GenericTypeToken.BoundGenericToken.BoundType.EXTENDS 
                    ? BoundType.EXTENDS : BoundType.SUPER
            );
            
            generic.setLowerBound("?");
            
            bound.getBounds().stream()
                .map(TypeTokenUtil::toType)
                .forEachOrdered(generic::add);
        } else {
            @SuppressWarnings("unchecked")
            final GenericTypeToken.UnboundGenericToken unbound = 
                (GenericTypeToken.UnboundGenericToken) gen;
            
            generic.add(toType(unbound.getTypeToken()));
        }
        
        return generic;
    }
    
    /**
     * Utility classes should not be instantiated.
     */
    private TypeTokenUtil() {
        instanceNotAllowed(getClass());
    }
}
