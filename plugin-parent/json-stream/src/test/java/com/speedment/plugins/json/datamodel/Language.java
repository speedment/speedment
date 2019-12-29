package com.speedment.plugins.json;

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
