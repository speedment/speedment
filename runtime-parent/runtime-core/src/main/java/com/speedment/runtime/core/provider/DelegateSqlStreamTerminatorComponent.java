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
package com.speedment.runtime.core.provider;

import com.speedment.runtime.core.component.sql.override.SqlStreamTerminatorComponent;
import com.speedment.runtime.core.component.sql.override.doubles.DoubleCountTerminator;
import com.speedment.runtime.core.component.sql.override.ints.IntCountTerminator;
import com.speedment.runtime.core.component.sql.override.longs.LongCountTerminator;
import com.speedment.runtime.core.component.sql.override.reference.*;
import com.speedment.runtime.core.internal.component.sql.override.SqlStreamTerminatorComponentImpl;

/**
 *
 * @author Per Minborg
 */
public final class DelegateSqlStreamTerminatorComponent implements SqlStreamTerminatorComponent {

    private final SqlStreamTerminatorComponent inner;

    public DelegateSqlStreamTerminatorComponent() {
        this.inner = new SqlStreamTerminatorComponentImpl();
    }

    @Override
    public <ENTITY> DoubleCountTerminator<ENTITY> getDoubleCountTerminator() {
        return inner.getDoubleCountTerminator();
    }

    @Override
    public <ENTITY> void setDoubleCountTerminator(DoubleCountTerminator<ENTITY> count) {
        inner.setDoubleCountTerminator(count);
    }

    @Override
    public <ENTITY> IntCountTerminator<ENTITY> getIntCountTerminator() {
        return inner.getIntCountTerminator();
    }

    @Override
    public <ENTITY> void setIntCountTerminator(IntCountTerminator<ENTITY> count) {
        inner.setIntCountTerminator(count);
    }

    @Override
    public <ENTITY> LongCountTerminator<ENTITY> getLongCountTerminator() {
        return inner.getLongCountTerminator();
    }

    @Override
    public <ENTITY> void setLongCountTerminator(LongCountTerminator<ENTITY> count) {
        inner.setLongCountTerminator(count);
    }

    @Override
    public <ENTITY> ForEachTerminator<ENTITY> getForEachTerminator() {
        return inner.getForEachTerminator();
    }

    @Override
    public <ENTITY> void setForEachTerminator(ForEachTerminator<ENTITY> forEach) {
        inner.setForEachTerminator(forEach);
    }

    @Override
    public <ENTITY> ForEachOrderedTerminator<ENTITY> getForEachOrderedTerminator() {
        return inner.getForEachOrderedTerminator();
    }

    @Override
    public <ENTITY> void setForEachOrderedTerminator(ForEachOrderedTerminator<ENTITY> forEachOrdered) {
        inner.setForEachOrderedTerminator(forEachOrdered);
    }

    @Override
    public <ENTITY> ToArrayTerminator<ENTITY> getToArrayTerminator() {
        return inner.getToArrayTerminator();
    }

    @Override
    public <ENTITY> void setToArrayTerminator(ToArrayTerminator<ENTITY> toArray) {
        inner.setToArrayTerminator(toArray);
    }

    @Override
    public <ENTITY> ToArrayGeneratorTerminator<ENTITY> getToArrayGeneratorTerminator() {
        return inner.getToArrayGeneratorTerminator();
    }

    @Override
    public <ENTITY> void setToArrayGeneratorTerminator(ToArrayGeneratorTerminator<ENTITY> toArray) {
        inner.setToArrayGeneratorTerminator(toArray);
    }

    @Override
    public <ENTITY> ReduceTerminator<ENTITY> getReduceTerminator() {
        return inner.getReduceTerminator();
    }

    @Override
    public <ENTITY> void setReduceTerminator(ReduceTerminator<ENTITY> reduce) {
        inner.setReduceTerminator(reduce);
    }

    @Override
    public <ENTITY> ReduceIdentityTerminator<ENTITY> getReduceIdentityTerminator() {
        return inner.getReduceIdentityTerminator();
    }

    @Override
    public <ENTITY> void setReduceIdentityTerminator(ReduceIdentityTerminator<ENTITY> reduce) {
        inner.setReduceIdentityTerminator(reduce);
    }

    @Override
    public <ENTITY> ReduceIdentityCombinerTerminator<ENTITY> getReduceIdentityCombinerTerminator() {
        return inner.getReduceIdentityCombinerTerminator();
    }

    @Override
    public <ENTITY> void setReduceIdentityCombinerTerminator(ReduceIdentityCombinerTerminator<ENTITY> reduce) {
        inner.setReduceIdentityCombinerTerminator(reduce);
    }

    @Override
    public <ENTITY> CollectTerminator<ENTITY> getCollectTerminator() {
        return inner.getCollectTerminator();
    }

    @Override
    public <ENTITY> void setCollectTerminator(CollectTerminator<ENTITY> collect) {
        inner.setCollectTerminator(collect);
    }

    @Override
    public <ENTITY> CollectSupplierAccumulatorCombinerTerminator<ENTITY> getCollectSupplierAccumulatorCombinerTerminator() {
        return inner.getCollectSupplierAccumulatorCombinerTerminator();
    }

    @Override
    public <ENTITY> void setCollectSupplierAccumulatorCombinerTerminator(CollectSupplierAccumulatorCombinerTerminator<ENTITY> collect) {
        inner.setCollectSupplierAccumulatorCombinerTerminator(collect);
    }

    @Override
    public <ENTITY> MinTerminator<ENTITY> getMinTerminator() {
        return inner.getMinTerminator();
    }

    @Override
    public <ENTITY> void setMinTerminator(MinTerminator<ENTITY> min) {
        inner.setMinTerminator(min);
    }

    @Override
    public <ENTITY> MaxTerminator<ENTITY> getMaxTerminator() {
        return inner.getMaxTerminator();
    }

    @Override
    public <ENTITY> void setMaxTerminator(MaxTerminator<ENTITY> max) {
        inner.setMaxTerminator(max);
    }

    @Override
    public <ENTITY> AnyMatchTerminator<ENTITY> getAnyMatchTerminator() {
        return inner.getAnyMatchTerminator();
    }

    @Override
    public <ENTITY> void setAnyMatchTerminator(AnyMatchTerminator<ENTITY> anyMatch) {
        inner.setAnyMatchTerminator(anyMatch);
    }

    @Override
    public <ENTITY> AllMatchTerminator<ENTITY> getAllMatchTerminator() {
        return inner.getAllMatchTerminator();
    }

    @Override
    public <ENTITY> void setAllMatchTerminator(AllMatchTerminator<ENTITY> allMatch) {
        inner.setAllMatchTerminator(allMatch);
    }

    @Override
    public <ENTITY> NoneMatchTerminator<ENTITY> getNoneMatchTerminator() {
        return inner.getNoneMatchTerminator();
    }

    @Override
    public <ENTITY> void setNoneMatchTerminator(NoneMatchTerminator<ENTITY> allMatch) {
        inner.setNoneMatchTerminator(allMatch);
    }

    @Override
    public <ENTITY> FindFirstTerminator<ENTITY> getFindFirstTerminator() {
        return inner.getFindFirstTerminator();
    }

    @Override
    public <ENTITY> void setFindFirstTerminator(FindFirstTerminator<ENTITY> findFirst) {
        inner.setFindFirstTerminator(findFirst);
    }

    @Override
    public <ENTITY> FindAnyTerminator<ENTITY> getFindAnyTerminator() {
        return inner.getFindAnyTerminator();
    }

    @Override
    public <ENTITY> void setFindAnyTerminator(FindAnyTerminator<ENTITY> findAny) {
        inner.setFindAnyTerminator(findAny);
    }

    @Override
    public <ENTITY> CountTerminator<ENTITY> getCountTerminator() {
        return inner.getCountTerminator();
    }

    @Override
    public <ENTITY> void setCountTerminator(CountTerminator<ENTITY> count) {
        inner.setCountTerminator(count);
    }

    @Override
    public <ENTITY> SpliteratorTerminator<ENTITY> getSpliteratorTerminator() {
        return inner.getSpliteratorTerminator();
    }

    @Override
    public <ENTITY> void setSpliteratorTerminator(SpliteratorTerminator<ENTITY> spliterator) {
        inner.setSpliteratorTerminator(spliterator);
    }

    @Override
    public <ENTITY> IteratorTerminator<ENTITY> getIteratorTerminator() {
        return inner.getIteratorTerminator();
    }

    @Override
    public <ENTITY> void setIteratorTerminator(IteratorTerminator<ENTITY> iterator) {
        inner.setIteratorTerminator(iterator);
    }
}
