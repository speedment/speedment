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
package com.speedment.codegen.lang.models;

import com.speedment.codegen.lang.interfaces.Callable;
import com.speedment.codegen.lang.interfaces.Copyable;
import com.speedment.codegen.lang.models.implementation.JavadocImpl;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/**
 *
 * @author Emil Forslund
 */
public interface Javadoc extends Copyable<Javadoc>, Callable<Javadoc> {
    default Javadoc add(String row) {
		getRows().add(row);
		return this;
	}
	
	default Javadoc add(String first, String... rows) {
		getRows().add(first);
		Collections.addAll(getRows(), rows);
		return this;
	}

	default Javadoc add(JavadocTag tag) {
		getTags().add(tag);
		return this;
	}

    List<String> getRows();
	List<JavadocTag> getTags();
    
    enum Factory { INST;
        private Supplier<Javadoc> prototype = () -> new JavadocImpl();
    }

    static Javadoc of() {
        return Factory.INST.prototype.get();
    }
    
    static Javadoc of(String row) {
        return Factory.INST.prototype.get().add(row);
    }
    
    static Javadoc of(String row, String... rows) {
        return Factory.INST.prototype.get().add(row, rows);
    }
    
    static void setSupplier(Supplier<Javadoc> a) {
        Factory.INST.prototype = a;
    }
}