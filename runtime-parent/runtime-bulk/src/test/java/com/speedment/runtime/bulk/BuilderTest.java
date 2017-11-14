package com.speedment.runtime.bulk;

import com.speedment.runtime.bulk.Operation.Type;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.core.manager.Persister;
import com.speedment.runtime.core.manager.Remover;
import com.speedment.runtime.core.manager.Updater;
import com.speedment.runtime.field.Field;
import java.util.stream.Stream;
import org.junit.Before;
import org.junit.Test;

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
                    final UpdateOperation<?> po = (UpdateOperation) o;
                    po.predicates().forEachOrdered(p -> {
                        System.out.println("     peredicate:" + p);
                    });
                    po.consumers().forEachOrdered(c -> {
                        System.out.println("     consumer  :" + c);
                    });
                    po.mappers().forEachOrdered(m -> {
                        System.out.println("     mapper    :" + m);
                    });
                    break;
                }
                case REMOVE: {
                    final RemoveOperation<?> po = (RemoveOperation) o;
                    po.predicates().forEachOrdered(p -> {
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
        public Updater<Point> updater() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Remover<Point> remover() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }

}
