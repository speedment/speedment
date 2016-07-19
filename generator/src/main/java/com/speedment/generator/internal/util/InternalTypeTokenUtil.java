/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.generator.internal.util;

import com.speedment.common.codegen.model.Type;
import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.internal.java.JavaGenerator;
import com.speedment.common.codegen.model.Generic;
import com.speedment.common.codegen.model.Generic.BoundType;

import com.speedment.runtime.config.typetoken.ArrayTypeToken;
import com.speedment.runtime.config.typetoken.GenericTypeToken;
import com.speedment.runtime.config.typetoken.TypeToken;
import com.speedment.runtime.exception.SpeedmentException;
import static com.speedment.runtime.util.StaticClassUtil.instanceNotAllowed;

/**
 * A factory that can create type tokens using the most fitting implementation.
 * 
 * @author  Emil Forslund
 * @author  Simon Jonasson
 * @since   3.0.0
 */
public final class InternalTypeTokenUtil {
    
    public static String render(TypeToken token) {
        final Generator gen = new JavaGenerator();
        return gen.on(toType(token)).orElseThrow(() -> new SpeedmentException(
            "Code Generator did not return any result for type token '" + token + "'."
        ));
    }
    
    public static String renderShort(TypeToken token) {
        final Generator gen = new JavaGenerator();
        final Type type = toType(token);
        gen.getDependencyMgr().load(type.getName());
        return gen.on(type).orElseThrow(() -> new SpeedmentException(
            "Code Generator did not return any result for type token '" + token + "'."
        ));
    }
    
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
                .map(InternalTypeTokenUtil::toGeneric)
                .forEachOrdered(type::add);
        }
        
        return type;
    }
    
    private static Generic toGeneric(GenericTypeToken.GenericToken gen) {
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
                .map(InternalTypeTokenUtil::toType)
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
    private InternalTypeTokenUtil() {
        instanceNotAllowed(getClass());
    }
}
