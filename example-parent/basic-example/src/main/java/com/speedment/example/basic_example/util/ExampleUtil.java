/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.oracle_java_magazine.util;

import com.company.sakila.SakilaApplication;
import com.company.sakila.SakilaApplicationBuilder;
import com.speedment.runtime.core.ApplicationBuilder.LogType;
import java.util.Scanner;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 */
public final class ExampleUtil {

    private static final String DEFAULT_PASSWORD = "sakila-password";

    @SafeVarargs
    public static SakilaApplication buildApplication(UnaryOperator<SakilaApplicationBuilder>... operators) {

        System.out.println("Connecting to a MySQL database on 'localhost:3306'");
        System.out.println("Enter password (<return> = '" + DEFAULT_PASSWORD + "'): ");

        final Scanner scan = new Scanner(System.in);
        final String inputPassword = scan.nextLine();
        final String password = inputPassword.isEmpty() ? DEFAULT_PASSWORD : inputPassword;

        final SakilaApplicationBuilder builder = new SakilaApplicationBuilder()
            .withLogging(LogType.STREAM)
            .withPassword(password);

        Stream.of(operators).reduce(
            builder,
            (b, o)-> o.apply(b),
            (a, b) -> a);

        return builder.build();

    }

    public static <T> void printStream(Stream<T> stream) {
        stream.forEachOrdered(System.out::println);
    }

    public static void log(String testName) {
        System.out.format("*** Starting example \"%s\" ***%n", testName);
    }

    private ExampleUtil() {
    }

}
