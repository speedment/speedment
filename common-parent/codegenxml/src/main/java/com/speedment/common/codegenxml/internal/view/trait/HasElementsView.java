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
package com.speedment.common.codegenxml.internal.view.trait;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.util.Formatting;
import com.speedment.common.codegenxml.ContentElement;
import com.speedment.common.codegenxml.Element;
import com.speedment.common.codegenxml.trait.HasElements;

import java.util.List;

import static com.speedment.common.codegen.util.CollectorUtil.joinIfNotEmpty;

/**
 *
 * @author Per Minborg
 * @param <T>  the type of the main view
 */
public interface HasElementsView<T extends HasElements<T>> {

    default boolean useIndent() {
        return true;
    }

    default String transformElements(Generator gen, T model) {
        final List<Element> elements = model.elements();
        if (elements.size() == 1 && (elements.get(0) instanceof ContentElement)) {
            return gen.on(elements.get(0)).orElse("");
        } else {
            return gen.onEach(elements)
                .map(s -> useIndent() ? Formatting.indent(s) : s)
                .collect(joinIfNotEmpty(Formatting.nl(), Formatting.nl(), Formatting.nl()));
        }
    }

}
