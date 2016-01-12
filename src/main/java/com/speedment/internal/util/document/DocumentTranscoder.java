/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.util.document;

import com.speedment.config.db.Project;
import com.google.gson.Gson;
import com.speedment.annotation.Api;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.core.config.db.ProjectImpl;
import static com.speedment.util.StaticClassUtil.instanceNotAllowed;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


/**
 *
 * @author Emil Forslund
 */
@Api(version = "2.3")
public final class DocumentTranscoder {
    
    public static final String ROOT = "config";
    
    public static String save(Project project) throws SpeedmentException {
        final Gson gson   = new Gson();
        final String json = gson.toJson(project);
        return json;
    }
    
    public static void save(Project project, Path location) throws SpeedmentException {
        try {Files.write(location, save(project).getBytes());}
        catch (final IOException ex) {
            throw new SpeedmentException(
                "Could not save json-file to path '" + location + "'."
            );
        }
    }
    
    public static Project load(String json) throws SpeedmentException {
        final Gson gson = new Gson();
        return gson.fromJson(json, ProjectImpl.class);
    }
    
    public static Project load(Path location) throws SpeedmentException {
        try {
            final byte[] content = Files.readAllBytes(location);
            return load(new String(content));
        } catch (final IOException ex) {
            throw new SpeedmentException(
                "Could not load json-file from path '" + location + "'."
            );
        }
    }
    
    private DocumentTranscoder() { instanceNotAllowed(getClass()); }
}