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
package com.speedment.runtime.core.component;

import com.speedment.common.injector.annotation.InjectKey;

/**
 * Contains general information about the installment like the software title
 * and version. This is used to print correct messages for an example when the
 * application launches.
 *
 * @author  Emil Forslund
 * @since   3.0.0
 */
@InjectKey(InfoComponent.class)
public interface InfoComponent  {

    /**
     * Returns the vendor of the Speedment product. This is primarily used to 
     * show meaningful information to the user.
     *
     * @return the vendor
     */
    String getVendor();

    /**
     * Returns the title of the Speedment product. This is primarily used to
     * show meaningful information to the user.
     *
     * @return the title
     */
    String getTitle();

    /**
     * Returns the subtitle of the Speedment product. This is primarily used to
     * show meaningful information to the user.
     *
     * @return the subtitle
     */
    String getSubtitle();

    /**
     * The name of the official repository of the Speedment product. This is
     * used to lookup the latest version.
     *
     * @return  the name of the official repository
     */
    String getRepository();

    /**
     * Return the non-null version of this Speedment implementation. It consists
     * of any string assigned by the vendor of this implementation and does not
     * have any particular syntax specified or expected by the Java runtime. It
     * may be compared for equality with other package version strings used for
     * this implementation by this vendor for this package.
     *
     * @return the non-null version of this Speedment implementation
     */
    String getImplementationVersion();

    /**
     * Returns the non-null version number of the specification that this
     * Speedment implements. This version string must be a sequence of
     * non-negative decimal integers separated by "."'s and may have leading
     * zeros. When version strings are compared the most significant numbers are
     * compared.
     *
     * @return the non-null version number of the specification that this
     * Speedment implements
     */
    String getSpecificationVersion();

    /**
     * Returns the non-null "nick-name" of the specification that this
     * Speedment implements.
     *
     * @return the non-null "nick-name" of the specification that this
     * Speedment implements
     */
    String getSpecificationNickname();

     /**
     * Returns if this version is intended for production use.
     *
     * @return if this version is intended for production use
     */
    default boolean isProductionMode() {
        return !getImplementationVersion().toUpperCase().contains("EA")
            && !getImplementationVersion().toUpperCase().contains("SNAPSHOT");
    }

    default String getLicenseName() {
        return "Apache-2.0";
    }

    default String getEditionAndVersionString() {
        return getTitle() + ":" + getImplementationVersion();
    }

    // Almost: http://patorjk.com/software/taag/#p=display&f=Big&t=Type%20Something%20

    default String getBanner() {
        return String.format(
              "   ____                   _                     _     %n"+
              "  / ___'_ __  __  __   __| |_ __ __    __ _ __ | |    %n"
            + "  \\___ | '_ |/  \\/  \\ / _  | '_ \\ _ \\ /  \\ '_ \\| |_   %n"
            + "   ___)| |_)| '_/ '_/| (_| | | | | | | '_/ | | |  _|  %n"
            + "  |____| .__|\\__\\\\__\\ \\____|_| |_| |_|\\__\\_| |_| '_   %n"
            + "=======|_|======================================\\__|==");
    }


}
