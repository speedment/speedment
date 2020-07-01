package com.speedment.common.codegen.controller;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.constant.SimpleParameterizedType;
import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.Field;
import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.model.Value;

import java.util.Map;

class AutoImportsTest extends AbstractControllerTest {

    private static final Generator generator = Generator.forJava();

    @Override
    File createFile() {
        return File.of("com/example/DateMapper.java")
                .add(Class.of("DateMapper").public_()
                    .add(Field.of("utilDateMap", SimpleParameterizedType.create(Map.class, String.class, java.util.Date.class))
                        .private_().static_().final_()
                        .set(Value.ofReference("new HashMap<>()")))
                    .add(Field.of("sqlDateMap",  SimpleParameterizedType.create(Map.class, String.class, java.sql.Date.class))
                        .private_().static_().final_()
                        .set(Value.ofReference("new HashMap<>()"))))
                    .call(new AutoImports(generator.getDependencyMgr()))
                .call(new AlignTabs<>());
    }

    @Override
    String expected() {
        return "package com.example;\n" +
                "\n" +
                "import java.util.Date;\n" +
                "import java.util.Map;\n" +
                "\n" +
                "public class DateMapper {\n" +
                "    \n" +
                "    private static final Map<String, Date> utilDateMap = new HashMap<>();\n" +
                "    private static final Map<String, java.sql.Date> sqlDateMap = new HashMap<>();\n" +
                "}";
    }
}