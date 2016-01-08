package com.speedment.config;

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
    
    public static final String ROOT = "project";
    
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
            return load(new String(Files.readAllBytes(location)));
        } catch (final IOException ex) {
            throw new SpeedmentException(
                "Could not load json-file from path '" + location + "'."
            );
        }
    }
    
    private DocumentTranscoder() { instanceNotAllowed(getClass()); }
}