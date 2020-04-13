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
package com.speedment.tool.core.internal.component;

import com.speedment.common.rest.Rest;
import com.speedment.runtime.core.component.InfoComponent;
import com.speedment.tool.core.component.VersionComponent;
import com.speedment.tool.core.exception.SpeedmentToolException;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * The default implementation of the {@link VersionComponent} interface that 
 * uses the public GitHub API to establish the latest released version.
 * 
 * @author  Emil Forslund
 * @since   3.0.0
 */
public final class VersionComponentImpl implements VersionComponent {

    private final InfoComponent info;

    public VersionComponentImpl(InfoComponent info) {
        this.info = requireNonNull(info);
    }

    @Override
    @SuppressWarnings("unchecked")
    public CompletableFuture<String> latestVersion() {
        return Rest.connectHttps("service.speedment.com")
            .get(format("version/%s/latest", info.getRepository()))
            .thenApplyAsync(res -> {
                if (res.success()) {
                    return res.decodeJson()
                        .map(m -> (Map<String, String>) m)
                        .map(m -> m.get("tag"))
                        .orElseThrow(() -> new SpeedmentToolException(
                            "Could not establish the latest version."
                        ));
                } else {
                    throw new SpeedmentToolException(
                        "Received an error '" + res.getText() + 
                        "' from the GitHub API."
                    );
                }
            });
    }
}