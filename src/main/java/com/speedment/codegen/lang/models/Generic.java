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
package com.speedment.codegen.lang.models;

import com.speedment.codegen.lang.interfaces.Copyable;
import com.speedment.codegen.lang.models.implementation.GenericImpl;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/**
 *
 * @author Emil Forslund
 */
public interface Generic extends Copyable<Generic> {
    
    public static enum BoundType {EXTENDS, SUPER};
    
    Generic setLowerBound(String lowerBound);
	Optional<String> getLowerBound();

    default Generic add(Type upperBound) {
		getUpperBounds().add(upperBound);
		return this;
	}
    
    List<Type> getUpperBounds();
	Generic setBoundType(BoundType type);
	BoundType getBoundType();
	Optional<Type> asType();
    
    enum Factory { INST;
        private Supplier<Generic> supplier = () -> new GenericImpl();
    }

    static Generic of() {
        return Factory.INST.supplier.get();
    }
    
    static void setSupplier(Supplier<Generic> a) {
        Factory.INST.supplier = a;
    }
}