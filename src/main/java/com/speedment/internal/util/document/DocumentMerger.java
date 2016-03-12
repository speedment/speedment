/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.util.document;

import com.speedment.config.Document;
import com.speedment.config.db.trait.HasName;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.core.config.BaseDocument;
import com.speedment.ui.config.DocumentProperty;
import static com.speedment.util.StaticClassUtil.instanceNotAllowed;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import javafx.collections.ObservableList;
import static java.util.Objects.requireNonNull;
import java.util.function.Function;
import static java.util.stream.Collectors.toList;
import javafx.beans.property.Property;

/**
 * A utility class for merging two documents to preserve maximum amount of
 * information.
 * 
 * @author Emil Forslund
 */
public final class DocumentMerger {
    
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
            DocumentProperty existing, 
            Document proposed,
            DocumentConstructor constructor
        ) {
        
        // Go through the union of both the documents keys.
        final Set<String> newKeys = new HashSet<>(proposed.getData().keySet());
        for (final String key : newKeys) {
            final Object proposedValue = proposed.getData().get(key);
            
            // Check if the proposed value fulfills the requirements to be
            // considered a list of child documents.
            boolean wasChild = false;
            if (proposedValue instanceof List<?>) {
                @SuppressWarnings("unchecked")
                final List<Object> list = (List<Object>) proposedValue;
                
                if (!list.isEmpty()) {
                    final Object first = list.get(0);
                    
                    if (first instanceof Map<?, ?>) {
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
                        
                        wasChild = true;
                    }
                }
            }
            
            // If it wasn't a key used for child documents, consider it a 
            // property.
            if (!wasChild) {
                
                if (setPropertyIf(String.class,  proposedValue, casted -> existing.stringPropertyOf(key,  () -> casted))
                ||  setPropertyIf(Boolean.class, proposedValue, casted -> existing.booleanPropertyOf(key, () -> casted))
                ||  setPropertyIf(Integer.class, proposedValue, casted -> existing.integerPropertyOf(key, () -> casted))
                ||  setPropertyIf(Long.class,    proposedValue, casted -> existing.longPropertyOf(key,    () -> casted))
                ||  setPropertyIf(Float.class,   proposedValue, casted -> existing.doublePropertyOf(key,  () -> casted))
                ||  setPropertyIf(Double.class,  proposedValue, casted -> existing.doublePropertyOf(key,  () -> casted))
                ||  setPropertyIf(Byte.class,    proposedValue, casted -> existing.doublePropertyOf(key,  () -> casted))
                ||  setPropertyIf(Short.class,   proposedValue, casted -> existing.doublePropertyOf(key,  () -> casted))
                ||  setPropertyIf(Object.class,  proposedValue, casted -> existing.objectPropertyOf(key,  (Class<Object>) casted.getClass(), () -> casted))) {}
                else {
                    throw new SpeedmentException(
                        "Property was not of any known type."
                    );
                }
            }
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
            DocumentProperty parent,
            String key,
            List<Document> proposed, 
            DocumentConstructor constructor) {

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
                    tasks.add(first.getName(), new MergeWithoutMovingTask(first, second, constructor));
                    
                // if they are mentioning different elements, find the 
                // entities in the other list and create a task for each.
                } else {
                    tasks.add(first.getName(), new MergeIntoExistingTask(first, constructor));
                    tasks.add(HasName.of(second).getName(), new MergeByMovingTask(parent, second, key, constructor));
                }
            } else {
                if (i < existing.size()) {
                    final DocumentProperty first = existing.get(i);
                    tasks.add(first.getName(), new MergeIntoExistingTask(first, constructor));
                } else if (i < proposed.size()) {
                    final Document second = proposed.get(i);
                    tasks.add(HasName.of(second).getName(), new MergeByMovingTask(parent, second, key, constructor));
                } else {
                    throw new SpeedmentException( // Not possible.
                        "This stage should never be reached."
                    );
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
     * the same value on their {@code name} property. Equality is not used to
     * determine if two documents are the same since they could have been 
     * created during different stages and with different data.
     * <p>
     * If one or both of the documents does not have a name property specified,
     * the default value specified by {@link HasName} will be used.
     * 
     * @param first   the first document
     * @param second  the second document
     * @return        true if they represent the same data
     */
    private static boolean isSame(Document first, Document second) {
        final Optional<? extends Document> firstParent  = first.getParent();
        final Optional<? extends Document> secondParent = second.getParent();
        
        if (firstParent.isPresent() ^ secondParent.isPresent()) {
            return false;
        }
        
        final String firstName  = HasName.of(first).getName();
        final String secondName = HasName.of(second).getName();
        final boolean namesEqual = Objects.equals(firstName, secondName);
        
        if (firstParent.isPresent()) {
            return namesEqual && isSame(firstParent.get(), secondParent.get());
        } else {
            return namesEqual;
        }
    }
    
    /**
     * Sets a property generated with the specified getter to a value if it is
     * of the correct type and the setting would not break any bindings. If a
     * binding would be broken, a {@link SpeedmentException} is thrown.
     * 
     * @param <T>             the expected type of the value
     * @param <P>             the property type
     * @param type            the expected type of the value
     * @param value           the value
     * @param propertyGetter  getter for the property
     * @return                {@code true} if a value was set, else {@code false}
     */
    private static <T, P extends Property<? super T>> boolean setPropertyIf(
            Class<T> type, 
            Object value,
            Function<T, P> propertyGetter) {
        
        if (type.isInstance(value)) {
            final T casted = type.cast(value);
            final P property = propertyGetter.apply(casted);
            
            if (!Objects.equals(casted, property.getValue())) {
                if (property.isBound()) {
                    throw new SpeedmentException(
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
    private final static class MergeTaskManager {
        
        private final List<MergeTask> tasks = new LinkedList<>();
        
        public void add(String name, MergeTask task) {
            final Iterator<MergeTask> iterator = tasks.iterator();
            
            while (iterator.hasNext()) {
                final MergeTask next = iterator.next();
                
                if (next.canBeReplaced(name)) {
                    iterator.remove();
                }
            }
            
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
         * @param name  the name of the replacing document
         * @return      {@code true} if this can be removed, else {@code false}
         */
        boolean canBeReplaced(String name);
        
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
    private final static class MergeWithoutMovingTask implements MergeTask {

        private final DocumentProperty existing;
        private final Document proposed;
        private final DocumentConstructor constructor;
        
        private MergeWithoutMovingTask(
                DocumentProperty existing, 
                Document proposed, 
                DocumentConstructor constructor) {
            
            this.existing    = requireNonNull(existing);
            this.proposed    = requireNonNull(proposed);
            this.constructor = requireNonNull(constructor);
        }
        
        @Override
        public boolean canBeReplaced(String name) {
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
    private final static class MergeIntoExistingTask implements MergeTask {

        private final DocumentProperty document;
        private final String name;
        private final DocumentConstructor constructor;
        
        private MergeIntoExistingTask(DocumentProperty document, DocumentConstructor constructor) {
            this.document    = requireNonNull(document);
            this.name        = HasName.of(document).getName();
            this.constructor = requireNonNull(constructor);
        }

        @Override
        public boolean canBeReplaced(String name) {
            return this.name.equals(name);
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
    private final static class MergeByMovingTask implements MergeTask {
        
        private final DocumentProperty parent;
        private final Document document;
        private final String key;
        private final DocumentConstructor constructor;
        
        private MergeByMovingTask(DocumentProperty parent, Document document, String key, DocumentConstructor constructor) {
            this.parent      = parent; // Can be null.
            this.document    = requireNonNull(document);
            this.key         = requireNonNull(key);
            this.constructor = requireNonNull(constructor);
        }

        @Override
        public boolean canBeReplaced(String name) {
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
            
            // Name need to be set manually before the two documents are merged
            inst.stringPropertyOf(HasName.NAME, () -> null)
                .setValue(document.getAsString(HasName.NAME)
                    .orElseThrow(() -> new SpeedmentException(
                        "Attempting to merge by moving document without name. " + 
                        document
                    ))
                );
            
            return inst;
        }
    }
    
    private DocumentMerger() { instanceNotAllowed(getClass()); }
}