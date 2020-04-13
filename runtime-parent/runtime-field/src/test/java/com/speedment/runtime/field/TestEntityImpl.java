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

/**
 *
 * @author pemi
 */
public class TestEntityImpl implements TestEntity {

    private Integer id;
    private String name;
    private TestEnum testEnum;

    public TestEntityImpl(Integer id, String name, TestEnum testEnum) {
        this.id = id;
        this.name = name;
        this.testEnum = testEnum;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public TestEnum getEnum() {
        return testEnum;
    }

    @Override
    public TestEntity setId(Integer id) {
        this.id = id;
        return this;
    }

    @Override
    public TestEntity setName(String name) {
        this.name = name;
        return this;
    }

    public TestEntity setEnum(TestEnum testEnum) {
        this.testEnum = testEnum;
        return this;
    }

    @Override
    public String toString() {
        return "TestEntityImpl{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", testEnum=" + testEnum +
            '}';
    }

    @Override
    public TestEntity copy() {
        return new TestEntityImpl(id, name, testEnum);
    }
}
