/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.core.internal.field;

import org.junit.Before;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static com.speedment.runtime.core.internal.field.Entity.ID;
import static com.speedment.runtime.core.internal.field.Entity.NAME;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author pemi
 */
public class BaseFieldTest {

    protected List<String> names;
    protected List<Entity> entities;

    public BaseFieldTest() {
    }

    @Before
    public void setUp() {
        names = Stream.of(null, "a", "a", "a", null, "b", "c", "d", "e", "f", "g", "h", null, "i", "j", null,
            "ab", "abc", "abcd", "abcde", "abcdef", "abcdefg",
            "Ab", "AbC", "AbCd", "AbCdE", "AbCdEf", "AbCdEfG",
            ""
        ).collect(toList());

        final AtomicInteger id = new AtomicInteger();
        entities = names.stream().map(name -> new EntityImpl(id.getAndIncrement(), name)).collect(toList());
    }

    protected List<Entity> collect(Predicate<Entity> predicate) {
        return entities.stream()
            .filter(predicate)
            .sorted(ID.comparator().thenComparing(NAME.comparatorNullFieldsFirst()))
            .collect(toList());
    }

    protected <T> void printList(String header, List<T> list) {
//        System.out.println("*** " + header + " ***");
//        list.forEach(System.out::println);
    }

}
