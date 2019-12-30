package com.speedment.plugins.enums.internal.ui;

import com.speedment.common.injector.annotation.Config;
import com.speedment.common.json.Json;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.core.ApplicationMetadata;
import com.speedment.tool.core.exception.SpeedmentToolException;

import java.util.Map;

import static java.util.Objects.requireNonNull;

final class TempApplicationMetadata implements ApplicationMetadata {

    private final String json;

    public TempApplicationMetadata(@Config(name="temp.json", value="") String json) {
        this.json = requireNonNull(json);
    }

    @Override
    public Project makeProject() {
        try {
            @SuppressWarnings("unchecked") final Map<String, Object> data =
                (Map<String, Object>) Json.fromJson(json);
            return Project.create(data);
        } catch (final Exception ex) {
            throw new SpeedmentToolException(
                "Error deserializing temporary project JSON.", ex
            );
        }
    }
}
