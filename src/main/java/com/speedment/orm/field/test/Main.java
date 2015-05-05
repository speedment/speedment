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
package com.speedment.orm.field.test;

import java.util.List;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;
// import static com.speedment.orm.function.Predicates.greterThan;

/**
 *
 * @author pemi
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //List<Hare> hareList = Stream.of(new Hare()).filter(h- > equals("harry",Hare::getName)).collect(toList());
        // Supplier<String> nameSupplier = Hare h -> h.get;
        List<Hare> hareList = Stream.of(new HareHarry())
                .filter(h -> "harry".equals(h.getName()))
                .filter(h -> h.getAge() > 1)
                .filter(h -> h.getAge() < 9)
                .collect(toList());

        System.out.println(hareList);

//        List<Hare> hareList2 = Stream.of(new HareHarry())
//                .filter(eq("harry", Hare::getName))
//                .filter(Predicates.greaterThan(Hare::getAge, 1))
//                .filter(Predicates.lessThan(Hare::getAge, 9))
//                .collect(toList());
//
//        System.out.println(hareList2);
//
        HareManager hm = new HareManager();
//
//        List<Hare> hareList3 = hm.stream()
//                .filter(eq("harry", Hare::getName))
//                .filter(Predicates.greaterThan(Hare::getAge, 1))
//                .filter(Predicates.lessThan(Hare::getAge, 9))
//                .collect(toList());
//
//        System.out.println(hareList3);

        //hm.stream().forEach(System.out::println);
        List<Hare> hareList4 = hm.stream()
                .filter(HareField.NAME.equal("harry"))
                //.peek(System.out::println)
                .filter(HareField.AGE.greaterThan(1))
                //.peek(System.out::println)
                .filter(HareField.AGE.lessThan(9))
                // .peek(System.out::println)
                .collect(toList());
        System.out.println(hareList4);

        List<Hare> hareList5 = hm.stream()
                .filter(HareField.NAME.equal("harry").and(HareField.AGE.greaterThan(1)))
                .filter(HareField.AGE.lessThan(9))
                .collect(toList());
        System.out.println(hareList5);

        final Hare hare = hareList.stream().findAny().get();
        String name = HareField.NAME.getFrom(hare);

    }

}
