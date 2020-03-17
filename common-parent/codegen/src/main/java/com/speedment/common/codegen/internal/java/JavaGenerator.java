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
package com.speedment.common.codegen.internal.java;

import com.speedment.common.codegen.*;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A hook to the generator that can be passed to various stages in the pipeline.
 * Contains multiple methods for generating model-to-model or model-to-text.
 * <p>
 * The <code>JavaGenerator</code> comes with all the basic types
 * of the java language and the 'java.lang'-package ignored in imports and 
 * has views of all the basic language concepts preinstalled.
 * 
 * @author Emil Forslund
 */
public final class JavaGenerator implements Generator {
    
	private static final Pattern[] IGNORED = compileAll(
        "^void$",
		"^byte$",
        "^short$",
        "^char$",
        "^int$",
        "^long$",
        "^boolean$",
        "^float$",
        "^double$",
        "^java\\.lang\\.[^\\.]+$"
    );

	private final Generator inner;


    /**
     * Instantiates the JavaGenerator.
     */
    public JavaGenerator() {
        this(new JavaTransformFactory());
    }
	
    /**
     * Instantiates the JavaGenerator using an array of custom 
     * {@link TransformFactory}.
     * <p>
     * Warning! If you use this constructor, no transforms will be installed
     * by default!
     * 
     * @param factory  the transform factory to use
     */
	public JavaGenerator(TransformFactory factory) {
	    inner = Generator.create(DependencyManager.create(IGNORED), factory);
	}

    @Override
    public DependencyManager getDependencyMgr() {
        return inner.getDependencyMgr();
    }

    @Override
    public RenderStack getRenderStack() {
        return inner.getRenderStack();
    }

    @Override
    public <A, B> Stream<Meta<A, B>> metaOn(A from, Class<B> to) {
        return inner.metaOn(from, to);
    }

    @Override
    public <A, B> Stream<Meta<A, B>> metaOn(A from, Class<B> to, Class<? extends Transform<A, B>> transform) {
        return inner.metaOn(from, to, transform);
    }

    @Override
    public <M> Stream<Meta<M, String>> metaOn(M model) {
        return inner.metaOn(model);
    }

    @Override
    public <A> Stream<Meta<A, String>> metaOn(Collection<A> models) {
        return inner.metaOn(models);
    }

    @Override
    public <A, B> Stream<Meta<A, B>> metaOn(Collection<A> models, Class<B> to) {
        return inner.metaOn(models, to);
    }

    @Override
    public <A, B> Stream<Meta<A, B>> metaOn(Collection<A> models, Class<B> to, Class<? extends Transform<A, B>> transform) {
        return inner.metaOn(models, to, transform);
    }

    @Override
    public Optional<String> on(Object model) {
        return inner.on(model);
    }

    @Override
    public <M> Stream<String> onEach(Collection<M> models) {
        return inner.onEach(models);
    }

    @Override
    public <A, B> Optional<Meta<A, B>> transform(Transform<A, B> transform, A model, TransformFactory factory) {
        return inner.transform(transform, model, factory);
    }

    public static Generator create(DependencyManager mgr, TransformFactory factory) {
        return Generator.create(mgr, factory);
    }

    private static Pattern[] compileAll(String... regexp) {
        final Set<Pattern> patterns = Stream.of(regexp)
            .map(Pattern::compile)
            .collect(Collectors.toSet());

        return patterns.toArray(new Pattern[patterns.size()]);
    }
}
