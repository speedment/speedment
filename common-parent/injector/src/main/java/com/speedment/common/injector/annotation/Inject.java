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
package com.speedment.common.injector.annotation;

import java.lang.annotation.*;

/**
 * Annotates a field that should be set automatically using dependency
 * injection. If this annotation is put on a constructor, then that constructor
 * will be used when the dependency injector instantiates the class. If multiple
 * constructors have the {@code @Inject} annotation, then the priority between
 * them is unspecified. You should therefore use the {@link OnlyIfMissing}
 * annotation to indicate which constructor should have precedence if some types
 * are missing in the dependency injector.
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
@Inherited
@Target({ElementType.FIELD, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
public @interface Inject {}