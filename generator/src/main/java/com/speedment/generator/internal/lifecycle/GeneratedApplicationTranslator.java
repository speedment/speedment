package com.speedment.generator.internal.lifecycle;

import com.speedment.fika.codegen.Generator;
import com.speedment.fika.codegen.model.Interface;
import com.speedment.fika.codegen.model.File;
import com.speedment.fika.codegen.model.Type;
import com.speedment.generator.internal.DefaultJavaClassTranslator;
import com.speedment.runtime.Speedment;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.internal.runtime.AbstractApplicationBuilder;

/**
 *
 * @author Emil Forslund
 * @since  2.0.0
 */
public final class GeneratedApplicationTranslator extends DefaultJavaClassTranslator<Project, Interface> {

    private final String className = "Generated" + getSupport().typeName(getSupport().projectOrThrow()) + "Application";
    
    public GeneratedApplicationTranslator(
            Speedment speedment, 
            Generator generator, 
            Project project) {
        
        super(speedment, generator, project, Interface::of);
    }

    @Override
    protected String getClassOrInterfaceName() {
        return className;
    }

    @Override
    public boolean isInGeneratedPackage() {
        return true;
    }

    @Override
    protected Interface makeCodeGenModel(File file) {
        return newBuilder(file, className)
            .forEveryProject((clazz, project) -> {
                clazz.public_()
                    .add(Type.of(Speedment.class));
            }).build();
    }

    @Override
    protected String getJavadocRepresentText() {
        return "The generated {@link " + AbstractApplicationBuilder.class.getName() + 
            "} application interface for the {@link " + Project.class.getName() + 
            "} named " + getSupport().projectOrThrow().getName() + ".";
    }
}
