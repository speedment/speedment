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
import com.speedment.util.CharSequences;
import com.speedment.util.Trees;
import com.speedment.util.Trees.Order;
import java.util.stream.Collectors;

/**
 *
 * @author pemi
 */
public class Package_ implements CodeModel, Packagable {

    private CharSequence name;
    private Package_ package_;

    public Package_() {
    }

    public Package_(final String name) {
        this.name = name;
    }

    public CharSequence getName() {
        return name;
    }

    public Package_ setName(final CharSequence name) {
        this.name = name;
        return this;
    }

    @Override
    public Type getModelType() {
        return Type.PACKAGE;
    }

    @Override
    public Package_ getPackage() {
        return package_;
    }

    @Override
    public Package_ setPackage(final Package_ package_) {
        this.package_ = package_;
        return this;
    }

    public static final Package_ by(CharSequence packagePath) {
        return make(null, packagePath);
    }

    private static Package_ make(Package_ parent, CharSequence cs) {
        if (cs.length() == 0) {
            return parent;
        }
        int dotIndex = CharSequences.indexOf(cs, '.');
        final Package_ result = new Package_().setPackage(parent);
        if (dotIndex == CharSequences.INDEX_NOT_FOUND) {
            return result.setName(cs);
        }
        return make(result.setName(cs.subSequence(0, dotIndex)), cs.subSequence(dotIndex + 1, cs.length()));
    }

    @Override
    public String toString() {
        return Trees.walk(this, Package_::getPackage, Order.BACKWARD).map(Package_::getName).collect(Collectors.joining("."));
    }

}
