/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.util.stream;

import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public class StreamUtil {
    public static <T> Stream<T> streamOfNullable(T element) {
        if (element == null) {
            return Stream.empty();
        } else {
            return Stream.of(element);
        }
    }
}