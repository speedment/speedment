/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
import com.speedment.db.crud.Create;
import com.speedment.db.crud.Delete;
import com.speedment.db.crud.Read;
import com.speedment.db.crud.Result;
import com.speedment.db.crud.Update;
import com.speedment.exception.SpeedmentException;
import com.speedment.component.CrudHandlerComponent;
import com.speedment.license.Software;
import static java.util.Objects.requireNonNull;
import java.util.function.Function;
import java.util.stream.Stream;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 */
public final class CrudHandlerComponentImpl extends InternalOpenSourceComponent implements CrudHandlerComponent {

    private CreateMethod creator;
    private UpdateMethod updater;
    private DeleteMethod deleter;
    private ReadMethod reader;

    public CrudHandlerComponentImpl(Speedment speedment) {
        super(speedment);
    }

    @Override
    public CrudHandlerComponent setCreator(CreateMethod creator) {
        this.creator = requireNonNull(creator);
        return this;
    }

    @Override
    public CrudHandlerComponent setUpdater(UpdateMethod updater) {
        this.updater = requireNonNull(updater);
        return this;
    }

    @Override
    public CrudHandlerComponent setDeleter(DeleteMethod deleter) {
        this.deleter = requireNonNull(deleter);
        return this;
    }

    @Override
    public CrudHandlerComponent setReader(ReadMethod reader) {
        this.reader = requireNonNull(reader);
        return this;
    }

    @Override
    public <T> T create(Create operation, Function<Result, T> mapper) throws SpeedmentException {
        return creator.apply(
            requireNonNull(operation),
            requireNonNull(mapper)
        );
    }

    @Override
    public <T> T update(Update operation, Function<Result, T> mapper) throws SpeedmentException {
        return updater.apply(
            requireNonNull(operation),
            requireNonNull(mapper)
        );
    }

    @Override
    public <T> void delete(Delete operation) throws SpeedmentException {
        deleter.apply(requireNonNull(operation));
    }

    @Override
    public <T> Stream<T> read(Read operation, Function<Result, T> mapper) throws SpeedmentException {
        return reader.apply(
            requireNonNull(operation),
            requireNonNull(mapper)
        );
    }
    
    @Override
    public Stream<Software> getDependencies() {
        return Stream.empty();
    }
}
