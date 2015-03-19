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
package com.speedment.orm;

import com.speedment.orm.config.model.Dbms;
import com.speedment.orm.config.model.aspects.Node;
import com.speedment.orm.config.model.parameters.StandardDbmsType;
import com.speedment.orm.db.DbmsHandler;
import com.speedment.orm.platform.Platform;
import com.speedment.orm.platform.component.DbmsHandlerFactoryComponent;
import com.speedment.util.Trees;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Dbms dbms = Dbms.newDbms();
        dbms.setType(StandardDbmsType.MYSQL);
        dbms.setName("db0");
        dbms.setIpAddress("localhost");
        dbms.setUsername("root");
       
        final DbmsHandler handler = Platform.get().get(DbmsHandlerFactoryComponent.class).make(dbms);

        handler.schemasPopulated().forEachOrdered(schema -> {

            Function<Node, Stream<Node>> traverser = n -> n.asParent().map(p -> p.stream()).orElse(Stream.empty()).map(c -> (Node) c);

            Trees.traverse(schema,
                    traverser,
                    Trees.TraversalOrder.DEPTH_FIRST_PRE).forEachOrdered(System.out::println);
//            System.out.println(schema.toString());
        });
        
    }

}
