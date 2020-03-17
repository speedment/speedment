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
package com.speedment.common.injector.exception;

/**
 * Exception thrown if an Injector annotation like {@code WithState} is used in
 * a way that indicates an error in the setup of the injected class. For an
 * example, if a parameter to a method is set to have a state that occurs far
 * before or after the state of the method itself.
 *
 * @author Emil Forslund
 * @since  3.1.17
 */
public final class MisusedAnnotationException extends RuntimeException {

    private static final long serialVersionUID = 2302868474717414052L;

    public MisusedAnnotationException(String message) {
        super(message);
    }
}
