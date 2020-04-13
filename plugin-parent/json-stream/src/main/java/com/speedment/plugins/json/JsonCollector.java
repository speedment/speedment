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
package com.speedment.plugins.json;

import com.speedment.plugins.json.internal.JsonCollectorImpl;
import static java.util.Objects.requireNonNull;
import java.util.StringJoiner;
import java.util.stream.Collector;
import static java.util.stream.Collectors.joining;

/**
 * A specialized java {@link Collector} that converts streams of Speedment
 * entities into JSON arrays.
 * <p>
 * Example usage:  <code>
 *      app.getOrThrow(EmployeeManager.class).stream()
 *          .filter(Employee.AGE.greaterThan(35))
 *          .filter(Employee.NAME.startsWith("B"))
 *          .collect(JsonCollector.toJson(
 *              jsonComponent.allOf(employees)
 *          ));
 * </code>
 *
 * @param <ENTITY> the entity type
 *
 * @author Emil Forslund
 * @since 1.0.0
 */
public interface JsonCollector<ENTITY> extends Collector<ENTITY, StringJoiner, String> {

    /**
     * Returns a collector that calls the specified encoder for each element in
     * the stream and joins the resulting stream separated by commas and
     * surrounded by square brackets. Each element is also formatted using the
     * specified {@link JsonEncoder}.
     *
     * @param <ENTITY> the type of the stream
     * @param encoder the enocder to use
     * @return the json string
     */
    static <ENTITY> JsonCollector<ENTITY> toJson(JsonEncoder<ENTITY> encoder) {
        requireNonNull(encoder);
        return JsonCollectorImpl.collect(encoder::apply);
    }
}
