/**
 * Models for typical object-oriented language building blocks are located in 
 * this package. The ambition of codegen is to separate model, view and 
 * controller logic into different classes. This package represents the model
 * part of that trio.
 * <p>
 * The interfaces in this package does not share a common ancestor. The reason
 * for this is that any class should qualify as a model as long as a 
 * corresponding view is installed in the active
 * {@link com.speedment.codegen.Generator}.
 * <p>
 * This package is part of the API. Modifications to classes here should only
 * (if ever) be done in major releases.
 */
package com.speedment.codegen.model;