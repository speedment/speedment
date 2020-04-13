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
package com.speedment.runtime.application.internal.util;

import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.runtime.core.ApplicationBuilder;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public final class JpmsUtil {

    private static final Logger LOGGER = LoggerManager.getLogger(ApplicationBuilder.LogType.MODULE_SYSTEM.getLoggerName());

    private JpmsUtil() {}

    public static void logModulesIfEnabled() {
        final Map<String, List<String>> moduleNames = modules().stream()
            .map(Object::toString)
            .map(JpmsUtil::removeModuleTag)
            .collect(groupingBy(JpmsUtil::initialPath, TreeMap::new, mapping(JpmsUtil::restPath, toList())));

        LOGGER.debug("JPMS Modules: ");
        moduleNames.forEach((key, value) -> LOGGER.debug("%s.%s", key, format(value)));
    }

    private static String format(List<String> value) {
        if (value.size() == 1) {
            return value.iterator().next();
        } else {
            return value.stream().collect(Collectors.joining(", ", "[", "]"));
        }
    }

    private static Set<Object> modules() {
        try {
            final MethodHandles.Lookup lookup = MethodHandles.lookup();
            final Class<?> moduleLayerClass = Class.forName("java.lang.ModuleLayer");
            final MethodType bootType = MethodType.methodType(moduleLayerClass);
            final MethodHandle methodHandleBoot = lookup.findStatic(moduleLayerClass,"boot", bootType);
            final Object boot = methodHandleBoot.invoke();

            final MethodType modulesType = MethodType.methodType(Set.class);
            final MethodHandle methodHandleModules = lookup.findVirtual(moduleLayerClass,"modules", modulesType);
            final Object modules = methodHandleModules.invoke(boot);

            @SuppressWarnings("unchecked")
            final Set<Object> result = (Set<Object>)modules;
            return result;

        } catch (Throwable ignore) {
            // ignore this exception
        }
        return Collections.emptySet();
    }

    private static String removeModuleTag(String s) {
        final String module = "module ";
        if (s.startsWith(module)) {
            return s.substring(module.length());
        }
        return s;
    }

    private static String initialPath(String s) {
        int index = s.lastIndexOf('.');
        if (index == -1) {
            return s;
        } else if (index == 0) {
            return "?";
        } else {
            return s.substring(0, index);
        }
    }

    private static String restPath(String s) {
        int index = s.lastIndexOf('.');
        if (index == -1) {
            return "";
        } else {
            return s.substring(index + 1);
        }
    }

}
