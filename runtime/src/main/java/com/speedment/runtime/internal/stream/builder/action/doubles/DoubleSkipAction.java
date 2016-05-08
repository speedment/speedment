/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.internal.stream.builder.action.doubles;

import static com.speedment.runtime.internal.stream.builder.action.StandardBasicAction.SKIP;
import com.speedment.runtime.internal.stream.builder.action.trait.HasSkip;
import com.speedment.runtime.stream.action.Action;
import java.util.stream.DoubleStream;

/**
 *
 * @author pemi
 */
public final class DoubleSkipAction extends Action<DoubleStream, DoubleStream> implements HasSkip {

    private final long skip;

    public DoubleSkipAction(long n) {
        super(s -> s.skip(n), DoubleStream.class, SKIP);
        this.skip = n;
    }

    @Override
    public long getSkip() {
        return skip;
    }

}
