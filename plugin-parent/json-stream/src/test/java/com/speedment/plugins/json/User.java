package com.speedment.plugins.json;

import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.field.IntField;
import com.speedment.runtime.field.StringField;
import com.speedment.runtime.typemapper.internal.IdentityTypeMapper;
import com.speedment.runtime.typemapper.primitive.PrimitiveTypeMapper;

public interface User {

    IntField<User, Integer> ID = IntField.create(ColumnIdentifier.of("speedment_test","speedment_test", "user", "id"), User::getId, User::setId, new PrimitiveTypeMapper<>(), true);

    StringField<User, String> NAME = StringField.create(ColumnIdentifier.of("speedment_test","speedment_test", "user", "name"), User::getName, User::setName, new IdentityTypeMapper<>(), false);

    int getId();

    User setId(int id);

    String getName();

    User setName(String name);

}
