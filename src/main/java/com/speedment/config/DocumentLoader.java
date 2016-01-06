package com.speedment.config;

import com.speedment.config.db.Project;
import com.google.gson.Gson;
import com.speedment.internal.core.config.db.ProjectImpl;
import static com.speedment.util.StaticClassUtil.instanceNotAllowed;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


/**
 *
 * @author Emil Forslund
 */
public final class DocumentLoader {
    
    public static String save(Project project) {
        final Gson gson   = new Gson();
        final String json = gson.toJson(project);
        return json;
    }
    
    public static void save(Project project, Path location) {
        try {Files.write(location, save(project).getBytes());}
        catch (final IOException ex) {
            throw new RuntimeException(
                "Could not save json-file to path '" + location + "'."
            );
        }
    }
    
    public static Project load(String json) {
        final Gson gson = new Gson();
        return gson.fromJson(json, ProjectImpl.class);
    }
    
    public static Project load(Path location) {
        try {
            return load(new String(Files.readAllBytes(location)));
        } catch (final IOException ex) {
            throw new RuntimeException(
                "Could not save json-file to path '" + location + "'."
            );
        }
    }
    
    private DocumentLoader() { instanceNotAllowed(getClass()); }
}