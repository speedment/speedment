/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.orm.config;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import com.speedment.util.$;
import groovy.lang.Script;
import java.util.ArrayList;
import org.codehaus.groovy.control.CompilerConfiguration;

/**
 *
 * @author pemi
 */
public class GroovyTest {

    // http://docs.codehaus.org/display/GROOVY/Embedding+Groovy
    public static void main(String[] args) {
        final Binding binding = new Binding();
        binding.setVariable("foo", 2);
        //binding.setVariable("myClass", new MyClass("Olle"));

        final CompilerConfiguration configuration = new CompilerConfiguration();
        configuration.setScriptBaseClass(Project.class.getName());
        configuration.setDebug(true);
        configuration.setVerbose(true);
        configuration.setRecompileGroovySource(true);

        final GroovyShell shell = new GroovyShell(Project.class.getClassLoader(), binding, configuration);
//        GroovyShell shell = new GroovyShell(binding);

//        final Object value = shell.evaluate(script());
        Object value = shell.evaluate("println 'Hello World!'; x = 123; setName('Sven');return this");
        //Object value = shell.evaluate("println 'Hello World!'; x = 123; myClass.setVal('Sven'); return foo * 10");
//        assert value.equals(20);
        System.out.println(value);
        assert binding.getVariable("x").equals(123);

        //System.out.println(binding);
        //System.out.println(binding.getVariable("myClass"));
        System.out.println(binding.getVariables());

    }

    public static String script() {
        final $ result = new $();
        result.$(
                "println 'Hello World!';",
                "x = 123",
                "setName('Sven')",
                "return this"
        );
        return result.toString();
    }

    public static class Project extends Script {

        private String name;

        public Project() {
            log("Created");
        }

        public Project(String name) {
            this();
            this.name = name;
        }

        public String getName() {
            log("getName()");
            return name;
        }

        public void setName(String val) {
            log("setName()");
            this.name = val;
        }

        @Override
        public Object run() {
            log("run()");
            return null;
        }

        @Override
        public String toString() {
            return Project.class.getSimpleName() + " { name=\"" + name + "\"}";
        }

        private static void log(String msg) {
            System.out.println(msg);
        }

    }

}
