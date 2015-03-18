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
package com.speedment.codegen.base;

/**
 *
 * @author Emil Forslund
 */
public interface Code<M> {
    String getText();
    CodeView<?> getView();
    Installer getInstaller();
    M getModel();
    
    class Impl<M> implements Code<M> {
    
        private String text;
        private CodeView<?> view;
        private Installer installer;
        private M model;

        Impl() {}

        @Override
        public String getText() {
            return text;
        }

        protected Impl<M> setCode(String code) {
            this.text = code;
            return this;
        }

        @Override
        public CodeView<?> getView() {
            return view;
        }

        protected Impl<M> setView(CodeView<?> view) {
            this.view = view;
            return this;
        }

        @Override
        public Installer getInstaller() {
            return installer;
        }

        protected Impl<M> setInstaller(Installer installer) {
            this.installer = installer;
            return this;
        }

        @Override
        public M getModel() {
            return model;
        }

        public Impl<M> setModel(M model) {
            this.model = model;
            return this;
        }
    }
}
