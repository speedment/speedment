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

import com.speedment.codegen.lang.models.Javadoc;
import com.speedment.codegen.lang.models.JavadocTag;
import com.speedment.util.Copier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 *
 * @author Emil Forslund
 */
public class JavadocImpl implements Javadoc {
	private final List<String> rows;
	private final List<JavadocTag> tags;

	public JavadocImpl() {
		rows = new ArrayList<>();
		tags = new ArrayList<>();
	}
	
	public JavadocImpl(final String text) {
		rows = Arrays.asList(text.split("\n"));
		tags = new ArrayList<>();
	}
	
	protected JavadocImpl(final Javadoc prototype) {
		rows = Copier.copy(prototype.getRows(), s -> s);
		tags = Copier.copy(prototype.getTags());
	}
	
    @Override
	public List<String> getRows() {
		return rows;
	}

    @Override
	public List<JavadocTag> getTags() {
		return tags;
	}
    
    @Override
	public JavadocImpl copy() {
		return new JavadocImpl(this);
	}

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.rows);
        hash = 47 * hash + Objects.hashCode(this.tags);
        return hash;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        return Optional.ofNullable(obj)
            .filter(o -> Javadoc.class.isAssignableFrom(o.getClass()))
            .map(o -> (Javadoc) o)
            .filter(o -> Objects.equals(getRows(), o.getRows()))
            .filter(o -> Objects.equals(getTags(), o.getTags()))
            .isPresent();
    }
}