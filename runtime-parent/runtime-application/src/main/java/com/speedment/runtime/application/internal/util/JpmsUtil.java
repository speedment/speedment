package com.speedment.runtime.application.internal.util;

import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.runtime.core.ApplicationBuilder;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.*;

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
        moduleNames.forEach((key, value) -> LOGGER.debug("%s %s", key, value));
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
