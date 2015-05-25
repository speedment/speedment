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
package com.speedment.util.json;

import com.speedment.core.field.doubles.DoubleField;
import com.speedment.core.field.ints.IntField;
import com.speedment.core.field.longs.LongField;
import com.speedment.core.field.reference.ComparableReferenceForeignKeyField;
import com.speedment.core.field.reference.ReferenceField;
import com.speedment.core.field.reference.ReferenceForeignKeyField;
import com.speedment.core.field.reference.string.StringReferenceForeignKeyField;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import static java.util.stream.Collectors.joining;

/**
 *
 * @author Emil Forslund
 * @param <ENTITY> Entity type
 */
public final class Json<ENTITY> {

	private final List<Function<ENTITY, String>> getters;
		
	public Json() {
		this.getters = new ArrayList<>();
	}
	
	public <T> Json<ENTITY> put(ReferenceField<ENTITY, T> field) {
		return put(field.getColumn().getName(), field::getFrom);
	}
	
	public Json<ENTITY> put(IntField<ENTITY> field) {
		return put(field.getColumn().getName(), field::getFrom);
	}
	
	public Json<ENTITY> put(LongField<ENTITY> field) {
		return put(field.getColumn().getName(), field::getFrom);
	}
	
	public Json<ENTITY> put(DoubleField<ENTITY> field) {
		return put(field.getColumn().getName(), field::getFrom);
	}
	
	public <T, FK_ENTITY> Json<ENTITY> put(ReferenceForeignKeyField<ENTITY, T, FK_ENTITY> field, Json<FK_ENTITY> builder) {
		return put(field.getColumn().getName(), field::findFrom, builder);
	}
	
	public <T extends Comparable<? super T>, FK_ENTITY> Json<ENTITY> put(ComparableReferenceForeignKeyField<ENTITY, T, FK_ENTITY> field, Json<FK_ENTITY> builder) {
		return put(field.getColumn().getName(), field::findFrom, builder);
	}
	
	public <FK_ENTITY> Json<ENTITY> put(StringReferenceForeignKeyField<ENTITY, FK_ENTITY> field, Json<FK_ENTITY> builder) {
		return put(field.getColumn().getName(), field::findFrom, builder);
	}

	public <T> Json<ENTITY> put(String label, Function<ENTITY, T> getter) {
		getters.add(e -> "\"" + label + "\":\"" + getter.apply(e) + "\"");
		return this;
	}
	
	public <T> Json<ENTITY> put(String label, ToDoubleFunction<ENTITY> getter) {
		getters.add(e -> "\"" + label + "\":" + getter.applyAsDouble(e));
		return this;
	}
	
	public <T> Json<ENTITY> put(String label, ToIntFunction<ENTITY> getter) {
		getters.add(e -> "\"" + label + "\":" + getter.applyAsInt(e));
		return this;
	}
	
	public <T> Json<ENTITY> put(String label, ToLongFunction<ENTITY> getter) {
		getters.add(e -> "\"" + label + "\":" + getter.applyAsLong(e));
		return this;
	}
	
	public <FK_ENTITY> Json<ENTITY> put(String label, Function<ENTITY, FK_ENTITY> getter, Json<FK_ENTITY> builder) {
		getters.add(e -> "\"" + label + "\":" + builder.from(getter.apply(e)));
		return this;
	}
	
	public String from(ENTITY entity) {
		return "{" + 
			getters.stream()
				.map(g -> g.apply(entity))
				.collect(joining(",")) + 
			"}";
	}
}