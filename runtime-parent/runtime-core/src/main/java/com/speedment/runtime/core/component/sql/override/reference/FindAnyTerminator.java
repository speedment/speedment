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
package com.speedment.runtime.core.component.sql.override.reference;

import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import static com.speedment.runtime.core.internal.component.sql.override.def.reference.DefaultFindAnyTerminator.DEFAULT;
import com.speedment.runtime.core.internal.manager.sql.SqlStreamTerminator;
import com.speedment.runtime.core.internal.stream.builder.pipeline.ReferencePipeline;
import java.util.Optional;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> the original stream entity source type 
 */
@FunctionalInterface
public interface FindAnyTerminator<ENTITY> extends ReferenceTerminator {

    <T> Optional<T> apply(
        SqlStreamOptimizerInfo<ENTITY> info,
        SqlStreamTerminator<ENTITY> sqlStreamTerminator,
        ReferencePipeline<T> pipeline
    );

    @SuppressWarnings("unchecked")
    static <ENTITY> FindAnyTerminator<ENTITY> defaultTerminator() {
        return (FindAnyTerminator<ENTITY>) DEFAULT;
    }

}
