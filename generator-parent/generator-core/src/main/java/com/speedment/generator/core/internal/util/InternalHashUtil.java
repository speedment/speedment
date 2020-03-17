/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.generator.core.internal.util;

import com.speedment.generator.core.util.HashUtil;
import com.speedment.runtime.core.exception.SpeedmentException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.joining;

public final class InternalHashUtil {

    private InternalHashUtil() {}

    private static final String ALGORITHM = "MD5";
    private static final Charset CHARSET  = StandardCharsets.UTF_8;

    public static boolean compare(Path contentPath, Path checksumPath) {
        final String expected = HashUtil.md5(contentPath);
        final String actual   = load(checksumPath).get(0);
        return expected.equals(actual);
    }

    public static boolean compare(Path path, String checksum) {
        final String expected = HashUtil.md5(path);
        return expected.equals(checksum);
    }

    public static boolean compare(String content, String checksum) {
        final String expected = md5(content);
        return expected.equals(checksum);
    }

    public static String md5(Path path) {
        return md5(load(path));
    }

    public static String md5(String content) {
        return md5(Arrays.asList(content.split("\\s+")));
    }

    private static String md5(List<String> rows) {
        return md5(rows.stream()
            .map(String::trim)
            .flatMap(s -> Arrays.stream(s.split("\\s+")))
            .collect(joining())
            .getBytes(CHARSET)
        );
    }

    private static String md5(byte[] bytes) {
        final MessageDigest md;

        try {
            md = MessageDigest.getInstance(ALGORITHM);
        } catch(final NoSuchAlgorithmException ex) {
            throw new SpeedmentException(
                "Could not find hashing algorithm '" + ALGORITHM + "'.", ex
            );
        }

        return bytesToHex(md.digest(bytes));
    }

    private static String bytesToHex(byte[] bytes) {
        final StringBuilder result = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {
            result.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        return result.toString();
    }

    private static List<String> load(Path path) {
        try {
            return Files.readAllLines(path, CHARSET);
        } catch (final IOException ex) {
            throw new SpeedmentException(
                "Error reading file '" + path + "' for hashing.", ex
            );
        }
    }


}
