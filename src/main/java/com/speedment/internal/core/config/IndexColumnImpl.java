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
package com.speedment.internal.core.config;

import com.speedment.config.Index;
import com.speedment.config.IndexColumn;
import com.speedment.config.aspects.Parent;
import com.speedment.internal.core.config.aspects.ColumnableHelper;
import com.speedment.config.parameters.OrderType;
import com.speedment.internal.util.Cast;
import java.util.Optional;

/**
 *
 * @author pemi
 */
public final class IndexColumnImpl extends AbstractOrdinalConfigEntity implements IndexColumn, ColumnableHelper {

    private Index parent;
    private OrderType orderType;

    @Override
    protected void setDefaults() {
        setOrderType(OrderType.ASC);
    }

    @Override
    public OrderType getOrderType() {
        return orderType;
    }

    @Override
    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    @Override
    public void setParent(Parent<?> parent) {
        this.parent = Cast.castOrFail(parent, Index.class);
    }

    @Override
    public Optional<Index> getParent() {
        return Optional.ofNullable(parent);
    }
}
