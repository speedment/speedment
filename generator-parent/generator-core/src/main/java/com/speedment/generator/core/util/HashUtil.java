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
package com.speedment.generator.core.util;

import com.speedment.generator.core.internal.util.InternalHashUtil;

import java.nio.file.Path;

import static java.util.Objects.requireNonNull;

/**
 * A utility method for creating and comparing hashes of files.
 * 
 * @author  Emil Forslund
 * @since   3.0.0
 */
public final class HashUtil {

    private HashUtil() {}

    public static boolean compare(Path contentPath, Path checksumPath) {
        requireNonNull(contentPath);
        requireNonNull(checksumPath);
        return InternalHashUtil.compare(contentPath, checksumPath);
    }
    
    public static boolean compare(Path path, String checksum) {
        requireNonNull(path);
        requireNonNull(checksum);
        return InternalHashUtil.compare(path, checksum);
    }
    
    public static boolean compare(String content, String checksum) {
        requireNonNull(content);
        requireNonNull(checksum);
        return InternalHashUtil.compare(content, checksum);
    }
    
    public static String md5(Path path) {
        requireNonNull(path);
        return InternalHashUtil.md5(path);
    }
    
    public static String md5(String content) {
        requireNonNull(content);
        return InternalHashUtil.md5(content);
    }

}
