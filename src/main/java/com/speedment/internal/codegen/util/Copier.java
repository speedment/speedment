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
package com.speedment.internal.codegen.util;

import com.speedment.internal.codegen.lang.interfaces.Copyable;
import static com.speedment.internal.util.StaticClassUtil.instanceNotAllowed;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 */
public final class Copier {
    
	public static <T extends Copyable<T>> T copy(T prototype) {
        return prototype == null ? null : prototype.copy();
    }
    
    public static <T extends Copyable<T>> Optional<T> copy(Optional<T> prototype) {
		return Copier.copy(prototype, c -> c.copy());
	}
	
	public static <T> Optional<T> copy(Optional<T> prototype, Function<T, T> copier) {
		if (prototype.isPresent()) {
			return Optional.of(
				requireNonNull(copier).apply(prototype.get())
			);
		} else {
			return Optional.empty();
		}
	}
	
	public static <T extends Copyable<T>> List<T> copy(List<T> prototype) {
		return Copier.copy(requireNonNull(prototype), c -> c.copy());
	}
	
	public static <T> List<T> copy(List<T> prototype, Function<T, T> copier) {
		return copy(requireNonNull(prototype), copier, new ArrayList<>());
	}
	
	public static <T extends Copyable<T>> Set<T> copy(Set<T> prototype) {
		return Copier.copy(requireNonNull(prototype), c -> c.copy());
	}

	public static <T> Set<T> copy(Set<T> prototype, Function<T, T> copier) {
		return copy(requireNonNull(prototype), copier, new HashSet<>());
	}
	
	public static <T, L extends Collection<T>> L copy(Collection<T> prototype, Function<T, T> copier, L empty) {
		requireNonNull(prototype).forEach(e -> empty.add(copier.apply(e)));
		return empty;
	}
    
    /**
     * Utility classes should not be instantiated.
     */
    private Copier() { instanceNotAllowed(getClass()); }
}