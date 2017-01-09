package com.speedment.generator.core.translator;

import com.speedment.common.codegen.Meta;
import com.speedment.common.codegen.model.File;
import com.speedment.common.injector.Injector;
import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.generator.core.internal.translator.TranslatorManagerHelper;
import com.speedment.generator.translator.TranslatorManager;
import com.speedment.runtime.config.Project;
import java.nio.file.Path;

/**
 *
 * @author Emil Forslund
 * @since  3.0.2
 */
public abstract class AbstractTranslatorManager implements TranslatorManager {
    
    private final TranslatorManagerHelper helper;
    
    protected AbstractTranslatorManager() {
        helper = new TranslatorManagerHelper();
    }
    
    @ExecuteBefore(State.INITIALIZED)
    void init(Injector injector) {
        // Since we created the instance of 'helper' manually, we have to
        // invoke the injector manually. We can do this in the "INITIALIZED" 
        // phase since we don't need access to any components and we want to 
        // simulate that this happends on construction.
        injector.inject(helper);
    }

    @Override
    public void accept(Project project) {
        helper.accept(this, project);
    }

    @Override
    public void clearExistingFiles(Project project) {
        helper.clearExistingFiles(this, project);
    }

    @Override
    public void writeToFile(Project project, Meta<File, String> meta, boolean overwriteExisting) {
        helper.writeToFile(this, project, meta, overwriteExisting);
    }

    @Override
    public void writeToFile(Project project, String filename, String content, boolean overwriteExisting) {
        helper.writeToFile(this, project, filename, content, overwriteExisting);
    }

    @Override
    public void writeToFile(Path location, String content, boolean overwriteExisting) {
        helper.writeToFile(this, location, content, overwriteExisting);
    }

    @Override
    public int getFilesCreated() {
        return helper.getFilesCreated();
    }
}