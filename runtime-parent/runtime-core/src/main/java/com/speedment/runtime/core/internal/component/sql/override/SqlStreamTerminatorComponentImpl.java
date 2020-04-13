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
package com.speedment.runtime.core.internal.component.sql.override;

import com.speedment.runtime.core.component.sql.override.SqlStreamTerminatorComponent;
import com.speedment.runtime.core.component.sql.override.doubles.DoubleCountTerminator;
import com.speedment.runtime.core.component.sql.override.ints.IntCountTerminator;
import com.speedment.runtime.core.component.sql.override.longs.LongCountTerminator;
import com.speedment.runtime.core.component.sql.override.reference.AllMatchTerminator;
import com.speedment.runtime.core.component.sql.override.reference.AnyMatchTerminator;
import com.speedment.runtime.core.component.sql.override.reference.CollectSupplierAccumulatorCombinerTerminator;
import com.speedment.runtime.core.component.sql.override.reference.CollectTerminator;
import com.speedment.runtime.core.component.sql.override.reference.CountTerminator;
import com.speedment.runtime.core.component.sql.override.reference.FindAnyTerminator;
import com.speedment.runtime.core.component.sql.override.reference.FindFirstTerminator;
import com.speedment.runtime.core.component.sql.override.reference.ForEachOrderedTerminator;
import com.speedment.runtime.core.component.sql.override.reference.ForEachTerminator;
import com.speedment.runtime.core.component.sql.override.reference.IteratorTerminator;
import com.speedment.runtime.core.component.sql.override.reference.MaxTerminator;
import com.speedment.runtime.core.component.sql.override.reference.MinTerminator;
import com.speedment.runtime.core.component.sql.override.reference.NoneMatchTerminator;
import com.speedment.runtime.core.component.sql.override.reference.ReduceIdentityCombinerTerminator;
import com.speedment.runtime.core.component.sql.override.reference.ReduceIdentityTerminator;
import com.speedment.runtime.core.component.sql.override.reference.ReduceTerminator;
import com.speedment.runtime.core.component.sql.override.reference.SpliteratorTerminator;
import com.speedment.runtime.core.component.sql.override.reference.ToArrayGeneratorTerminator;
import com.speedment.runtime.core.component.sql.override.reference.ToArrayTerminator;
import com.speedment.runtime.core.internal.component.sql.override.optimized.doubles.OptimizedDoubleCountTerminator;
import com.speedment.runtime.core.internal.component.sql.override.optimized.ints.OptimizedIntCountTerminator;
import com.speedment.runtime.core.internal.component.sql.override.optimized.longs.OptimizedLongCountTerminator;
import com.speedment.runtime.core.internal.component.sql.override.optimized.reference.OptimizedCountTerminator;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 */
public final class SqlStreamTerminatorComponentImpl implements SqlStreamTerminatorComponent {

    // Reference
    private ForEachTerminator<?> forEachTerminator = ForEachTerminator.defaultTerminator();
    private ForEachOrderedTerminator<?> forEachOrderedTerminator = ForEachOrderedTerminator.defaultTerminator();
    private ToArrayTerminator<?> toArrayTerminator = ToArrayTerminator.defaultTerminator();
    private ToArrayGeneratorTerminator<?> toArrayGeneratorTerminator = ToArrayGeneratorTerminator.defaultTerminator();
    private ReduceTerminator<?> reduceTerminator = ReduceTerminator.defaultTerminator();
    private ReduceIdentityTerminator<?> reduceIdentityTerminator = ReduceIdentityTerminator.defaultTerminator();
    private ReduceIdentityCombinerTerminator<?> reduceIdentityCombinerTerminator = ReduceIdentityCombinerTerminator.defaultTerminator();
    private CollectTerminator<?> collectTerminator = CollectTerminator.defaultTerminator();
    private CollectSupplierAccumulatorCombinerTerminator<?> collectSupplierAccumulatorCombinerTerminator = CollectSupplierAccumulatorCombinerTerminator.defaultTerminator();
    private MinTerminator<?> minTerminator = MinTerminator.defaultTerminator();
    private MaxTerminator<?> maxTerminator = MaxTerminator.defaultTerminator();
    private AnyMatchTerminator<?> anyMatchTerminator = AnyMatchTerminator.defaultTerminator();
    private AllMatchTerminator<?> allMatchTerminator = AllMatchTerminator.defaultTerminator();
    private NoneMatchTerminator<?> noneMatchTerminator = NoneMatchTerminator.defaultTerminator();
    private FindFirstTerminator<?> findFirstTerminator = FindFirstTerminator.defaultTerminator();
    private FindAnyTerminator<?> findAnyTerminator = FindAnyTerminator.defaultTerminator();
    private CountTerminator<?> countTerminator = OptimizedCountTerminator.create();
    private SpliteratorTerminator<?> spliteratorTerminator = SpliteratorTerminator.defaultTerminator();
    private IteratorTerminator<?> iteratorTerminator = IteratorTerminator.defaultTerminator();
    // double
    private DoubleCountTerminator<?> doubleCountTerminator = OptimizedDoubleCountTerminator.create();
    // int
    private IntCountTerminator<?> intCountTerminator = OptimizedIntCountTerminator.create();
    // long
    private LongCountTerminator<?> longCountTerminator = OptimizedLongCountTerminator.create();

    /// Reference    
    @Override
    @SuppressWarnings("unchecked")
    public <ENTITY> ForEachTerminator<ENTITY> getForEachTerminator() {
        return (ForEachTerminator<ENTITY>) forEachTerminator;
    }

    @Override
    public <ENTITY> void setForEachTerminator(ForEachTerminator<ENTITY> forEach) {
        this.forEachTerminator = requireNonNull(forEach);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <ENTITY> ForEachOrderedTerminator<ENTITY> getForEachOrderedTerminator() {
        return (ForEachOrderedTerminator<ENTITY>) forEachOrderedTerminator;
    }

    @Override
    public <ENTITY> void setForEachOrderedTerminator(ForEachOrderedTerminator<ENTITY> forEachOrdered) {
        this.forEachOrderedTerminator = requireNonNull(forEachOrdered);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <ENTITY> ToArrayTerminator<ENTITY> getToArrayTerminator() {
        return (ToArrayTerminator<ENTITY>) toArrayTerminator;
    }

    @Override
    public <ENTITY> void setToArrayTerminator(ToArrayTerminator<ENTITY> toArray) {
        this.toArrayTerminator = requireNonNull(toArray);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <ENTITY> ToArrayGeneratorTerminator<ENTITY> getToArrayGeneratorTerminator() {
        return (ToArrayGeneratorTerminator<ENTITY>) toArrayGeneratorTerminator;
    }

    @Override
    public <ENTITY> void setToArrayGeneratorTerminator(ToArrayGeneratorTerminator<ENTITY> toArray) {
        this.toArrayGeneratorTerminator = requireNonNull(toArray);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <ENTITY> ReduceTerminator<ENTITY> getReduceTerminator() {
        return (ReduceTerminator<ENTITY>) reduceTerminator;
    }

    @Override
    public <ENTITY> void setReduceTerminator(ReduceTerminator<ENTITY> reduce) {
        this.reduceTerminator = requireNonNull(reduce);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <ENTITY> ReduceIdentityTerminator<ENTITY> getReduceIdentityTerminator() {
        return (ReduceIdentityTerminator<ENTITY>) reduceIdentityTerminator;
    }

    @Override
    public <ENTITY> void setReduceIdentityTerminator(ReduceIdentityTerminator<ENTITY> reduce) {
        this.reduceIdentityTerminator = requireNonNull(reduce);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <ENTITY> ReduceIdentityCombinerTerminator<ENTITY> getReduceIdentityCombinerTerminator() {
        return (ReduceIdentityCombinerTerminator<ENTITY>) reduceIdentityCombinerTerminator;
    }

    @Override
    public <ENTITY> void setReduceIdentityCombinerTerminator(ReduceIdentityCombinerTerminator<ENTITY> reduce) {
        this.reduceIdentityCombinerTerminator = requireNonNull(reduce);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <ENTITY> CollectTerminator<ENTITY> getCollectTerminator() {
        return (CollectTerminator<ENTITY>) collectTerminator;
    }

    @Override
    public <ENTITY> void setCollectTerminator(CollectTerminator<ENTITY> collect) {
        this.collectTerminator = requireNonNull(collect);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <ENTITY> CollectSupplierAccumulatorCombinerTerminator<ENTITY> getCollectSupplierAccumulatorCombinerTerminator() {
        return (CollectSupplierAccumulatorCombinerTerminator<ENTITY>) collectSupplierAccumulatorCombinerTerminator;
    }

    @Override
    public <ENTITY> void setCollectSupplierAccumulatorCombinerTerminator(CollectSupplierAccumulatorCombinerTerminator<ENTITY> collect) {
        this.collectSupplierAccumulatorCombinerTerminator = requireNonNull(collect);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <ENTITY> MinTerminator<ENTITY> getMinTerminator() {
        return (MinTerminator<ENTITY>) minTerminator;
    }

    @Override
    public <ENTITY> void setMinTerminator(MinTerminator<ENTITY> collect) {
        this.minTerminator = requireNonNull(collect);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <ENTITY> MaxTerminator<ENTITY> getMaxTerminator() {
        return (MaxTerminator<ENTITY>) maxTerminator;
    }

    @Override
    public <ENTITY> void setMaxTerminator(MaxTerminator<ENTITY> collect) {
        this.maxTerminator = requireNonNull(collect);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <ENTITY> AnyMatchTerminator<ENTITY> getAnyMatchTerminator() {
        return (AnyMatchTerminator<ENTITY>) anyMatchTerminator;
    }

    @Override
    public <ENTITY> void setAnyMatchTerminator(AnyMatchTerminator<ENTITY> anyMatch) {
        this.anyMatchTerminator = requireNonNull(anyMatch);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <ENTITY> AllMatchTerminator<ENTITY> getAllMatchTerminator() {
        return (AllMatchTerminator<ENTITY>) allMatchTerminator;
    }

    @Override
    public <ENTITY> void setAllMatchTerminator(AllMatchTerminator<ENTITY> allMatch) {
        this.allMatchTerminator = requireNonNull(allMatch);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <ENTITY> NoneMatchTerminator<ENTITY> getNoneMatchTerminator() {
        return (NoneMatchTerminator<ENTITY>) noneMatchTerminator;
    }

    @Override
    public <ENTITY> void setNoneMatchTerminator(NoneMatchTerminator<ENTITY> noneMatch) {
        this.noneMatchTerminator = requireNonNull(noneMatch);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <ENTITY> FindFirstTerminator<ENTITY> getFindFirstTerminator() {
        return (FindFirstTerminator<ENTITY>) findFirstTerminator;
    }

    @Override
    public <ENTITY> void setFindFirstTerminator(FindFirstTerminator<ENTITY> noneMatch) {
        this.findFirstTerminator = requireNonNull(noneMatch);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <ENTITY> FindAnyTerminator<ENTITY> getFindAnyTerminator() {
        return (FindAnyTerminator<ENTITY>) findAnyTerminator;
    }

    @Override
    public <ENTITY> void setFindAnyTerminator(FindAnyTerminator<ENTITY> noneMatch) {
        this.findAnyTerminator = requireNonNull(noneMatch);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <ENTITY> CountTerminator<ENTITY> getCountTerminator() {
        return (CountTerminator<ENTITY>) countTerminator;
    }

    @Override
    public <ENTITY> void setCountTerminator(CountTerminator<ENTITY> count) {
        this.countTerminator = requireNonNull(count);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <ENTITY> SpliteratorTerminator<ENTITY> getSpliteratorTerminator() {
        return (SpliteratorTerminator<ENTITY>) spliteratorTerminator;
    }

    @Override
    public <ENTITY> void setSpliteratorTerminator(SpliteratorTerminator<ENTITY> count) {
        this.spliteratorTerminator = requireNonNull(count);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public <ENTITY> IteratorTerminator<ENTITY> getIteratorTerminator() {
        return (IteratorTerminator<ENTITY>) iteratorTerminator;
    }

    @Override
    public <ENTITY> void setIteratorTerminator(IteratorTerminator<ENTITY> count) {
        this.iteratorTerminator = requireNonNull(count);
    }

    // double
    @Override
    @SuppressWarnings("unchecked")
    public <ENTITY> DoubleCountTerminator<ENTITY> getDoubleCountTerminator() {
        return (DoubleCountTerminator<ENTITY>) doubleCountTerminator;
    }

    @Override
    public <ENTITY> void setDoubleCountTerminator(DoubleCountTerminator<ENTITY> count) {
        this.doubleCountTerminator = requireNonNull(count);
    }

    // int
    @Override
    @SuppressWarnings("unchecked")
    public <ENTITY> IntCountTerminator<ENTITY> getIntCountTerminator() {
        return (IntCountTerminator<ENTITY>) intCountTerminator;
    }

    @Override
    public <ENTITY> void setIntCountTerminator(IntCountTerminator<ENTITY> count) {
        this.intCountTerminator = requireNonNull(count);
    }

    // long
    @Override
    @SuppressWarnings("unchecked")
    public <ENTITY> LongCountTerminator<ENTITY> getLongCountTerminator() {
        return (LongCountTerminator<ENTITY>) longCountTerminator;
    }

    @Override
    public <ENTITY> void setLongCountTerminator(LongCountTerminator<ENTITY> count) {
        this.longCountTerminator = requireNonNull(count);
    }

}
