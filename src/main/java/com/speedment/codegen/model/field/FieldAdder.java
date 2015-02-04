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
package com.speedment.codegen.model.field;

import com.speedment.codegen.Adder;
import com.speedment.codegen.model.annotation.Annotation_;
import com.speedment.codegen.model.modifier.FieldModifier_;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

/**
 *
 * @author pemi
 * @param <T>
 */
public class FieldAdder<T> extends Field_ implements Adder<T> {

    final T parent;
    final Consumer<FieldAdder<T>> updater;

    public FieldAdder(T parent, Consumer<FieldAdder<T>> updater) {
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
    public FieldAdder<T> public_() {
        super.public_();
        return this;
    }

    @Override
    public FieldAdder<T> protected_() {
        super.protected_();
        return this;
    }

    @Override
    public FieldAdder<T> private_() {
        super.private_();
        return this;
    }

    @Override
    public FieldAdder<T> static_() {
        super.static_();
        return this;
    }

    @Override
    public FieldAdder<T> final_() {
        super.final_();
        return this;
    }

    @Override
    public FieldAdder<T> transient_() {
        super.transient_();
        return this;
    }

    @Override
    public FieldAdder<T> volatile_() {
        super.volatile_();
        return this;
    }

    @Override
    public FieldAdder<T> set(Set<FieldModifier_> newSet) {
        super.set(newSet);
        return this;
    }

    @Override
    public FieldAdder<T> setName(CharSequence name) {
        super.setName(name);
        return this;
    }

    @Override
    public FieldAdder<T> add(Annotation_ annotation) {
        super.add(annotation);
        return this;
    }

    @Override
    public FieldAdder<T> add(FieldModifier_ firstModifier_m, FieldModifier_... restModifiers) {
        super.add(firstModifier_m, restModifiers);
        return this;
    }

}
