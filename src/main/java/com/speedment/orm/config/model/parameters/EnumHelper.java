package com.speedment.orm.config.model.parameters;

import com.speedment.orm.config.model.ConfigEntity;
import com.speedment.orm.config.model.aspects.Childable;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public interface EnumHelper<E extends Enum<E>> {
    String getName();

    class Hidden {
//        
//        static <E extends Enum<E>, H extends EnumHelper<E>> Optional<E> findByNameIgnoreCase(final H helper, final String name) {
//            return findByNameIgnoreCase(helper.getNameMap(), name);
//        }
        
        static <E extends Enum<E> & EnumHelper<E>, C extends ConfigEntity> E defaultFor(Stream<E> stream, Predicate<E> predicate, final C entity, final E defaultValue) {
            return Optional.ofNullable(entity)
                .flatMap(e -> e.getParent())
                .flatMap(e -> streamFor(
                    stream, predicate, e
                ).filter(predicate).findAny())
                .orElse(defaultValue);
        }
    
        static <E extends Enum<E>, H extends EnumHelper<E>, C extends Childable> Stream<E> streamFor(Stream<E> stream, Predicate<E> predicate, final C parent) {
            if (FieldStorageType.class.isAssignableFrom(parent.getInterfaceMainClass())) {
                return stream;
            } else {
                return stream.filter(predicate.negate());
            }
        }
//
//        static <E extends Enum<E> & EnumHelper<E>> Optional<E> findByNameIgnoreCase(Stream<E> values, final String name) {
//            return values
//                .filter((E t) -> name.equalsIgnoreCase(t.getName()))
//                .findFirst();
//        }

        static <E extends Enum<E> & EnumHelper<E>> Map<String, E> buildMap(E[] values) {
            return Collections.unmodifiableMap(Stream.of(values).collect(
                Collectors.toMap((dt) -> Hidden.normalize(dt.getName()), Function.identity())
            ));
        }

        static <E> Optional<E> findByNameIgnoreCase(Map<String, E> map, final String name) {
            return Optional.ofNullable(map.get(normalize(name)));
        }

        private static String normalize(String string) {
            return string == null ? null : string.toLowerCase();
        }
    }
}