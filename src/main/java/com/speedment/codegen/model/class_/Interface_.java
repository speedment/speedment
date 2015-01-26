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
package com.speedment.codegen.model.class_;

import com.speedment.codegen.model.modifier.InterfaceModifier_;

/**
 *
 * @author Duncan
 */
public class Interface_ extends ClassAndInterfaceBase<Interface_, InterfaceModifier_> {

    public Interface_() {
        super(InterfaceModifier_.class);
    }

    @Override
    public Type getModelType() {
        return Type.INTERFACE;
    }

    public Interface_ abstract_() {
        add(InterfaceModifier_.ABSTRACT);
        return this;
    }

    public Interface_ private_() {
        add(InterfaceModifier_.PRIVATE);
        return this;
    }

    public Interface_ protected_() {
        add(InterfaceModifier_.PROTECTED);
        return this;
    }

    public Interface_ public_() {
        add(InterfaceModifier_.PUBLIC);
        return this;
    }

    public Interface_ static_() {
        add(InterfaceModifier_.STATIC);
        return this;
    }

    public Interface_ strictfp_() {
        add(InterfaceModifier_.STRICTFP);
        return this;
    }

}
