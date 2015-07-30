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
package com.speedment.util.transaction;

import com.speedment.core.manager.metaresult.MetaResult;
import com.speedment.util.PureStatic;
import java.util.function.Consumer;

/**
 *
 * @author pemi
 */
public class MetadataUtil implements PureStatic {

    /**
     * This class contains only static methods and thus, no instance shall be
     * created.
     *
     */
    private MetadataUtil() {
        instanceNotAllowed();
    }

    public static <T> Consumer<MetaResult<T>> toText(Consumer<String> consumer) {
        return meta -> {
            meta.getSqlMetaResult().ifPresent(sql -> {
                final StringBuilder sb = new StringBuilder();
                sb.append("sql = ").append(sql.getQuery()).append("\n");
                sb.append("params = ").append(sql.getParameters()).append("\n");
                sb.append("thowable = ").append(sql.getThrowable()
                    .map(t -> t.getMessage())
                    .orElse("")).append("\n");
                consumer.accept(sb.toString());
            });
        };

    }
}
