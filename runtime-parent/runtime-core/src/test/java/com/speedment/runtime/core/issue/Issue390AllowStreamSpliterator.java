package com.speedment.runtime.core.issue;

import com.speedment.runtime.core.component.sql.SqlStreamOptimizerComponent;
import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import com.speedment.runtime.core.component.sql.override.SqlStreamTerminatorComponent;
import com.speedment.runtime.core.db.AsynchronousQueryResult;
import com.speedment.runtime.core.db.ConnectionUrlGenerator;
import com.speedment.runtime.core.db.DbmsMetadataHandler;
import com.speedment.runtime.core.db.DbmsOperationHandler;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.db.FieldPredicateView;
import com.speedment.runtime.core.internal.component.sql.SqlStreamOptimizerComponentImpl;
import com.speedment.runtime.core.internal.component.sql.override.SqlStreamTerminatorComponentImpl;
import com.speedment.runtime.core.internal.db.AbstractDbmsType;
import com.speedment.runtime.core.internal.db.AsynchronousQueryResultImpl;
import com.speedment.runtime.core.internal.manager.sql.SqlStreamTerminator;
import com.speedment.runtime.core.internal.stream.builder.pipeline.PipelineImpl;
import com.speedment.runtime.core.internal.stream.builder.pipeline.ReferencePipeline;
import com.speedment.runtime.core.stream.parallel.ParallelStrategy;
import com.speedment.runtime.field.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Stream;
import org.junit.Test;

/**
 *
 * @author Per Minborg
 */
public class Issue390AllowStreamSpliterator {

    @Test
    public void test() {
        final ReferencePipeline<String> pipeline = new PipelineImpl<>(() -> Stream.of("a", "b"));

        DbmsType dbmsType = new AbstractDbmsType() {
            @Override
            public String getName() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getDriverManagerName() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public int getDefaultPort() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getDbmsNameMeaning() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String getDriverName() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public DbmsMetadataHandler getMetadataHandler() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public DbmsOperationHandler getOperationHandler() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public ConnectionUrlGenerator getConnectionUrlGenerator() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public FieldPredicateView getFieldPredicateView() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        
        final SqlStreamOptimizerInfo<String> info = 
        SqlStreamOptimizerInfo.of(
            dbmsType   , 
            "select name from name_table", 
            "select count(*) from name_table", 
            (s, l) -> 1l, 
            (Field<String> f) -> "name", 
            (Field<String> f) -> String.class
        );

        final AsynchronousQueryResult<String> asynchronousQueryResult = new AsynchronousQueryResultImpl<>(
            "select name from name_table",
            new ArrayList<>(),
            rs -> "z",
            () -> null,
            ParallelStrategy.computeIntensityDefault(),
            ps -> {},
            rs -> {}
        );
        final SqlStreamOptimizerComponent sqlStreamOptimizerComponent = new SqlStreamOptimizerComponentImpl();
        final SqlStreamTerminatorComponent sqlStreamTerminatorComponent = new SqlStreamTerminatorComponentImpl();

        SqlStreamTerminator<String> streamTerminator = new SqlStreamTerminator<>(
            info,
            asynchronousQueryResult,
            sqlStreamOptimizerComponent,
            sqlStreamTerminatorComponent,
            true
        );

        Iterator<String> iterator = streamTerminator.iterator(pipeline);

        while (iterator.hasNext()) {
            String next = iterator.next();
            System.out.println(next);
        }

    }

}
