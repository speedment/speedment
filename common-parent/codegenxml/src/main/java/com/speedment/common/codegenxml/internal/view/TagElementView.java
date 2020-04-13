/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.codegenxml.internal.view;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.Transform;
import com.speedment.common.codegenxml.TagElement;
import com.speedment.common.codegenxml.internal.view.trait.HasAttributesView;
import com.speedment.common.codegenxml.internal.view.trait.HasElementsView;
import com.speedment.common.codegenxml.internal.view.trait.HasNameView;

import java.util.Optional;

/**
 *
 * @author Per Minborg
 */
public class TagElementView implements Transform<TagElement, String>,
        HasNameView<TagElement>, 
        HasElementsView<TagElement>, 
        HasAttributesView<TagElement> {

    @Override
    public Optional<String> transform(Generator gen, TagElement model) {
        return Optional.of("<" +
            transformName(model) +
            transformAttributes(gen, model) +
            (model.elements().isEmpty() ? '/' : "") +
            '>' +
            transformElements(gen, model) +
            (model.elements().isEmpty() ? "" : new StringBuilder()
                .append("</")
                .append(transformName(model))
                .append('>'))
        );
    }
}