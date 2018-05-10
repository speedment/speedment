/**
 * The Runtime Compute module adds a large number of functional interfaces to
 * the Speedment system that allows computations to be performed on entities in
 * a Stream without loosing metadata required by Speedment to optimize the
 * stream. The interfaces here also has default implementations, many which can
 * be obtained using utility classes. The idea is that an interface can either
 * be implemented using a lambda, but then without any metadata, or using one of
 * the pre-made utility classes, in which case metadata is preserved.
 * <p>
 * This package is part of the API.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
package com.speedment.runtime.compute;