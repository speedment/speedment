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
package com.speedment.example.basic_example;

import com.company.sakila.SakilaApplication;
import com.company.sakila.db0.sakila.address.AddressManager;
import com.speedment.example.basic_example.util.ExampleUtil;
import static com.speedment.example.basic_example.util.ExampleUtil.buildApplication;
import java.sql.Blob;
import java.sql.SQLException;

/**
 *
 * @author Per Minborg
 */
public class PrintAddresses {

    private final SakilaApplication app;
    private final AddressManager addresses;

    public PrintAddresses() {
        app = buildApplication();
        addresses = app.getOrThrow(AddressManager.class);
    }

    public static void main(String[] args) {
        new PrintAddresses().run();
    }

    private void run() {
        printAll();
        printAllMapped();
    }

    private void printAll() {
        ExampleUtil.log("printAll");

        addresses.stream()
            .forEach(System.out::println);

    }

    private void printAllMapped() {
        ExampleUtil.log("printAll");

        addresses.stream()
            .forEach(a -> System.out.format("Address %s, location Blob bytes %s %n", a.getAddress(), len(a.getLocation())));

    }

    private long len(Blob blob) {
        try {
            return blob.length();
        } catch (SQLException sqle) {
            return -1;
        }
    }

}
