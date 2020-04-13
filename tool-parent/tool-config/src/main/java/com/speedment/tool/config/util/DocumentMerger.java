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
package com.speedment.tool.config.util;


import com.speedment.runtime.config.Document;
import com.speedment.runtime.config.exception.SpeedmentConfigException;
import com.speedment.runtime.config.provider.BaseDocument;
import com.speedment.runtime.config.trait.HasId;
import com.speedment.runtime.config.trait.HasIdUtil;
import com.speedment.runtime.config.trait.HasName;
import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.tool.config.DocumentProperty;
import javafx.beans.property.Property;
import javafx.collections.ObservableList;

import java.util.*;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

/**
 * A utility class for merging two documents to preserve maximum amount of
 * information.
 * 
 * @author  Emil Forslund
 * @since   2.3.0
 */
public final class DocumentMerger {

    private DocumentMerger() {}

    /**
     * Merges a single document into an existing one, trying to save as much
     * information as possible. If new {@link DocumentProperty properties} need
     * to be created, the specified constructor is used.
     * 
     * @param existing     the document to merge into
     * @param proposed     the document to merge with
     * @param constructor  used to create new {@link DocumentProperty}
     */
    public static void merge(
            final DocumentProperty existing,
            final Document proposed,
            final DocumentConstructor constructor) {

        requireNonNull(existing);
        requireNonNull(proposed);
        requireNonNull(constructor);
        
        // Go through the keys specified by the proposed document
        proposed.getData().entrySet()
            .forEach(entry -> handleEntry(existing, proposed, constructor, entry));
    }

    private static void handleEntry(DocumentProperty existing, Document proposed, DocumentConstructor constructor, Map.Entry<String, Object> entry) {
        final String key           = entry.getKey();
        final Object proposedValue = proposed.getData().get(key); // Or null

        // Check if the proposed value fulfills the requirements to be
        // considered a list of child documents.
        if (proposedValue instanceof List<?>) {
            @SuppressWarnings("unchecked")
            final List<Object> list = (List<Object>) proposedValue;
            handleList(existing, proposed, constructor, key, list);
        } else {
            // If it wasn't a key used for child documents, consider it a
            // property.
            handleProperty(existing, proposedValue, key);
        }
    }

    private static void handleList(DocumentProperty existing, Document proposed, DocumentConstructor constructor, String key, List<Object> list) {
        if (list.isEmpty()) {
            // Make sure the list is initialized.
            existing.observableListOf(key);
        } else {
            final Object first = list.get(0);

            if (first instanceof Map<?, ?>) {
                @SuppressWarnings("unchecked")
                final List<Document> documents = list.stream()
                    .map(data -> (Map<String, Object>) data)
                    .map(data -> new BaseDocument(proposed, data))
                    .collect(toList());

                // Merge the new children into the existing document.
                merge(
                    existing,
                    key,
                    documents,
                    constructor
                );
            } else {
                throw new SpeedmentConfigException(
                    "Only Lists of Maps are supported in the " +
                        "document model."
                );
            }
        }

    }

    private static void handleProperty(DocumentProperty existing, Object proposedValue, String key) {
        // The block of this is intentionally left empty so that the
        // conditions are evaluated until one of them return true.
        if (setPropertyIf(String.class,  proposedValue, casted -> existing.stringPropertyOf(key,  () -> casted))
            ||  setPropertyIf(Boolean.class, proposedValue, casted -> existing.booleanPropertyOf(key, () -> casted))
            ||  setPropertyIf(Integer.class, proposedValue, casted -> existing.integerPropertyOf(key, () -> casted))
            ||  setPropertyIf(Long.class,    proposedValue, casted -> existing.longPropertyOf(key,    () -> casted))
            ||  setPropertyIf(Float.class,   proposedValue, casted -> existing.doublePropertyOf(key,  () -> casted))
            ||  setPropertyIf(Double.class,  proposedValue, casted -> existing.doublePropertyOf(key,  () -> casted))
            ||  setPropertyIf(Byte.class,    proposedValue, casted -> existing.integerPropertyOf(key,  () -> casted))
            ||  setPropertyIf(Short.class,   proposedValue, casted -> existing.integerPropertyOf(key,  () -> casted)))
        {
            // Do nothing
        } else {
            throw new SpeedmentConfigException(
                "Property was not of any known type."
            );
        }
    }

    /**
     * Merges the specified document on the specified key with a specified list
     * of proposed children. If a new {@link DocumentProperty} needs to be
     * created, the specified constructor is used.
     * 
     * @param parent       the parent to merge into
     * @param key          the key to merge on
     * @param proposed     the documents to merge with
     * @param constructor  used to create new {@link DocumentProperty}
     */
    private static void merge(
            final DocumentProperty parent,
            final String key,
            final List<Document> proposed,
            final DocumentConstructor constructor) {

        requireNonNull(parent);
        requireNonNull(key);
        requireNonNull(proposed);
        requireNonNull(constructor);
        
        // Create a list of tasks that should be executed to merge the lists.
        final MergeTaskManager tasks = new MergeTaskManager();
        final ObservableList<DocumentProperty> existing = parent.observableListOf(key);
        
        // Go through every item and create a task for every document
        for (int i = 0; i < Math.max(existing.size(), proposed.size()); i++) {
            
            // If both lists have remaining elements
            if (i < existing.size() && i < proposed.size()) {
                final DocumentProperty first = existing.get(i);
                final Document second = proposed.get(i);
                
                // If they are mentioning the same element, simply merge them 
                // into the same position.
                if (isSame(first, second)) {
                    tasks.add(first.getId(), new MergeWithoutMovingTask(first, second, constructor));
                    
                // if they are mentioning different elements, find the 
                // entities in the other list and create a task for each.
                } else {
                    tasks.add(first.getId(), new MergeIntoExistingTask(first, constructor));
                    tasks.add(HasId.of(second).getId(), new MergeByMovingTask(parent, second, key, constructor));
                }
            } else {
                if (i < existing.size()) {
                    final DocumentProperty first = existing.get(i);
                    tasks.add(first.getId(), new MergeIntoExistingTask(first, constructor));
                } else {
                    final Document second = proposed.get(i);
                    tasks.add(HasId.of(second).getId(), new MergeByMovingTask(parent, second, key, constructor));
                }
            }
        }
        
        // Execute merge
        tasks.execute(existing);
    }
    
    /**
     * Describes a constructor for a DocumentProperty.
     */
    @FunctionalInterface
    public interface DocumentConstructor {
        DocumentProperty create(DocumentProperty parent, String key);
    }
    
    /**
     * Returns if the two documents corresponds to the same data by first 
     * comparing if their parents are the same and then comparing if they have
     * the same value on their {@code id} property. Equality is not used to
     * determine if two documents are the same since they could have been 
     * created during different stages and with different data.
     * <p>
     * If one or both of the documents does not have an id property specified,
     * the default value specified by {@link HasName} will be used.
     * 
     * @param first   the first document
     * @param second  the second document
     * @return        true if they represent the same data
     */
    private static boolean isSame(
            final Document first,
            final Document second) {

        requireNonNull(first);
        requireNonNull(second);
        
        final Optional<? extends Document> firstParent  = first.getParent();
        final Optional<? extends Document> secondParent = second.getParent();
        
        if (firstParent.isPresent() ^ secondParent.isPresent()) {
            return false;
        }
        
        final String firstId  = HasId.of(first).getId();
        final String secondId = HasId.of(second).getId();
        final boolean idsEqual = Objects.equals(firstId, secondId);

        return firstParent.map(doc ->
            idsEqual && isSame(doc, secondParent.get())
        ).orElse(idsEqual);
    }
    
    /**
     * Sets a property generated with the specified getter to a value if it is
     * of the correct type and the setting would not break any bindings. If a
     * binding would be broken, a {@link SpeedmentException} is thrown.
     * 
     * @param <T>             the expected type of the value
     * @param <P>             the property type
     * @param type            the expected type of the value
     * @param value           the value or null
     * @param propertyGetter  getter for the property
     * @return                {@code true} if a value was set, else {@code false}
     */
    private static <T, P extends Property<? super T>> boolean setPropertyIf(
            final Class<T> type,
            final Object value, // Nullable
            final Function<T, P> propertyGetter) {

        requireNonNull(type);
        requireNonNull(propertyGetter);
        
        if (type.isInstance(value)) {
            final T casted = type.cast(value);
            final P property = propertyGetter.apply(casted);
            
            if (!Objects.equals(casted, property.getValue())) {
                if (property.isBound()) {
                    throw new SpeedmentConfigException(
                        "Attempting to set bound " + 
                        type.getSimpleName() + "Property " +
                        " to new value '" + casted + "'."
                    );
                } else {
                    property.setValue(casted);
                }
            }

            return true;
        }

        return false;
    }
   
    /**
     * An ordered collection of tasks that can be executed to merge a set of
     * changes into an ObservableList without destroying any observables.
     */
    private static final class MergeTaskManager {
        
        private final List<MergeTask> tasks = new LinkedList<>();
        
        public void add(String id, MergeTask task) {
            tasks.removeIf(next -> next.canBeReplaced(id));
            tasks.add(task);
        }
        
        public void execute(ObservableList<DocumentProperty> existing) {
            tasks.forEach(task -> task.execute(existing));
        }
    }
    
    /**
     * A task in the merger process that describes what to do with a specific
     * key entry. Some tasks can be replaced, which is indicated by the
     * {@link #canBeReplaced(java.lang.String)} method.
     */
    private interface MergeTask  {

        /**
         * Return {@code true} if this task can be removed given that another
         * task is created for the specified document.
         * 
         * @param id    the id of the replacing document
         * @return      {@code true} if this can be removed, else {@code false}
         */
        boolean canBeReplaced(String id);
        
        /**
         * Executes this merging task on the specified list.
         * 
         * @param target  the list to merge with
         */
        void execute(ObservableList<DocumentProperty> target);
    }
    
    /**
     * Merges two documents into one without moving neither or checking for
     * existance. This is done when both sources agree on the documents position.
     */
    private static final class MergeWithoutMovingTask implements MergeTask {

        private final DocumentProperty existing;
        private final Document proposed;
        private final DocumentConstructor constructor;
        
        private MergeWithoutMovingTask(
                final DocumentProperty existing,
                final Document proposed,
                final DocumentConstructor constructor) {

            this.existing    = requireNonNull(existing);
            this.proposed    = requireNonNull(proposed);
            this.constructor = requireNonNull(constructor);
        }
        
        @Override
        public boolean canBeReplaced(String id) {
            // Is always false since this task is already handling both documents.
            return false;
        }
        
        @Override
        public void execute(ObservableList<DocumentProperty> target) {
            merge(existing, proposed, constructor);
        }
    }
    
    /**
     * Merges a document into the source without moving it if it already exists.
     * If it does not yet exist in the source, merge it into the end. This is 
     * done with the submissive document.
     */
    private static final class MergeIntoExistingTask implements MergeTask {

        private final DocumentProperty document;
        private final String id;
        private final DocumentConstructor constructor;
        
        private MergeIntoExistingTask(
                final DocumentProperty document,
                final DocumentConstructor constructor) {

            this.document    = requireNonNull(document);
            this.id          = HasId.of(document).getId();
            this.constructor = requireNonNull(constructor);
        }

        @Override
        public boolean canBeReplaced(String id) {
            return this.id.equals(id);
        }
        
        @Override
        public void execute(ObservableList<DocumentProperty> target) {
            int existingIndex = -1;
            for (int i = 0; i < target.size(); i++) {
                if (isSame(target.get(i), document)) {
                    existingIndex = i;
                    break;
                }
            }
            
            // If the document has not yet been added, add it to the end.
            if (existingIndex < 0) {
                target.add(document);
            
            // If it already exists, merge it into this document and replace it
            // in the list.
            } else {
                final DocumentProperty existing = target.remove(existingIndex);
                merge(document, existing, constructor);
                target.add(existingIndex, document);
            }
        }
        
    }
    
    /**
     * Merges a document into the source by adding it to the end. If it already
     * exists, move the existing to the end and merge into it. This is done with
     * the superior document.
     */
    private static final class MergeByMovingTask implements MergeTask {
        
        private final DocumentProperty parent;
        private final Document document;
        private final String key;
        private final DocumentConstructor constructor;
        
        private MergeByMovingTask(
                final DocumentProperty parent,
                final Document document,
                final String key,
                final DocumentConstructor constructor) {

            this.parent      = parent; // Can be null.
            this.document    = requireNonNull(document);
            this.key         = requireNonNull(key);
            this.constructor = requireNonNull(constructor);
        }

        @Override
        public boolean canBeReplaced(String id) {
            return false;
        }
        
        @Override
        public void execute(ObservableList<DocumentProperty> target) {
            int existingIndex = -1;
            for (int i = 0; i < target.size(); i++) {
                if (isSame(target.get(i), document)) {
                    existingIndex = i;
                    break;
                }
            }
            
            // If it exists, remove it, else create it.
            final DocumentProperty existing = (existingIndex < 0)
                ? createWithConstructor()
                : target.remove(existingIndex);
            
            merge(existing, document, constructor);
            target.add(existing);
        }
        
        private DocumentProperty createWithConstructor() {
            final DocumentProperty inst = constructor.create(parent, key);
            
            // Name needs to be set manually before the two documents are merged
            inst.stringPropertyOf(HasIdUtil.ID, () -> null)
                .setValue(HasId.of(document).getId());
            
            return inst;
        }
    }
    

}