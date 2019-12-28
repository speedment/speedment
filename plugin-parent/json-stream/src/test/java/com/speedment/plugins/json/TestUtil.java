package com.speedment.plugins.json;

import com.speedment.common.json.Json;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.util.DocumentTranscoder;
import com.speedment.runtime.core.exception.SpeedmentException;

import java.nio.file.Paths;
import java.util.Map;

public final class TestUtil {
    private TestUtil() {}

    public static Project project() throws SpeedmentException {
        final Project p = DocumentTranscoder.load(Paths.get("src", "test", "resources", "speedment.json"), TestUtil::fromJson);
        return p;
    }

    private static Map<String, Object> fromJson(String json) {
        @SuppressWarnings("unchecked")
        final Map<String, Object> parsed = (Map<String, Object>) Json.fromJson(json);
        return parsed;
    }

}