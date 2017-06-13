/**
 * The configuration system is located in this package. A configuration file
 * consists of a hierarchy of {@link Document Documents} that can be parsed to
 * and from for an example JSON. 
 * <p>
 * The document instance itself is never important and references to it should 
 * therefore never be compared using identity comparison. Any document can
 * contain any data. Some interfaces exists to expose a typed view of certain
 * levels in the document tree, but an untyped implementation could just as well
 * be used.
 * <p>
 * A general description of the document system can be found here:
 * <a href="http://www.ageofjava.com/2016/01/the-best-of-both-worlds.html">
 *      The Best of Both Worlds
 * </a> by Emil Forslund
 * <p>
 * This package is part of the API. Modifications to classes here should only
 * (if ever) be done in major releases.
 */
package com.speedment.runtime.config;
