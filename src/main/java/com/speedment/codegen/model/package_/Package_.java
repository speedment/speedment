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
package com.speedment.codegen.model.package_;

import com.speedment.codegen.model.CodeModel;
import com.speedment.codegen.model.CodeModel.Type;
import com.speedment.codegen.model.class_.Class_;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pemi
 */
public class Package_ implements CodeModel, Packagable {

    // TODO: An idea might be to let classes be part of a package rather than having
    // a package contain a number of classes. Since the primary purpose of the code
    // generator is to have each class render to a particular java file, iterating
    // over each class in a package is secondary.
    private String name_;
    private final List<Class_> classes;
    private Package_ package_;

    public Package_() {
        this.classes = new ArrayList<>();

    }

    public String getName_() {
        return name_;
    }

    public void setName_(String name_) {
        this.name_ = name_;
    }

    @Override
    public Type getType() {
        return Type.PACKAGE;
    }

    public Package_ add(Class_ class_) {
        classes.add(class_);
        return this;
    }

    @Override
    public Package_ getPackage() {
        return package_;
    }

    @Override
    public Package_ setPackage(Package_ package_) {
        this.package_ = package_;
        return this;
    }

}
