package com.speedment.codegen.model;

/**
 *
 * @author pemi
 */
public class Statement_ {

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

}
