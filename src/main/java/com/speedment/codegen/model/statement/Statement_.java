package com.speedment.codegen.model.statement;

import com.speedment.codegen.model.CodeModel;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author pemi
 */
public interface Statement_ extends CodeModel {


    default List<Statement_> getStatements() {
        return Collections.emptyList();
    }

    @Override
    default Type getModelType() {
        return Type.STATEMENT;
    }

    public static SimpleStatement of(CharSequence statement) {
        return new SimpleStatement(statement);
    }

}
