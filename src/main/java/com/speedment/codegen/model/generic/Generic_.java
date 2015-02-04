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
package com.speedment.codegen.model.generic;

import com.speedment.codegen.Nameable;
import com.speedment.codegen.model.AbstractCodeModel;
import com.speedment.codegen.model.CodeModel;
import com.speedment.codegen.model.type.ScalarType_;
import com.speedment.codegen.model.class_.Interface_;
import com.speedment.util.stream.StreamUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public class Generic_ extends AbstractCodeModel<Generic_> implements CodeModel, Nameable, Genericable {

    private CharSequence name;
    private final List<Generic_> generics;
    private ScalarType_ extendsType;
    private List<Interface_> interfaces;
    /// Todo; Add "extends" and "super" stuff

    public Generic_() {
        this.generics = new ArrayList<>();
    }

    @Override
    public Type getModelType() {
        return Type.GENERIC_PARAMETER;
    }

    @Override
    public CharSequence getName() {
        return name;
    }

    @Override
    public Generic_ setName(CharSequence name) {
        return with(name, n -> this.name = n);

    }

    @Override
    public Genericable add(Generic_ generic) {
        return with(generic, getGenerics()::add);
    }

    @Override
    public List<Generic_> getGenerics() {
        return generics;
    }

    @Override
    public boolean hasGeneric(Generic_ generic) {
        return generics.contains(generic);
    }

    public ScalarType_ getExtendsType() {
        return extendsType;
    }

    public List<Interface_> getInterfaces() {
        return interfaces;
    }

    public Generic_ setExtendsType(ScalarType_ extendsType) {
        return with(extendsType, e -> this.extendsType = e);
    }

    @Override
    public Stream<CodeModel> stream() {
        return StreamUtil.<CodeModel>of(generics, interfaces);
    }
}
