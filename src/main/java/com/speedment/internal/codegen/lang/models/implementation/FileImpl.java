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
package com.speedment.internal.codegen.lang.models.implementation;

import com.speedment.internal.codegen.lang.models.ClassOrInterface;
import com.speedment.internal.codegen.lang.models.File;
import com.speedment.internal.codegen.lang.models.Import;
import com.speedment.internal.codegen.lang.models.Javadoc;
import com.speedment.internal.codegen.util.Copier;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import static java.util.Objects.requireNonNull;
import java.util.Optional;

/**
 * This is the default implementation of the {@link File} interface.
 * This class should not be instantiated directly. Instead you should call the
 * {@link File#of(java.lang.String)} method to get an instance. In that way, 
 * you can layer change the implementing class without modifying the using code.
 * 
 * @author Emil Forslund
 * @see    File
 */
public final class FileImpl implements File {
	
	private String name;
	private Javadoc doc;
	private final List<Import> imports;
	private final List<ClassOrInterface<?>> classes;
	
    /**
     * Initializes this file using a name.
     * <p>
     * <b>Warning!</b> This class should not be instantiated directly but using 
     * the {@link File#of(java.lang.String)} method!
     * 
     * @param name  the filename
     */
	public FileImpl(String name) {
		this.name	 = requireNonNull(name);
		this.doc	 = null;
		this.imports = new ArrayList<>();
		this.classes = new ArrayList<>();
	}
	
    /**
     * Copy constructor.
     * 
     * @param prototype  the prototype
     */
	protected FileImpl(File prototype) {
		this.name	 = requireNonNull(prototype).getName();
		this.doc	 = prototype.getJavadoc().map(Copier::copy).orElse(null);
		this.imports = Copier.copy(prototype.getImports());
		this.classes = Copier.copy(prototype.getClasses(), c -> c.copy());
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public File setName(String name) {
		this.name = requireNonNull(name);
		return this;
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public String getName() {
		return name;
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public File set(Javadoc doc) {
		this.doc = doc;
		return this;
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public Optional<Javadoc> getJavadoc() {
		return Optional.ofNullable(doc);
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public List<Import> getImports() {
		return imports;
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public List<ClassOrInterface<?>> getClasses() {
		return classes;
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public FileImpl copy() {
		return new FileImpl(this);
	}

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + Objects.hashCode(this.doc);
        hash = 97 * hash + Objects.hashCode(this.imports);
        hash = 97 * hash + Objects.hashCode(this.classes);
        return hash;
    }

    /**
     * {@inheritDoc}
     */
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