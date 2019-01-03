package com.speedment.runtime.connector.sqlite.internal.util;

import com.speedment.runtime.config.Document;
import com.speedment.runtime.config.trait.HasId;
import com.speedment.runtime.config.trait.HasMainInterface;
import com.speedment.runtime.config.trait.HasName;
import com.speedment.runtime.config.trait.HasParent;

import java.util.StringJoiner;

/**
 * Utility class for writing pretty output to the log.
 *
 * @author Emil Forslund
 * @since  3.1.10
 */
public final class LoggingUtil {

    public static <P extends HasId & HasName,
        D extends Document & HasId & HasName & HasMainInterface & HasParent<P>>
    String describe(D doc) {

        return new StringJoiner(" ")
            .add(doc.mainInterface().getSimpleName())
            .add(doc.getId()).add("in")
            .add(doc.getParentOrThrow().getId())
            .toString();
    }

    private LoggingUtil() {}
}
