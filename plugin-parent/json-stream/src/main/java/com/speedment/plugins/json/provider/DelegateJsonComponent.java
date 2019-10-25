package com.speedment.plugins.json.provider;

import com.speedment.plugins.json.JsonComponent;
import com.speedment.plugins.json.JsonEncoder;
import com.speedment.plugins.json.internal.JsonComponentImpl;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.field.Field;

public final class DelegateJsonComponent implements JsonComponent {

    private final JsonComponentImpl inner;

    public DelegateJsonComponent(ProjectComponent projectComponent) {
        this.inner = new JsonComponentImpl(projectComponent);
    }

    @Override
    public <ENTITY> JsonEncoder<ENTITY> noneOf(Manager<ENTITY> manager) {
        return inner.noneOf(manager);
    }

    @Override
    public <ENTITY> JsonEncoder<ENTITY> allOf(Manager<ENTITY> manager) {
        return inner.allOf(manager);
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
    @Override
    public final <ENTITY> JsonEncoder<ENTITY> of(Manager<ENTITY> manager, Field<ENTITY>... fields) {
        return inner.of(manager, fields);
    }

}
