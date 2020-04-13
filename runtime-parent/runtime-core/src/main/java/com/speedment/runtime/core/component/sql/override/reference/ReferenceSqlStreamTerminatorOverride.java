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

/**
 * An interface used for overriding a reference stream's terminating operations.
 * 
 * @author Per Minborg
 */
public interface ReferenceSqlStreamTerminatorOverride {

    <ENTITY> ForEachTerminator<ENTITY> getForEachTerminator();

    <ENTITY> void setForEachTerminator(ForEachTerminator<ENTITY> forEach);

    <ENTITY> ForEachOrderedTerminator<ENTITY> getForEachOrderedTerminator();

    <ENTITY> void setForEachOrderedTerminator(ForEachOrderedTerminator<ENTITY> forEachOrdered);

    <ENTITY> ToArrayTerminator<ENTITY> getToArrayTerminator();

    <ENTITY> void setToArrayTerminator(ToArrayTerminator<ENTITY> toArray);

    <ENTITY> ToArrayGeneratorTerminator<ENTITY> getToArrayGeneratorTerminator();

    <ENTITY> void setToArrayGeneratorTerminator(ToArrayGeneratorTerminator<ENTITY> toArray);

    <ENTITY> ReduceTerminator<ENTITY> getReduceTerminator();

    <ENTITY> void setReduceTerminator(ReduceTerminator<ENTITY> reduce);

    <ENTITY> ReduceIdentityTerminator<ENTITY> getReduceIdentityTerminator();

    <ENTITY> void setReduceIdentityTerminator(ReduceIdentityTerminator<ENTITY> reduce);

    <ENTITY> ReduceIdentityCombinerTerminator<ENTITY> getReduceIdentityCombinerTerminator();

    <ENTITY> void setReduceIdentityCombinerTerminator(ReduceIdentityCombinerTerminator<ENTITY> reduce);

    <ENTITY> CollectTerminator<ENTITY> getCollectTerminator();

    <ENTITY> void setCollectTerminator(CollectTerminator<ENTITY> collect);

    <ENTITY> CollectSupplierAccumulatorCombinerTerminator<ENTITY> getCollectSupplierAccumulatorCombinerTerminator();

    <ENTITY> void setCollectSupplierAccumulatorCombinerTerminator(CollectSupplierAccumulatorCombinerTerminator<ENTITY> collect);

    <ENTITY> MinTerminator<ENTITY> getMinTerminator();

    <ENTITY> void setMinTerminator(MinTerminator<ENTITY> min);

    <ENTITY> MaxTerminator<ENTITY> getMaxTerminator();

    <ENTITY> void setMaxTerminator(MaxTerminator<ENTITY> max);

    <ENTITY> AnyMatchTerminator<ENTITY> getAnyMatchTerminator();

    <ENTITY> void setAnyMatchTerminator(AnyMatchTerminator<ENTITY> anyMatch);

    <ENTITY> AllMatchTerminator<ENTITY> getAllMatchTerminator();

    <ENTITY> void setAllMatchTerminator(AllMatchTerminator<ENTITY> allMatch);

    <ENTITY> NoneMatchTerminator<ENTITY> getNoneMatchTerminator();

    <ENTITY> void setNoneMatchTerminator(NoneMatchTerminator<ENTITY> allMatch);

    <ENTITY> FindFirstTerminator<ENTITY> getFindFirstTerminator();

    <ENTITY> void setFindFirstTerminator(FindFirstTerminator<ENTITY> findFirst);

    <ENTITY> FindAnyTerminator<ENTITY> getFindAnyTerminator();

    <ENTITY> void setFindAnyTerminator(FindAnyTerminator<ENTITY> findAny);

    <ENTITY> CountTerminator<ENTITY> getCountTerminator();

    <ENTITY> void setCountTerminator(CountTerminator<ENTITY> count);

    <ENTITY> SpliteratorTerminator<ENTITY> getSpliteratorTerminator();

    <ENTITY> void setSpliteratorTerminator(SpliteratorTerminator<ENTITY> spliterator);

    <ENTITY> IteratorTerminator<ENTITY> getIteratorTerminator();

    <ENTITY> void setIteratorTerminator(IteratorTerminator<ENTITY> iterator);

}
