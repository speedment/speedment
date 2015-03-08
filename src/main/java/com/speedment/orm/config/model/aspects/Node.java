package com.speedment.orm.config.model.aspects;

import com.speedment.util.Trees;
import java.util.Optional;
import static java.util.stream.Collectors.joining;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public interface Node extends Nameable {
    default <P extends Childable> Optional<P> getParent() {
        return Optional.empty();
    }
    
    default boolean isChildable() {
        return false;
    }

//    default boolean isParentable() {
//        return false;
//    }
    
    default boolean isOrdinable() {
        return false;
    }
    
    @SuppressWarnings("unchecked")
    default Stream<? extends Childable> ancestors() {
        return Trees.walkOptional(
            getParent().map(p -> (Childable) p).get(), 
            p -> Optional.of(p)
                .map(p2 -> p2.getParent())
                .filter(p2 -> p2.isPresent())
                .map(p2 -> (Childable) p2.get()),
            Trees.WalkingOrder.BACKWARD
        );
    }
    
    @SuppressWarnings("unchecked")
    default <E extends Node> Optional<E> ancestor(final Class<E> clazz) {
        return ancestors()
            .filter(p -> clazz.isAssignableFrom(p.getClass()))
            .map(p -> (E) p)
            .findFirst();
    }
    
    default String getRelativeName(Node from) {
        return ancestors().map(Nameable::getName).collect(joining("."));
    }
    
    <T> Class<T> getInterfaceMainClass();
}