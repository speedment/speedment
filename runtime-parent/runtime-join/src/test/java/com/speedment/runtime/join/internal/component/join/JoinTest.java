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
package com.speedment.runtime.join.internal.component.join;

import com.speedment.common.injector.Injector;
import com.speedment.common.logger.Level;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.common.tuple.nullable.Tuple2OfNullables;
import com.speedment.runtime.core.ApplicationBuilder;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.JoinComponent;
import com.speedment.runtime.join.JoinStreamSupplierComponent;
import com.speedment.runtime.join.internal.component.join.test_support.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author Per Minborg
 */
@Execution(ExecutionMode.CONCURRENT)
final class JoinTest {

    private final static Logger LOGGER_JOIN =
        LoggerManager.getLogger(ApplicationBuilder.LogType.JOIN.getLoggerName());

    private JoinComponent jc;
    private JoinStreamSupplierComponent jssc;


    @BeforeEach
    void init() throws InstantiationException {
        LOGGER_JOIN.setLevel(Level.DEBUG);

//        final Speedment app = new DefaultApplicationBuilder(MockMetadata.class)
//            .withSkipCheckDatabaseConnectivity()
//            .withSkipValidateRuntimeConfig()
//            .withBundle(JoinBundle.class)
//            .withComponent(MockStreamSupplierComponent.class)
//            .withComponent(SqlJoinStreamSupplierComponent.class)
//            .withComponent(SqlAdapterUtil.E0SqlAdapter.class)
//            .withComponent(SqlAdapterUtil.E1SqlAdapter.class)
//            .withComponent(SqlAdapterUtil.E2SqlAdapter.class)
//            .withComponent(SqlAdapterUtil.E3SqlAdapter.class)
//            .withComponent(SqlAdapterUtil.E4SqlAdapter.class)
//            .withComponent(SqlAdapterUtil.E5SqlAdapter.class)
//            .withComponent(JoinTestUtil.E0MangerImpl.class)
//            .withComponent(JoinTestUtil.E1MangerImpl.class)
//            .withComponent(JoinTestUtil.E2MangerImpl.class)
//            .withComponent(JoinTestUtil.E3MangerImpl.class)
//            .withComponent(JoinTestUtil.E4MangerImpl.class)
//            .withComponent(JoinTestUtil.E5MangerImpl.class)
//            .withComponent(JoinTestUtil.MockPersistanceComponent.class)
//            .withSkipCheckDatabaseConnectivity()
//            .build();

        final Injector injector = Injector.builder()
            .withComponent(JoinComponentImpl.class)
            .withComponent(MockStreamSupplierComponent.class)
            .withComponent(MockEmptyJoinStreamSupplierComponent.class)
            .build();

        jc = injector.getOrThrow(JoinComponent.class);
        jssc = injector.getOrThrow(JoinStreamSupplierComponent.class);
    }

    @Test
    void crossJoin2() {
        assertDoesNotThrow(() -> {
            final Join<Tuple2OfNullables<JoinTestUtil.E0, JoinTestUtil.E1>> join = jc
                .from(JoinTestUtil.E0Manager.IDENTIFIER)
                .crossJoin(JoinTestUtil.E1Manager.IDENTIFIER)
                .build();

            final Set<Tuple2OfNullables<JoinTestUtil.E0, JoinTestUtil.E1>> set = join.stream().collect(toSet());
        });
    }

    @Test
    void innerJoin2() {
        assertDoesNotThrow(() -> {
            final Join<Tuple2OfNullables<JoinTestUtil.E0, JoinTestUtil.E1>> join = jc
                .from(JoinTestUtil.E0Manager.IDENTIFIER)
                .leftJoinOn(JoinTestUtil.E1.ID1).equal(JoinTestUtil.E0.ID0)
                .build();

            final Set<Tuple2OfNullables<JoinTestUtil.E0, JoinTestUtil.E1>> set = join.stream().collect(toSet());
        });
    }



    //
//    @Test
//    public void crossJoin2() {
//
////        final Join<Tuple2OfNullables<E0, E1>> join = jc.from(E0Manager.IDENTIFIER)
////            .crossJoin(E1Manager.IDENTIFIER)
////            .build();
//
//        //final Set<Tuple2OfNullables<E0, E1>> set = join.stream().collect(toSet());
//
//        //System.out.println(set.size());
//
//    }
//
//    @Test
//    public void innerJoin2() {
//
////        final Join<Tuple2OfNullables<E0, E1>> join = jc.from(E0Manager.IDENTIFIER)
////            .innerJoinOn(E1.ID2).equal(E0.ID1)
////            .build();
//
//        //final Set<Tuple2OfNullables<E0, E1>> set = join.stream().collect(toSet());
//
//        //System.out.println(set.size());
//
//    }

}
