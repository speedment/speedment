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
package com.speedment.runtime.join;

import com.speedment.common.tuple.Tuple2;
import com.speedment.common.tuple.Tuple3;
import com.speedment.common.tuple.Tuples;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.field.IntField;
import com.speedment.runtime.field.StringField;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author Per Minborg
 */
public class JoinSketchTest {

    JoinComponent jc = null;

    Manager<User> users = null;
    Manager<Picture> pictures = null;
    Manager<FrameType> frameTypes = null;

    private void test2Cartesian() {
        final Join<Tuple2<User, Picture>> join = jc
            .from(UserManager.IDENTIFIER)
            .crossJoin(PictureManager.IDENTIFIER)
            .build();
        // SELECT * from USER, PICTURE
    }

    private void test2InnerCustomObject() {
        final Join<UserPictureHolder> join = jc
            .from(UserManager.IDENTIFIER)
            .innerJoin(PictureManager.IDENTIFIER).on(User.USER_ID).equal(Picture.USER_ID)
            .build(UserPictureHolder::new);
        // SELECT * from USER INNER JOIN PICTURES ON USER.USER_ID = PICTURE.USER_ID
        // SELECT * from USER AS A INNER JOIN PICTURES AS B ON A.USER_ID = B.USER_ID
    }

    private void test2LeftJoin() {
        final Join<UserPictureHolder> join = jc
            .from(UserManager.IDENTIFIER)
            .leftJoin(PictureManager.IDENTIFIER).on(User.USER_ID).equal(Picture.USER_ID)
            .build(UserPictureHolder::new);
        // SELECT * from USER LEFT JOIN PICTURES ON USER.USER_ID = PICTURE.USER_ID
        //
        // Left type relations will have User = user and Picture = null ????
    }

    private void test2FullOuterJoin() {
        final Join<UserPictureHolder> join = jc
            .from(UserManager.IDENTIFIER)
            .fullOuterJoin(PictureManager.IDENTIFIER).on(User.USER_ID).equal(Picture.USER_ID)
            .build(UserPictureHolder::new);

        // SELECT * from USER FULL OUTER JOIN PICTURES ON USER.USER_ID = PICTURE.USER_ID
    }

    private void test2WithWheresOnTables() {
        Join<UserPictureHolder> join = jc
            .from(UserManager.IDENTIFIER).where(User.USER_ID.greaterOrEqual(100))
            .innerJoin(PictureManager.IDENTIFIER).on(User.USER_ID).equal(Picture.USER_ID).where(Picture.SIZE.greaterThan(10))
            .build(UserPictureHolder::new);

        // SELECT * from USER 
        // INNER JOIN PICTURES ON USER.USER_ID = PICTURE.USER_ID
        // WHERE USER.USER_ID >= 100 AND
        // WHERE PICTURE.SIZE > 10
    }

    private void test2SelfJoin() {
        Join<Tuple2<User, User>> join = jc
            .from(UserManager.IDENTIFIER).where(User.USER_ID.greaterOrEqual(100))
            .innerJoin(UserManager.IDENTIFIER).on(User.NAME).equal(User.NAME)
            .build();
        // SELECT * from USER as A
        // INNER JOIN USER as B ON A.USER_NAME = B.USER_NAME
        // WHERE USER.USER_ID >= 100 AND
    }

    private void test3Cartesian() {
        final Join<Tuple3<User, Picture, FrameType>> join = jc
            .from(UserManager.IDENTIFIER)
            .crossJoin(PictureManager.IDENTIFIER)
            .crossJoin(FrameTypeManager.IDENTIFIER)
            .build();

        // SELECT * from USER, PICTURE, FRAME_TYPE
    }

    private void test3Inner() {

        final Join<Tuple3<User, Picture, FrameType>> join = jc
            .from(UserManager.IDENTIFIER)
            .innerJoin(PictureManager.IDENTIFIER).on(User.USER_ID).equal(Picture.USER_ID)
            .innerJoin(FrameTypeManager.IDENTIFIER).on(Picture.FRAME_ID).equal(FrameType.FRAME_ID) // Note that on() can be either on Picture or User
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

        IntField<User, Integer> USER_ID = null;
        StringField<User, String> NAME = null;
        StringField<User, String> PASSWORD = null;
    };

    interface Picture {

        IntField<Picture, Integer> PICTURE_ID = null;
        StringField<Picture, String> NAME = null;
        IntField<Picture, Integer> USER_ID = null;
        IntField<Picture, Integer> FRAME_ID = null;
        IntField<Picture, Integer> SIZE = null;
    };

    interface FrameType {

        IntField<FrameType, Integer> FRAME_ID = null;
        StringField<FrameType, String> NAME = null;
    };

    Join<Tuple3<User, Picture, FrameType>> join3 = jc.from(UserManager.IDENTIFIER)
        .leftJoin(PictureManager.IDENTIFIER).on(User.USER_ID).equal(Picture.USER_ID)
        .leftJoin(FrameTypeManager.IDENTIFIER).on(Picture.FRAME_ID).equal(FrameType.FRAME_ID)
        .build();

    // Using Utility methods to map entity operations into Tuple3 operations
    List<Tuple2<String, String>> result = join3.stream()
        .filter(Joins.Tuple3Util.testing0((User.NAME.between("A", "C"))))
        .sorted(Joins.Tuple3Util.comparing0(User.NAME.comparator()))
        .map(Tuples.toTuple(
            Joins.Tuple3Util.applying0(User.NAME.getter()),
            Joins.Tuple3Util.applying2(FrameType.NAME.getter()))
        )
        .collect(toList());

    // Alt 1: Use utility class 
    public static final class Joins {

        public static final class Tuple2Util {
            //TBW

        }

        public static final class Tuple3Util {

            public static <T0, T1, T2> Predicate<Tuple3<T0, T1, T2>> testing0(Predicate<T0> predicate) {
                return null;
            }

            public static <T0, T1, T2> Predicate<Tuple3<T0, T1, T2>> testing1(Predicate<T1> predicate) {
                return null;
            }

            public static <T0, T1, T2> Predicate<Tuple3<T0, T1, T2>> testing2(Predicate<T2> predicate) {
                return null;
            }

            public static <T0, T1, T2> Comparator<Tuple3<T0, T1, T2>> comparing0(Comparator<T0> comparator) {
                return null;
            }

            public static <T0, T1, T2> Comparator<Tuple3<T0, T1, T2>> comparing1(Comparator<T1> comparator) {
                return null;
            }

            public static <T0, T1, T2> Comparator<Tuple3<T0, T1, T2>> comparing2(Comparator<T2> comparator) {
                return null;
            }

            public static <T0, T1, T2, R> Function<Tuple3<T0, T1, T2>, R> applying0(Function<T0, R> extractor) {
                return null;
            }

            public static <T0, T1, T2, R> Function<Tuple3<T0, T1, T2>, R> applying1(Function<T1, R> extractor) {
                return null;
            }

            public static <T0, T1, T2, R> Function<Tuple3<T0, T1, T2>, R> applying2(Function<T2, R> extractor) {
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
