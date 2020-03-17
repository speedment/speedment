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
package com.speedment.runtime.core.component.sql.override.longs;

import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import static com.speedment.runtime.core.internal.component.sql.override.def.longs.DefaultLongCountTerminator.DEFAULT;
import com.speedment.runtime.core.internal.manager.sql.SqlStreamTerminator;
import com.speedment.runtime.core.internal.stream.builder.pipeline.LongPipeline;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> the original stream entity source type 
 */
@FunctionalInterface
public interface LongCountTerminator<ENTITY> extends LongTerminator {

    long apply(
        SqlStreamOptimizerInfo<ENTITY> info,
        SqlStreamTerminator<ENTITY> sqlStreamTerminator,
        LongPipeline pipeline
    );

    @SuppressWarnings("unchecked")
    static <ENTITY> LongCountTerminator<ENTITY> defaultTerminator() {
        return (LongCountTerminator<ENTITY>) DEFAULT;
    }

}
