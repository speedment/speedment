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

public interface User {

    IntField<User, Integer> ID = IntField.create(ColumnIdentifier.of("speedment_test","speedment_test", "user", "id"), User::getId, User::setId, TypeMapper.primitive(), true);

    StringField<User, String> NAME = StringField.create(ColumnIdentifier.of("speedment_test","speedment_test", "user", "name"), User::getName, User::setName, TypeMapper.identity(), false);

    ByteField<User, Byte> BYTE_FIELD = ByteField.create(ColumnIdentifier.of("speedment_test","speedment_test", "user", "id"), User::getByte, User::setByte, TypeMapper.primitive(), false);
    ShortField<User, Short> SHORT_FIELD = ShortField.create(ColumnIdentifier.of("speedment_test","speedment_test", "user", "id"), User::getShort, User::setShort, TypeMapper.primitive(), false);
    LongField<User, Long> LONG_FIELD = LongField.create(ColumnIdentifier.of("speedment_test","speedment_test", "user", "id"), User::getLong, User::setLong, TypeMapper.primitive(), false);
    FloatField<User, Float> FLOAT_FIELD = FloatField.create(ColumnIdentifier.of("speedment_test","speedment_test", "user", "id"), User::getFloat, User::setFloat, TypeMapper.primitive(), false);
    DoubleField<User, Double> DOUBLE_FIELD = DoubleField.create(ColumnIdentifier.of("speedment_test","speedment_test", "user", "id"), User::getDouble, User::setDouble, TypeMapper.primitive(), false);
    CharField<User, Character> CHAR_FIELD = CharField.create(ColumnIdentifier.of("speedment_test","speedment_test", "user", "id"), User::getChar, User::setChar, TypeMapper.primitive(), false);
    BooleanField<User, Boolean> BOOLEAN_FIELD = BooleanField.create(ColumnIdentifier.of("speedment_test","speedment_test", "user", "id"), User::getBoolean, User::setBoolean, TypeMapper.primitive(), false);

    IntForeignKeyField<User, Integer, Language> LANGUAGE = IntForeignKeyField.create(ColumnIdentifier.of("speedment_test","speedment_test", "user", "id"), User::getId, User::setId, Language.ID, TypeMapper.primitive(), true);

    int getId();

    User setId(int id);

    String getName();

    User setName(String name);

    byte getByte();

    User setByte(byte val);

    short getShort();

    User setShort(short val);

    long getLong();

    User setLong(long val);

    float getFloat();

    User setFloat(float val);

    double getDouble();

    User setDouble(double val);

    char getChar();

    User setChar(char val);

    boolean getBoolean();

    User setBoolean(boolean val);

    int getLanguage();

    User setLanguage(int val);

}
