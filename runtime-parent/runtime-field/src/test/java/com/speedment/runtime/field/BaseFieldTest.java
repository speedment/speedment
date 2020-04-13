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
package com.speedment.runtime.field;

import org.junit.jupiter.api.BeforeEach;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static com.speedment.runtime.field.TestEntity.ID;
import static com.speedment.runtime.field.TestEntity.NAME;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author pemi
 */
public abstract class BaseFieldTest {

    List<TestEntity> entities;

    @BeforeEach
    void setUp() {
        List<String> names = Stream.of(null, "a", "a", "a", null, "b", "c", "d", "e", "f", "g", "h", null, "i", "j", null,
            "ab", "abc", "abcd", "abcde", "abcdef", "abcdefg",
            "Ab", "AbC", "AbCd", "AbCdE", "AbCdEf", "AbCdEfG",
            ""
        ).collect(toList());

        final AtomicInteger idCnt = new AtomicInteger();
        final AtomicInteger enumCnt = new AtomicInteger();
        final TestEntity.TestEnum[] values = Stream.concat(Stream.of(TestEntity.TestEnum.values()), Stream.of((TestEntity.TestEnum) null)).toArray(TestEntity.TestEnum[]::new);

        entities = names.stream().map(name -> new TestEntityImpl(idCnt.getAndIncrement(), name, values[enumCnt.getAndIncrement() % values.length])).collect(toList());
    }

    public List<TestEntity> collect(Predicate<TestEntity> predicate) {
        return entities.stream()
            .filter(predicate)
            .sorted(ID.comparator().thenComparing(NAME.comparatorNullFieldsFirst()))
            .collect(toList());
    }

    <T> void printList(String header, List<T> list) {
//        System.out.println("*** " + header + " ***");
//        list.forEach(System.out::println);
    }

}
