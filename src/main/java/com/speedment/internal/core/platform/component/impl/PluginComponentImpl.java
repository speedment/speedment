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
package com.speedment.internal.core.platform.component.impl;

import com.speedment.Speedment;
import com.speedment.component.PluginComponent;
import com.speedment.config.plugin.Plugin;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public final class PluginComponentImpl extends Apache2AbstractComponent implements PluginComponent {
    
    private final Map<String, Plugin> plugins;

    public PluginComponentImpl(Speedment speedment) {
        super(speedment);
        plugins = new ConcurrentHashMap<>();
    }

    @Override
    public void install(Supplier<Plugin> constructor) {
        final Plugin plugin = constructor.get();
        plugins.put(plugin.getName(), plugin);
    }

    @Override
    public Stream<Plugin> stream() {
        return plugins.values().stream();
    }

    @Override
    public Optional<Plugin> get(String pluginName) {
        return Optional.ofNullable(plugins.get(pluginName));
    }

    @Override
    public AbstractComponent initialize() {
        return super.initialize(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AbstractComponent resolve() {
        return super.resolve(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}