package com.speedment.util.java.sql;

/**
 *
 * @author pemi
 */
public class SqlUtil {

    public static String sqlParseValue(final String value) {
        if (value == null) {
            return "NULL";
        }
        return "'" + value.replaceAll("'", "''") + "'";
    }

    public static String unQuote(final String s) {
        if (s.startsWith("\"") && s.endsWith("\"")) {
            // Un-quote the name
            return s.substring(1, s.length() - 1);
        }
        return s;
    }

}
