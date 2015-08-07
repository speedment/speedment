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

import com.speedment.codegen.lang.models.JavadocTag;
import java.util.Objects;
import java.util.Optional;

/**
 *
 * @author Emil Forslund
 */
public class JavadocTagImpl implements JavadocTag {
	private String name;
	private String value;
	private String text;
	
	public JavadocTagImpl(String name) {
		this.name  = name;
		this.value = null;
		this.text  = null;
	}
	
	public JavadocTagImpl(String name, String text) {
		this.name  = name;
		this.value = null;
		this.text  = text;
    }
	
	public JavadocTagImpl(String name, String value, String text) {
		this.name  = name;
		this.value = value;
		this.text  = text;
	}
	
	protected JavadocTagImpl(JavadocTag prototype) {
		this.name  = prototype.getName();
		this.value = prototype.getValue().orElse(null);
		this.text  = prototype.getText().orElse(null);
	}

    /**
     * {@inheritDoc}
     */
    @Override
	public Optional<String> getValue() {
		return Optional.ofNullable(value);
	}

    /**
     * {@inheritDoc}
     */
    @Override
	public JavadocTag setValue(String value) {
		this.value = value;
		return this;
	}

    /**
     * {@inheritDoc}
     */
    @Override
	public Optional<String> getText() {
		return Optional.ofNullable(text);
	}

    /**
     * {@inheritDoc}
     */
    @Override
	public JavadocTag setText(String text) {
		this.text = text;
		return this;
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public JavadocTag setName(String name) {
		this.name = name;
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
	public JavadocTagImpl copy() {
		return new JavadocTagImpl(this);
	}

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.name);
        hash = 67 * hash + Objects.hashCode(this.value);
        hash = 67 * hash + Objects.hashCode(this.text);
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        return Optional.ofNullable(obj)
            .filter(o -> JavadocTag.class.isAssignableFrom(o.getClass()))
            .map(o -> (JavadocTag) o)
            .filter(o -> Objects.equals(getName(), o.getName()))
            .filter(o -> Objects.equals(getValue(), o.getValue()))
            .filter(o -> Objects.equals(getText(), o.getText()))
            .isPresent();
    }
	
	public final static class JavadocTagConst extends JavadocTagImpl {
		
        public JavadocTagConst(String name) {
			super(name);
		}

		public JavadocTagConst(String name, String value) {
			super(name, value);
		}

		public JavadocTagConst(String name, String value, String text) {
			super(name, value, text);
		}
		
        /**
         * {@inheritDoc}
         */
		@Override
		public JavadocTag setValue(String value) {
			return copy().setValue(value);
		}
		
        /**
         * {@inheritDoc}
         */
		@Override
		public JavadocTag setText(String text) {
			return copy().setText(text);
		}

        /**
         * {@inheritDoc}
         */
		@Override
		public JavadocTag setName(String name) {
			return copy().setName(name);
		}
		
        /**
         * {@inheritDoc}
         */
		@Override
		public JavadocTagImpl copy() {
			return new JavadocTagImpl(this);
		}
	}
}
