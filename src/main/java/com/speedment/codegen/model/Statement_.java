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
package com.speedment.codegen.model;

import com.speedment.codegen.model.block.Block_;

/**
 *
 * @author pemi
 */
public class Statement_ implements CodeModel {

    private CharSequence statementText_;
    private Block_ block_;

    public Statement_(CharSequence statementText) {
        this.statementText_ = statementText;
    }

    public Statement_(Block_ block_) {
        this.block_ = block_;
    }

    public CharSequence getStatementText() {
        return statementText_;
    }

    public void setStatementText(CharSequence statementText) {
        this.statementText_ = statementText;
    }

    public Block_ getBlock() {
        return block_;
    }

    public void setBlock(Block_ block_) {
        this.block_ = block_;
    }

    @Override
    public Type getModelType() {
        return Type.STATEMENT;
    }
}
