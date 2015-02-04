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
package com.speedment.codegen.model.javadoc;

import com.speedment.codegen.Adder;
import java.util.Objects;
import java.util.function.Consumer;

/**
 *
 * @author pemi
 */
public class JavadocAdder<T> extends Javadoc_ implements Adder<T> {

    final T parent;
    final Consumer<JavadocAdder<T>> updater;

    public JavadocAdder(T parent, Consumer<JavadocAdder<T>> updater) {
        super();
        this.parent = Objects.requireNonNull(parent);
        this.updater = Objects.requireNonNull(updater);
    }

    @Override
    public T add() {
        updater.accept(this);
        return parent;
    }

    @Override
    public JavadocAdder<T> add(CharSequence text) {
        super.add(text);
        return this;
    }

    @Override
    public JavadocAdder<T> add(Tag tag, CharSequence text) {
        super.add(tag, text);
        return this;
    }

    @Override
    public JavadocAdder<T> return_(CharSequence text) {
        return_(text);
        return this;
    }

    @Override
    public JavadocAdder<T> param_(CharSequence text) {
        super.param_(text);
        return this;
    }

    @Override
    public JavadocAdder<T> throws_(CharSequence text) {
        super.throws_(text);
        return this;
    }

    @Override
    public JavadocAdder<T> throws_(Class<? extends Exception> throwClass, CharSequence additionalText) {
        super.throws_(throwClass, additionalText);
        return this;
    }

}
