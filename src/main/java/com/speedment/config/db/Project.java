package com.speedment.config.db;

import com.speedment.annotation.Api;
import com.speedment.config.Document;
import com.speedment.config.db.trait.HasEnabled;
import com.speedment.config.db.trait.HasMainInterface;
import com.speedment.config.db.trait.HasName;
import static com.speedment.internal.util.document.DocumentUtil.newDocument;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
@Api(version = "2.3")
public interface Project extends Document, HasEnabled, HasName, HasMainInterface {
    
    final String 
        PACKAGE_NAME     = "packageName",
        PACKAGE_LOCATION = "packageLocation",
        CONFIG_PATH      = "configPath",
        DBMSES           = "dbmses";
    

    /**
     * Returns the name of the generated package where this project will be
     * located.
     *
     * @return the name of the generated package
     */
    default String getPackageName() {
        return getAsString(PACKAGE_NAME).orElse("com.speedment.example");
    }

    /**
     * Returns where the code generated for this project will be located.
     *
     * @return the package location
     */
    default String getPackageLocation() {
        return getAsString(PACKAGE_LOCATION).orElse("src/main/java/");
    }

    /**
     * Returns the path to the groovy configuration file for this project. The
     * path may not be set at the time of the calling and the result may
     * therefore be {@code empty}.
     *
     * @return the path to the groovy configuration file
     */
    default Optional<Path> getConfigPath() {
        return getAsString(CONFIG_PATH).map(Paths::get);
    }

    default Stream<? extends Dbms> dbmses() {
        return children(DBMSES, dbmsConstructor());
    }
    
    default Dbms addNewDbms() {
        return dbmsConstructor().apply(this, newDocument(this, DBMSES));
    }
    
    BiFunction<Project, Map<String, Object>, ? extends Dbms> dbmsConstructor();

    @Override
    default Class<Project> mainInterface() {
        return Project.class;
    }
}