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
package com.speedment.common.codegenxml;

import com.speedment.common.codegen.*;
import com.speedment.common.codegen.provider.StandardTransformFactory;
import com.speedment.common.codegenxml.internal.view.*;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 */
public final class XmlGenerator implements Generator {

    private final Generator inner;

    public XmlGenerator() {
        inner = Generator.create(DependencyManager.create(), new XmlTransformFactory());
    }

    public static Generator forJava() {
        return Generator.forJava();
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

    private static final class XmlTransformFactory implements TransformFactory {

        private final TransformFactory inner;

        private XmlTransformFactory() {
            inner = new StandardTransformFactory(XmlTransformFactory.class.getSimpleName());
            install(Document.class, DocumentView::new);
            install(XmlDeclaration.class, XmlDeclarationView::new);
            install(DocType.class, DocTypeView::new);
            install(Attribute.class, AttributeView::new);
            install(ContentElement.class, ContentElementView::new);
            install(TagElement.class, TagElementView::new);
        }

        @Override
        public String getName() {
            return inner.getName();
        }

        @Override
        public <A, T extends Transform<A, String>> TransformFactory install(Class<A> from, Supplier<T> transformer) {
            return inner.install(from, transformer);
        }

        @Override
        public <A, B, T extends Transform<A, B>> TransformFactory install(Class<A> from, Class<B> to, Supplier<T> transformer) {
            return inner.install(from, to, transformer);
        }

        @Override
        public <A, T extends Transform<A, ?>> Set<Map.Entry<Class<?>, T>> allFrom(Class<A> from) {
            return inner.allFrom(from);
        }
    }
}
