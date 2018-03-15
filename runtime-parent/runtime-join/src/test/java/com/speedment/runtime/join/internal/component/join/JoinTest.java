package com.speedment.runtime.join.internal.component.join;

/**
 *
 * @author Per Minborg
 */
public final class JoinTest {

//    private JoinComponent jc;
//    //private JoinStreamSupplierComponent jssc;
//
//    // @Before
//    public void init() throws InstantiationException {
//        
//        final Speedment app = new DefaultApplicationBuilder(MockMetadata.class)
//            .withSkipCheckDatabaseConnectivity()
//            .withSkipValidateRuntimeConfig()
//            .withBundle(JoinBundle.class)
//            .withComponent(MockStreamSupplierComponent.class)
//            .withComponent(SqlJoinStreamSupplierComponent.class)
//            .withComponent(E0SqlAdapter.class)
//            .withComponent(E1SqlAdapter.class)
//            .withComponent(E2SqlAdapter.class)
//            .withComponent(E3SqlAdapter.class)
//            .withComponent(E4SqlAdapter.class)
//            .withComponent(E5SqlAdapter.class)
//            .withComponent(E0MangerImpl.class)
//            .withComponent(E1MangerImpl.class)
//            .withComponent(E2MangerImpl.class)
//            .withComponent(E3MangerImpl.class)
//            .withComponent(E4MangerImpl.class)
//            .withComponent(E5MangerImpl.class)
//            .withComponent(MockPersistanceComponent.class)
//            .withSkipCheckDatabaseConnectivity()
//            .build();
//
////        final Injector injector = Injector.builder()
////            .withBundle(JoinBundle.class)
////            .withComponent(MockStreamSupplierComponent.class)
////            .withComponent(SqlJoinStreamSupplierComponent.class)
////            .build();
//        jc = app.getOrThrow(JoinComponent.class);
//    }
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
