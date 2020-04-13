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
package com.speedment.plugins.json.datamodel;

import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.field.*;
import com.speedment.runtime.typemapper.TypeMapper;

public interface Language {

    IntField<Language, Integer> ID = IntField.create(ColumnIdentifier.of("speedment_test","speedment_test", "user", "id"), Language::getId, Language::setId, TypeMapper.primitive(), true);

    StringField<Language, String> NAME = StringField.create(ColumnIdentifier.of("speedment_test","speedment_test", "user", "name"), Language::getName, Language::setName, TypeMapper.identity(), false);

    int getId();

    Language setId(int id);

    String getName();

    Language setName(String name);

}
