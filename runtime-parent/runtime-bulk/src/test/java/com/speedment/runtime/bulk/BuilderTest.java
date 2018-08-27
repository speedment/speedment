/**
 *
 * Copyright (c) 2006-2018, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.bulk;

import com.speedment.runtime.bulk.Operation.Type;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.HasLabelSet;
import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.core.manager.Persister;
import com.speedment.runtime.core.manager.Remover;
import com.speedment.runtime.core.manager.Updater;
import com.speedment.runtime.field.Field;
import org.junit.Before;
import org.junit.Test;

import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 */
public class BuilderTest {

    private Manager<Point> mgr;

    @Before
    public void setup() {
        mgr = new PointManager();
    }

    @Test
    public void testRemoveAll() {
        System.out.println("testRemoveAll");
        BulkOperation bo = BulkOperation.builder()
            .remove(mgr)
            .build();

        printInfo(bo);
    }

    @Test
    public void testRemoveOnePredicate() {
        System.out.println("testRemoveOnePredicate");
        BulkOperation bo = BulkOperation.builder()
            .remove(mgr).where(Point::isOrigo)
            .build();
        bo.operations().forEachOrdered(System.out::println);
    }

    @Test
    public void testRemoveTwoPredicates() {
        System.out.println("testRemoveTwoPredicates");
        BulkOperation bo = BulkOperation.builder()
            .remove(mgr).where(Point::isFirstQuadrant).where(Point::isOrigo)
            .build();

        printInfo(bo);
    }

    /*
    void update(long id) {
        
        // Read the updates entity
        Point newPoint = sqlMgr.stream()
            .filter(Point.ID.equal(id)
            .findFirst()
            .get());
        
        // Refresh the in-JVM-memory store
        BulkOperation bo = BulkOperation.builder()
            .update(mgr)
            .where(Point.ID.equals(id))
            .compute(op -> newPoint);
            .build();
        
        dataStore.createJob(bo);
        
        // Batch update from database
        
    }

    void update(List<Long> ids) {
        
        // Read all updates entities using a single select and collect them to a Map
        Map<Long, Point> updates = sqlMgr.stream()
            .filter(Point.ID.in(ids))
            .collect(
                toMap(
                    Point::getId, 
                    Function.identity()
                )
            );
        
        // Refresh the in-JVM-memory store
        BulkOperation bo = BulkOperation.builder()
            .update(memMgr)
            .where(Point.ID.in(updates.keySet()))
            .compute(op -> updates.get(op.getId()));
            .build();
        
        dataStore.createJob(bo);
        
    }
    
    */
    
    @Test
    public void testMixed() {
        System.out.println("testMixed");
        BulkOperation bo = BulkOperation.builder()
            .remove(mgr)
            .remove(mgr).where(Point::isOrigo)
            .remove(mgr).where(Point::isFirstQuadrant).where(Point::isOrigo)
            .update(mgr).set(p -> p.setX(p.getX() + 1)) // Consumer

            .update(mgr).compute(
            p -> {
                p.setX(0);
                return p;
            }) // Function
            .update(mgr).where(Point::isOrigo).set(s -> s.setX(s.getX()))
            .update(mgr).where(Point::isOrigo).where(Point::isFirstQuadrant).set(Point::increaseX).set(Point::increaseX)
            .persist(mgr).values(() -> Stream.of(new Point(1, 1), new Point(2, 2)))
            .build();

        printInfo(bo);
    }

    private void printInfo(BulkOperation bo) {
        bo.operations().forEachOrdered(o -> {
            final Type type = o.type();
            System.out.format("%10s %s %n", type, o);
            switch (type) {
                case PERSIST: {
                    final PersistOperation<?> po = (PersistOperation) o;
                    po.generatorSuppliers().forEachOrdered(gs -> {
                        System.out.println("     generatorSupplier:" + gs);
                    });
                    break;
                }
                case UPDATE: {
                    final UpdateOperation<?> uo = (UpdateOperation) o;
                    uo.predicates().forEachOrdered(p -> {
                        System.out.println("     peredicate:" + p);
                    });
                    uo.consumers().forEachOrdered(c -> {
                        System.out.println("     consumer  :" + c);
                    });
                    uo.mappers().forEachOrdered(m -> {
                        System.out.println("     mapper    :" + m);
                    });
                    break;
                }
                case REMOVE: {
                    final RemoveOperation<?> ro = (RemoveOperation) o;
                    ro.predicates().forEachOrdered(p -> {
                        System.out.println("     peredicate:" + p);
                    });
                    break;
                }
            }

        });
    }

    private static class Point {

        private int x, y;

        public Point() {
        }

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        int getX() {
            return x;
        }

        int getY() {
            return y;
        }

        void setX(int x) {
            this.x = x;
        }

        void setY(int y) {
            this.y = y;
        }

        void increaseX() {
            this.x++;
        }

        void increaseY() {
            this.y++;
        }

        boolean isOrigo() {
            return x == 0 && y == 0;
        }

        boolean isFirstQuadrant() {
            return x >= 0 && y >= 0;
        }

        boolean isSecondQuadrant() {
            return x <= 0 && y >= 0;
        }

        @Override
        public String toString() {
            return new StringBuilder("Point(").append(x).append(", ").append(y).append(")").toString();
        }

    }

    private static class PointManager implements Manager<Point> {

        @Override
        public TableIdentifier<Point> getTableIdentifier() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Class<Point> getEntityClass() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Stream<Field<Point>> fields() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Stream<Field<Point>> primaryKeyFields() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Stream<Point> stream() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Persister<Point> persister() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Persister<Point> persister(HasLabelSet<Point> fields) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Updater<Point> updater() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Updater<Point> updater(HasLabelSet<Point> fields) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Remover<Point> remover() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }

}
