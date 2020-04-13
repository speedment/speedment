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
package com.speedment.runtime.field.expression;

import com.speedment.runtime.compute.ToBigDecimal;
import com.speedment.runtime.compute.ToBigDecimalNullable;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * Specific {@link FieldMapper} implementation that also implements
 * {@link ToBigDecimalNullable}.
 *
 * @param <ENTITY>  the entity type
 * @param <T>       the column type before mapped
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface FieldToBigDecimal<ENTITY, T>
extends FieldMapper<ENTITY, T, BigDecimal, ToBigDecimal<ENTITY>, Function<T, BigDecimal>>,
        ToBigDecimalNullable<ENTITY> {}