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

import com.speedment.codegen.java.views.interfaces.DocumentableView;
import com.speedment.codegen.lang.models.ClassOrInterface;
import com.speedment.codegen.lang.models.File;
import com.speedment.codegen.lang.models.Import;
import com.speedment.codegen.lang.models.Javadoc;
import com.speedment.util.Copier;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 *
 * @author Emil Forslund
 */
public class FileImpl implements File {
	
	private String name;
	private Javadoc doc;
	private final List<Import> imports;
	private final List<ClassOrInterface<?>> classes;
	
	public FileImpl(String name) {
		this.name	 = name;
		this.doc	 = null;
		this.imports = new ArrayList<>();
		this.classes = new ArrayList<>();
	}
	
	protected FileImpl(File prototype) {
		this.name	 = prototype.getName();
		this.doc	 = prototype.getJavadoc().map(Copier::copy).orElse(null);
		this.imports = Copier.copy(prototype.getImports());
		this.classes = Copier.copy(prototype.getClasses(), c -> c.copy());
	}

	@Override
	public File setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public File set(Javadoc doc) {
		this.doc = doc;
		return this;
	}

	@Override
	public Optional<Javadoc> getJavadoc() {
		return Optional.ofNullable(doc);
	}

	@Override
	public List<Import> getImports() {
		return imports;
	}

	@Override
	public List<ClassOrInterface<?>> getClasses() {
		return classes;
	}

	@Override
	public FileImpl copy() {
		return new FileImpl(this);
	}

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + Objects.hashCode(this.doc);
        hash = 97 * hash + Objects.hashCode(this.imports);
        hash = 97 * hash + Objects.hashCode(this.classes);
        return hash;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        return Optional.ofNullable(obj)
            .filter(o -> File.class.isAssignableFrom(o.getClass()))
            .map(o -> (File) o)
            .filter(o -> Objects.equals(getName(), o.getName()))
            .filter(o -> Objects.equals(getJavadoc(), o.getJavadoc()))
            .filter(o -> Objects.equals(getImports(), o.getImports()))
            .filter(o -> Objects.equals(getClasses(), o.getClasses()))
            .isPresent();
    }
}