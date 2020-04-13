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
package com.speedment.runtime.join;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.tuple.Tuple2;
import com.speedment.common.tuple.Tuple3;
import com.speedment.common.tuple.Tuples;
import com.speedment.common.tuple.nullable.Tuple2OfNullables;
import com.speedment.common.tuple.nullable.Tuple3OfNullables;
import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.field.IntField;
import com.speedment.runtime.field.StringField;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.join.internal.component.join.JoinComponentImpl;
import com.speedment.runtime.join.internal.component.join.test_support.MockEmptyJoinStreamSupplierComponent;
import com.speedment.runtime.typemapper.TypeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Per Minborg
 */
final class JoinSketchTest {

    JoinComponent jc = null;
    Manager<User> users = null;
    Manager<Picture> pictures = null;
    Manager<FrameType> frameTypes = null;

    /*
    SELECT
        m.title AS title,
    	e0.address AS from_email,
	    e1.address AS to_email
    FROM Message AS m
    LEFT JOIN User AS u0 ON m.from = u0.id
    LEFT JOIN Email AS e0 ON u0.email = e0.id
    LEFT JOIN User AS u1 ON m.to   = u1.id
    LEFT JOIN Email AS e1 ON u1.email = e1.id;
    */

    @BeforeEach
    void init() {
        try {
            Injector injector = Injector.builder()
                .withComponent(JoinComponentImpl.class)
                .withComponent(MockEmptyJoinStreamSupplierComponent.class)
                .build();
            jc = injector.getOrThrow(JoinComponent.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void simpleProblemTest() {
        assertDoesNotThrow(() ->
            jc.from(PictureManager.IDENTIFIER)
                .innerJoinOn(User.USER_ID).equal(Picture.USER_ID)
                .build(Tuples::of)
        );
    }


    private void problemTree() {

        final Join<Tuple3OfNullables<User, User, User>> join = jc
            .from(UserManager.IDENTIFIER)
            .innerJoinOn(User.NAME).equal(User.NAME) // Referes to the first name (ok!)
            .innerJoinOn(User.NAME).equal(User.NAME) // Which version of User does this equal() reference? first or second? .equal(User.NAME, 2) ???
            .build();


        HasComparableOperators<User, ?> secondUserName = User.NAME.tableAlias("Olle");

        final Join<Tuple3OfNullables<User, User, User>> join2 = jc
            .from(UserManager.IDENTIFIER)
            .innerJoinOn(secondUserName).equal(User.NAME) // Referes to the first name (ok!)
            .innerJoinOn(User.NAME).equal(secondUserName) // Referes to the second
            .build();

        final Join<Tuple3OfNullables<User, User, User>> join3 = jc
            .from(UserManager.IDENTIFIER)
            .innerJoinOn(User.NAME.tableAlias("Olle")).equal(User.NAME) // Referes to the first name (ok!)
            .innerJoinOn(User.NAME).equal(User.NAME.tableAlias("Olle")) // Which version of User does this equal() reference? first or second? .equal(User.NAME, 2) ???
            .build();


//        final Join<Tuple3OfNullables<User, User, User>> join4 = jc
//                .from(UserManager.IDENTIFIER)
//                .innerJoinOn(User.NAME).as("Olle").equal(User.NAME) // Referes to the first name (ok!)
//                .innerJoinOn(User.NAME).equal(User.NAME).from("Olle") // Which version of User does this equal() reference? first or second? .equal(User.NAME, 2) ???
//                .build();
//
//
//
//        final Join<Tuple3OfNullables<User, User, User>> join3 = jc
//                .from(UserManager.IDENTIFIER)
//                .innerJoinOn(User.NAME).equal(User.NAME) // Referes to the first name (ok!)
//                .innerJoinOn(User.NAME).equal(User.NAME.compose(Tuple3OfNullables.getter1()) // Which version of User does this equal() reference? first or second? .equal(User.NAME, 2) ???
//                .build();


        // SELECT * from USER AS A
        //  INNER JOIN PICTURES AS B ON A.USER_ID = B.USER_ID
        //  INNER JOIN USER AS C on A.NAME = C.NAME
    }

    private static <ENTITY, V extends Comparable<? super V>> HasComparableOperators<User, V> as(HasComparableOperators<User, V> initial) {
        return initial;
    }


    private void test2Cartesian() {
        final Join<Tuple2<User, Picture>> join = jc
            .from(UserManager.IDENTIFIER)
            .crossJoin(PictureManager.IDENTIFIER)
            .build(Tuples::of); // They are never null
        // SELECT * from USER, PICTURE
    }

    private void test2InnerCustomObject() {
        final Join<UserPictureHolder> join = jc
            .from(UserManager.IDENTIFIER)
            .innerJoinOn(Picture.USER_ID).equal(User.USER_ID)
            .build(UserPictureHolder::new);
        // SELECT * from USER INNER JOIN PICTURES ON USER.USER_ID = PICTURE.USER_ID
        // SELECT * from USER AS A INNER JOIN PICTURES AS B ON A.USER_ID = B.USER_ID
    }

    private void test2LeftJoin() {
        final Join<UserPictureHolder> join = jc
            .from(UserManager.IDENTIFIER)
            .leftJoinOn(Picture.USER_ID).equal(User.USER_ID)
            .build(UserPictureHolder::new);
        // SELECT * from USER LEFT JOIN PICTURES ON USER.USER_ID = PICTURE.USER_ID
        //
        // Left type relations will have User = user and Picture = null ????
    }

//    private void test2FullOuterJoin() {
//        final Join<UserPictureHolder> join = jc
//            .from(UserManager.IDENTIFIER)
//            .fullOuterJoinOn(Picture.USER_ID).equal(User.USER_ID)
//            .build(UserPictureHolder::new);
//
//        // SELECT * from USER FULL OUTER JOIN PICTURES ON USER.USER_ID = PICTURE.USER_ID
//    }

    private void test2WithWheresOnTables() {
        Join<UserPictureHolder> join = jc
            .from(UserManager.IDENTIFIER).where(User.USER_ID.greaterOrEqual(100))
            .innerJoinOn(Picture.USER_ID).equal(User.USER_ID).where(Picture.SIZE.greaterThan(10))
            .build(UserPictureHolder::new);

        // SELECT * from USER 
        // INNER JOIN PICTURES ON USER.USER_ID = PICTURE.USER_ID
        // WHERE USER.USER_ID >= 100 AND
        // WHERE PICTURE.SIZE > 10
    }

    private void test2SelfJoin() {
        Join<Tuple2OfNullables<User, User>> join = jc
            .from(UserManager.IDENTIFIER).where(User.USER_ID.greaterOrEqual(100))
            .innerJoinOn(User.NAME).equal(User.NAME)
            .build();
        // SELECT * from USER as A
        // INNER JOIN USER as B ON A.USER_NAME = B.USER_NAME
        // WHERE USER.USER_ID >= 100 AND
    }

    private void test3Cartesian() {
        final Join<Tuple3OfNullables<User, Picture, FrameType>> join = jc
            .from(UserManager.IDENTIFIER)
            .crossJoin(PictureManager.IDENTIFIER)
            .crossJoin(FrameTypeManager.IDENTIFIER)
            .build();

        // SELECT * from USER, PICTURE, FRAME_TYPE

        join.stream()
            .forEachOrdered(e -> {
                e.get0().isPresent();
            });


    }

    private void test3Inner() {

        final Join<Tuple3OfNullables<User, Picture, FrameType>> join = jc
            .from(UserManager.IDENTIFIER)
            .innerJoinOn(Picture.USER_ID).equal(Picture.USER_ID)
            .innerJoinOn(FrameType.FRAME_ID).equal(Picture.FRAME_ID) // Note that on() can be either on Picture or User
            .build();

        // SELECT * from USER AS A 
        //  INNER JOIN PICTURES AS B ON A.USER_ID = B.USER_ID
        //  INNER JOIN FRAME_TYPES AS C on B.FRAME_ID = C.FRANE_ID
    }


    private void problem() {

        final Join<Tuple3OfNullables<User, Picture, FrameType>> join = jc
            .from(UserManager.IDENTIFIER)
            .innerJoinOn(Picture.USER_ID).equal(User.USER_ID)
            .innerJoinOn(FrameType.FRAME_ID).equal(Picture.FRAME_ID) // Note that on() can be either on Picture or User
            .build();

        // SELECT * from USER AS A 
        //  INNER JOIN PICTURES AS B ON A.USER_ID = B.USER_ID
        //  INNER JOIN FRAME_TYPES AS C on B.FRAME_ID = C.FRANE_ID
    }

    private void problem2() {

        final Join<Tuple3OfNullables<User, User, User>> join = jc
            .from(UserManager.IDENTIFIER)
            .innerJoinOn(User.NAME).equal(User.NAME) // Referes to the first name (ok!)
            .innerJoinOn(User.NAME).equal(User.NAME) // Which version of User does this equal() reference? first or second? .equal(User.NAME, 2) ???
            .build();

        // SELECT * from USER AS A 
        //  INNER JOIN PICTURES AS B ON A.USER_ID = B.USER_ID
        //  INNER JOIN FRAME_TYPES AS C on B.FRAME_ID = C.FRANE_ID
    }


    interface UserManager {

        TableIdentifier<User> IDENTIFIER = TableIdentifier.of(
            "sakila",
            "sakila",
            "user"
        );

    }

    interface PictureManager {

        TableIdentifier<Picture> IDENTIFIER = TableIdentifier.of(
            "sakila",
            "sakila",
            "picture"
        );

    }

    interface FrameTypeManager {

        TableIdentifier<FrameType> IDENTIFIER = TableIdentifier.of(
            "sakila",
            "sakila",
            "frame_type"
        );

    }

    interface User {
        IntField<User, Integer> USER_ID = IntField.create(ColumnIdentifier.of("sakila", "sakila","user","user_id"),User::getUserId, User::setUserId,TypeMapper.primitive(),true);
        StringField<User, String> NAME = null;
        StringField<User, String> PASSWORD = null;

        int getUserId();
        void setUserId(int userId);

    }


    interface Picture {

        IntField<Picture, Integer> PICTURE_ID = null;
        StringField<Picture, String> NAME = null;
        IntField<Picture, Integer> USER_ID = IntField.create(ColumnIdentifier.of("sakila", "sakila","picture","user_id"),Picture::getUserId, Picture::setUserId,TypeMapper.primitive(),false);
        IntField<Picture, Integer> FRAME_ID = null;
        IntField<Picture, Integer> SIZE = null;

        int getUserId();
        void setUserId(int userId);

    }

    interface FrameType {

        IntField<FrameType, Integer> FRAME_ID = null;
        StringField<FrameType, String> NAME = null;
    }



    private void arne() {
        Join<Tuple3OfNullables<User, Picture, FrameType>> join3 = jc.from(UserManager.IDENTIFIER)
            .leftJoinOn(Picture.USER_ID).equal(User.USER_ID)
            .leftJoinOn(FrameType.FRAME_ID).equal(Picture.FRAME_ID)
            .build();

        // Using Utility methods to map entity operations into Tuple3 operations
        List<Tuple2<String, String>> result = join3.stream()
            .filter(Joins.Tuple3OfNullablesUtil.testing0((User.NAME.between("A", "C"))))
            .sorted(Joins.Tuple3OfNullablesUtil.comparing0(User.NAME.comparator()))
            .map(Tuples.toTuple(
                Joins.Tuple3OfNullablesUtil.applying0(User.NAME.getter()),
                Joins.Tuple3OfNullablesUtil.applying2(FrameType.NAME.getter()))
            )
            .collect(toList());

    }

    // Alt 1: Use utility class 
    public static final class Joins {

        public static final class Tuple2Util {
            //TBW

        }

        public static final class Tuple3OfNullablesUtil {

            public static <T0, T1, T2> Predicate<Tuple3OfNullables<T0, T1, T2>> testing0(Predicate<T0> predicate) {
                return null;
            }

            public static <T0, T1, T2> Predicate<Tuple3OfNullables<T0, T1, T2>> testing1(Predicate<T1> predicate) {
                return null;
            }

            public static <T0, T1, T2> Predicate<Tuple3OfNullables<T0, T1, T2>> testing2(Predicate<T2> predicate) {
                return null;
            }

            public static <T0, T1, T2> Comparator<Tuple3OfNullables<T0, T1, T2>> comparing0(Comparator<T0> comparator) {
                return null;
            }

            public static <T0, T1, T2> Comparator<Tuple3OfNullables<T0, T1, T2>> comparing1(Comparator<T1> comparator) {
                return null;
            }

            public static <T0, T1, T2> Comparator<Tuple3OfNullables<T0, T1, T2>> comparing2(Comparator<T2> comparator) {
                return null;
            }

            public static <T0, T1, T2, R> Function<Tuple3OfNullables<T0, T1, T2>, R> applying0(Function<T0, R> extractor) {
                return null;
            }

            public static <T0, T1, T2, R> Function<Tuple3OfNullables<T0, T1, T2>, R> applying1(Function<T1, R> extractor) {
                return null;
            }

            public static <T0, T1, T2, R> Function<Tuple3OfNullables<T0, T1, T2>, R> applying2(Function<T2, R> extractor) {
                return null;
            }

        }

    }

    // Alt 2: Expand Tuples with static methods
    interface ExtendedTuple3<T0, T1, T2> extends Tuple3<T0, T1, T2> {

        static <N0, N1, N2> Predicate<ExtendedTuple3<N0, N1, N2>> testing0(Predicate<N0> predicate) {
            return null;
        }

        // ...
    }

    public static final class UserPictureHolder {

        private final User user;
        private final Picture picture;

        public UserPictureHolder(User user, Picture picture) {
            this.user = user;
            this.picture = picture;
        }

        User user() {
            return user;
        }

        Picture picture() {
            return picture;
        }

    }

    public static final class IntStringHolder {

        private final Integer integer;
        private final String string;

        public IntStringHolder(Integer integer, String string) {
            this.integer = integer;
            this.string = string;
        }

        public Integer integer() {
            return integer;
        }

        public String string() {
            return string;
        }

    }

}
