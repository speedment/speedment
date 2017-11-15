package com.speedment.runtime.bulk;

import com.speedment.runtime.bulk.Operation.Type;
import com.speedment.runtime.config.identifier.HasTableIdentifier;
import com.speedment.runtime.config.identifier.TableIdentifier;
import java.util.stream.Stream;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Per Minborg
 */
public class BuilderTest {

    private HasTableIdentifier<Point> identifier;

    @Before
    public void setup() {
        identifier = new PointManager();
    }

    @Test
    public void testRemoveAll() {
        System.out.println("testRemoveAll");
        BulkOperation bo = BulkOperation.builder()
            .remove(identifier)
            .build();

        printInfo(bo);
    }

    @Test
    public void testRemoveOnePredicate() {
        System.out.println("testRemoveOnePredicate");
        BulkOperation bo = BulkOperation.builder()
            .remove(identifier).where(Point::isOrigo)
            .build();
        bo.operations().forEachOrdered(System.out::println);
    }

    @Test
    public void testRemoveTwoPredicates() {
        System.out.println("testRemoveTwoPredicates");
        BulkOperation bo = BulkOperation.builder()
            .remove(identifier).where(Point::isFirstQuadrant).where(Point::isOrigo)
            .build();

        printInfo(bo);
    }

    @Test
    public void testMixed() {
        System.out.println("testMixed");
        BulkOperation bo = BulkOperation.builder()
            .remove(identifier)
            .remove(identifier).where(Point::isOrigo)
            .remove(identifier).where(Point::isFirstQuadrant).where(Point::isOrigo)
            .update(identifier).set(p -> p.setX(p.getX() + 1)) // Consumer

            .update(identifier).compute(
            p -> {
                p.setX(0);
                return p;
            }) // Function
            .update(identifier).where(Point::isOrigo).set(s -> s.setX(s.getX()))
            .update(identifier).where(Point::isOrigo).where(Point::isFirstQuadrant).set(Point::increaseX).set(Point::increaseX)
            .persist(identifier).values(() -> Stream.of(new Point(1, 1), new Point(2, 2)))
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

    private static class PointManager implements HasTableIdentifier<Point> {

        @Override
        public TableIdentifier<Point> getTableIdentifier() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }

}
