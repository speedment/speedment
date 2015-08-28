package com.speedment.api.field.operators;

import com.speedment.api.annotation.Api;
import java.util.function.BiPredicate;

/**
 *
 * @author pemi
 */
@Api(version = "2.1")
public interface StringOperator {
    BiPredicate<String, String> getStringFilter();
}