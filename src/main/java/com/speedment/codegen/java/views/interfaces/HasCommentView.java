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
package com.speedment.codegen.java.views.interfaces;

import static com.speedment.codegen.Formatting.EMPTY;
import static com.speedment.codegen.Formatting.nl;
import com.speedment.codegen.base.Generator;
import com.speedment.codegen.base.Transform;
import com.speedment.codegen.lang.interfaces.HasComment;
import static java.util.stream.Collectors.joining;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 * @param <M>
 */
public interface HasCommentView<M extends HasComment<M>> extends Transform<M, String> {
    
    default String renderComment(Generator g, M model) {
        return model.getComment().map(comment -> 
            Stream.of(comment.split(nl()))
                .map(row -> "// " + row)
                .collect(joining(nl()))
        ).orElse(EMPTY);
    }
}