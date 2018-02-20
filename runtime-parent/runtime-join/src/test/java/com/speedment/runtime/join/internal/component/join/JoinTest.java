package com.speedment.runtime.join.internal.component.join;

import com.speedment.common.injector.Injector;
import com.speedment.common.tuple.Tuple;
import com.speedment.common.tuple.Tuple2;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.JoinBundle;
import com.speedment.runtime.join.JoinComponent;
import com.speedment.runtime.join.internal.component.join.test_support.JoinTestUtil.E1;
import com.speedment.runtime.join.internal.component.join.test_support.JoinTestUtil.E1Manager;
import com.speedment.runtime.join.internal.component.join.test_support.JoinTestUtil.E2;
import com.speedment.runtime.join.internal.component.join.test_support.JoinTestUtil.E2Manager;
import com.speedment.runtime.join.internal.component.join.test_support.MockStreamSupplierComponent;
import com.speedment.runtime.join.internal.component.stream.ExhaustiveJoinStreamSupplierComponent;
import java.util.Set;
import static java.util.stream.Collectors.toSet;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Per Minborg
 */
public final class JoinTest {

    private JoinComponent jc;
    //private JoinStreamSupplierComponent jssc;

    @Before
    public void init() throws InstantiationException {
        final Injector injector = Injector.builder()
            .withBundle(JoinBundle.class)
            .withComponent(MockStreamSupplierComponent.class)
            .withComponent(ExhaustiveJoinStreamSupplierComponent.class)
            .build();

        jc = injector.getOrThrow(JoinComponent.class);
    }

    @Test
    public void crossJoin2() {

        final Join<Tuple2<E1, E2>> join = jc.from(E1Manager.IDENTIFIER)
            .crossJoin(E2Manager.IDENTIFIER)
            .build();

        final Set<Tuple2<E1, E2>> set = join.stream().collect(toSet());

        System.out.println(set.size());

    }

    @Test
    public void innerJoin2() {

        final Join<Tuple2<E1, E2>> join = jc.from(E1Manager.IDENTIFIER)
            .innerJoinOn(E2.ID2).equal(E1.ID1)
            .build();

        final Set<Tuple2<E1, E2>> set = join.stream().collect(toSet());

        System.out.println(set.size());

    }

}
