/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.codegen.lang.models.constants;

import com.speedment.codegen.lang.models.Type;
import com.speedment.codegen.lang.models.implementation.GenericImpl;
import com.speedment.codegen.lang.models.implementation.TypeImpl.TypeConst;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 *
 * @author Emil Forslund
 */
public abstract class DefaultType {
	private DefaultType() {}

	public static final Type
		BYTE_PRIMITIVE = new TypeConst(byte.class),
		SHORT_PRIMITIVE = new TypeConst(short.class),
		INT_PRIMITIVE = new TypeConst(int.class),
		LONG_PRIMITIVE = new TypeConst(long.class),
		FLOAT_PRIMITIVE = new TypeConst(float.class),
		DOUBLE_PRIMITIVE = new TypeConst(double.class),
		BOOLEAN_PRIMITIVE = new TypeConst(boolean.class),
		CHAR_PRIMITIVE = new TypeConst(char.class),
		BYTE = new TypeConst(Byte.class),
		SHORT = new TypeConst(Short.class),
		INT = new TypeConst(Integer.class),
		LONG = new TypeConst(Long.class),
		FLOAT = new TypeConst(Float.class),
		DOUBLE = new TypeConst(Double.class),
		BOOLEAN = new TypeConst(Boolean.class),
		CHARACTER = new TypeConst(Character.class),
		STRING = new TypeConst(String.class),
		OBJECT = new TypeConst(Object.class),
		VOID = new TypeConst("void"),
		WILDCARD = new TypeConst("?"),
		LIST = new TypeConst(List.class),
		SET = new TypeConst(Set.class),
		MAP = new TypeConst(Map.class),
		QUEUE = new TypeConst(Queue.class),
		STACK = new TypeConst(Stack.class),
		OPTIONAL = new TypeConst(Optional.class),
		ENTRY = new TypeConst(HashMap.Entry.class),
		FUNCTION = new TypeConst(Function.class),
		PREDICATE = new TypeConst(Predicate.class),
		CONSUMER = new TypeConst(Consumer.class);
		
	public static final Type list(Type innerType) {
		return LIST.add(new GenericImpl().add(innerType));
	}
	
	public static final Type set(Type innerType) {
		return SET.add(new GenericImpl().add(innerType));
	}
	
	public static final Type map(Type innerTypeA, Type innerTypeB) {
		return MAP.add(new GenericImpl().add(innerTypeA)).add(new GenericImpl(innerTypeB));
	}
	
	public static final Type queue(Type innerType) {
		return QUEUE.add(new GenericImpl().add(innerType));
	}
	
    public static final Type stack(Type innerType) {
        return STACK.add(new GenericImpl().add(innerType));
    }
    
    public static final Type optional(Type innerType) {
        return OPTIONAL.add(new GenericImpl().add(innerType));
    }
    
    public static final Type entry(Type innerTypeA, Type innerTypeB) {
        return ENTRY.add(new GenericImpl().add(innerTypeA).add(innerTypeB));
    }
    
    public static final Type function(Type innerTypeA, Type innerTypeB) {
        return FUNCTION.add(new GenericImpl().add(innerTypeA).add(innerTypeB));
    }
    
    public static final Type predicate(Type innerType) {
        return PREDICATE.add(new GenericImpl().add(innerType));
    }
    
    public static final Type consumer(Type innerType) {
        return CONSUMER.add(new GenericImpl().add(innerType));
    }
    
    public static boolean isVoid(Type type) {
        return type == null || VOID.equals(type) || "void".equals(type.getName());
    }
}