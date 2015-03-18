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
package com.speedment.codegen.lang.models.implementation;

import com.speedment.codegen.lang.models.Constructor;
import com.speedment.codegen.lang.models.Enum;
import com.speedment.codegen.lang.models.EnumConstant;
import com.speedment.util.Copier;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 *
 * @author Emil Forslund
 */
public class EnumImpl extends ClassOrInterfaceImpl<Enum> implements Enum {
	
	private final List<EnumConstant> constants;
	private final List<Constructor> constructors;
	
	public EnumImpl(String name) {
		super(name);
		constants    = new ArrayList<>();
		constructors = new ArrayList<>();
	}
	
	protected EnumImpl(Enum prototype) {
		super (prototype);
		constants    = Copier.copy(prototype.getConstants());
		constructors = Copier.copy(prototype.getConstructors());
	}

    @Override
	public List<EnumConstant> getConstants() {
		return constants;
	}
	
	@Override
	public List<Constructor> getConstructors() {
		return constructors;
	}

	@Override
	public EnumImpl copy() {
		return new EnumImpl(this);
	}

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.constants);
        hash = 97 * hash + Objects.hashCode(this.constructors);
        return hash;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        return Optional.ofNullable(obj)
            .filter(o -> super.equals(o))
            .filter(o -> Enum.class.isAssignableFrom(o.getClass()))
            .map(o -> (Enum) o)
            .filter(o -> Objects.equals(getConstants(), o.getConstants()))
            .filter(o -> Objects.equals(getConstructors(), o.getConstructors()))
            .isPresent();
    }
}