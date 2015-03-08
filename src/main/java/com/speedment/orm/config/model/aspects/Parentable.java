package com.speedment.orm.config.model.aspects;

/**
 *
 * @author Emil Forslund
 */
public interface Parentable<P extends Childable> extends Node {
//    @Override
//    Optional<P> getParent();
    
    void setParent(P parent);
    
    Class<P> getParentInterfaceMainClass();
    
    default boolean isRoot() {
        return !getParent().isPresent();
    }

//    @Override
//    default boolean isParentable() {
//        return true;
//    }
}
