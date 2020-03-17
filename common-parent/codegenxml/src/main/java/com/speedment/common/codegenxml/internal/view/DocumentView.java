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
import com.speedment.common.codegen.util.Formatting;
import com.speedment.common.codegenxml.Document;
import com.speedment.common.codegenxml.internal.view.trait.HasElementsView;

import java.util.Optional;
import java.util.stream.Stream;

import static com.speedment.common.codegen.util.CollectorUtil.joinIfNotEmpty;

/**
 *
 * @author Per Minborg
 */
public final class DocumentView implements Transform<Document, String>,
    HasElementsView<Document> {

    @Override
    public boolean useIndent() {
        return false;
    }

    @Override
    public Optional<String> transform(Generator gen, Document model) {
        return Optional.of(
            Stream.of(
                gen.onEach(model.preamble()).map(s -> useIndent() ? Formatting.indent(s) : s).collect(joinIfNotEmpty(Formatting.nl(), "", "")),
                model.getXmlDeclaration().flatMap(gen::on).orElse(""),
                model.getDocType().flatMap(gen::on).orElse(""),
                transformElements(gen, model)
            ).filter(s -> !s.isEmpty())
            .collect(joinIfNotEmpty(Formatting.nl(), "", ""))
        );
    }
}
