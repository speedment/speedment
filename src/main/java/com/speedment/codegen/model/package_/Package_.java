/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.codegen.model.package_;

import com.speedment.codegen.model.CodeModel;
import com.speedment.codegen.model.CodeModel.Type;

/**
 *
 * @author pemi
 */
public class Package_ implements CodeModel, Packagable {

    private String name_;
    private Package_ package_;

	public Package_() {}
	
	public Package_(final String name_) {
		this.name_ = name_;
	}
	
    public String getName_() {
        return name_;
    }

    public void setName_(final String name_) {
        this.name_ = name_;
    }

    @Override
    public Type getType() {
        return Type.PACKAGE;
    }

    @Override
    public Package_ getPackage() {
        return package_;
    }

    @Override
    public Package_ setPackage(final Package_ package_) {
        this.package_ = package_;
        return this;
    }

}
