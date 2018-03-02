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
//            .withComponent(E1SqlAdapter.class)
//            .withComponent(E2SqlAdapter.class)
//            .withComponent(E3SqlAdapter.class)
//            .withComponent(E4SqlAdapter.class)
//            .withComponent(E5SqlAdapter.class)
//            .withComponent(E6SqlAdapter.class)
//            .withComponent(E1MangerImpl.class)
//            .withComponent(E2MangerImpl.class)
//            .withComponent(E3MangerImpl.class)
//            .withComponent(E4MangerImpl.class)
//            .withComponent(E5MangerImpl.class)
//            .withComponent(E6MangerImpl.class)
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
////        final Join<Tuple2OfNullables<E1, E2>> join = jc.from(E1Manager.IDENTIFIER)
////            .crossJoin(E2Manager.IDENTIFIER)
////            .build();
//
//        //final Set<Tuple2OfNullables<E1, E2>> set = join.stream().collect(toSet());
//
//        //System.out.println(set.size());
//
//    }
//
//    @Test
//    public void innerJoin2() {
//
////        final Join<Tuple2OfNullables<E1, E2>> join = jc.from(E1Manager.IDENTIFIER)
////            .innerJoinOn(E2.ID2).equal(E1.ID1)
////            .build();
//
//        //final Set<Tuple2OfNullables<E1, E2>> set = join.stream().collect(toSet());
//
//        //System.out.println(set.size());
//
//    }

}
