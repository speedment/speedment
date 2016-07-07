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
package com.speedment.common.codegen.internal;

import com.speedment.common.codegen.Meta;
import com.speedment.common.codegen.RenderTree;

import java.util.LinkedList;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 */
public final class DefaultRenderTree implements RenderTree {
    
    private final List<Meta<?, ?>> branches;

    private DefaultRenderTree(List<Meta<?, ?>> branches) {
        this.branches = requireNonNull(branches);
    }

    @Override
    public List<Meta<?, ?>> branches() {
        return branches;
    }
    
    public final static class Builder implements RenderTree.Builder {
        
        private final List<Meta<?, ?>> branches;
        
        public Builder() {
            branches = new LinkedList<>();
        }

        @Override
        public Builder withBranch(Meta<?, ?> meta) {
            branches.add(requireNonNull(meta));
            return this;
        }

        @Override
        public RenderTree build() {
            return new DefaultRenderTree(branches);
        }
    }
}