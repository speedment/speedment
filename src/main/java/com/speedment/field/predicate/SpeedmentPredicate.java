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
package com.speedment.field.predicate;

import com.speedment.annotation.Api;
import java.util.function.Predicate;

/**
 *
 * @author pemi
 * @param <ENTITY> Entity type
 * @param <V> Value type
 */
@Api(version = "2.2")
public interface SpeedmentPredicate<ENTITY, V> extends
    HasGetter<ENTITY, V>,
    HasPredicateType,
    HasEffectivePredicateType,
    HasFieldTrait,
    HasReferenceFieldTrait<ENTITY, V>,
    HasNegated,
    Predicate<ENTITY> {

    @Override
    SpeedmentPredicate<ENTITY, V> negate();
}