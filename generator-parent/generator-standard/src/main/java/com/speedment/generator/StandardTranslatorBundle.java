/*
 * Copyright (c) Emil Forslund, 2016.
 * All Rights Reserved.
 * 
 * NOTICE:  All information contained herein is, and remains
 * the property of Emil Forslund and his suppliers, if any. 
 * The intellectual and technical concepts contained herein 
 * are proprietary to Emil Forslund and his suppliers and may 
 * be covered by U.S. and Foreign Patents, patents in process, 
 * and are protected by trade secret or copyright law. 
 * Dissemination of this information or reproduction of this 
 * material is strictly forbidden unless prior written 
 * permission is obtained from Emil Forslund himself.
 */
package com.speedment.generator;

import com.speedment.common.injector.InjectBundle;
import com.speedment.generator.standard.internal.StandardTranslatorComponent;
import java.util.stream.Stream;

/**
 *
 * @author  Emil Forslund
 * @since   3.0.1
 */
public final class StandardTranslatorBundle implements InjectBundle {

    @Override
    public Stream<Class<?>> injectables() {
        return Stream.of(StandardTranslatorComponent.class);
    }

}