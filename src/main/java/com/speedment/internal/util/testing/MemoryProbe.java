package com.speedment.internal.util.testing;

/**
 *
 * @author Per Minborg
 */
public class MemoryProbe {

    private final long free, total, max;

    private MemoryProbe() {
        System.gc();
        System.gc();
        final Runtime rt = Runtime.getRuntime();
        this.free = rt.freeMemory();
        this.total = rt.totalMemory();
        this.max = rt.maxMemory();
    }

    protected MemoryProbe(long free, long total, long max) {
        this.free = free;
        this.total = total;
        this.max = max;
    }

    public long free() {
        return free;
    }

    public long total() {
        return total;
    }

    public long max() {
        return max;
    }

    public long used() {
        return total() - free();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "{free=" + free()
                + ", total=" + total()
                + ", max=" + max()
                + ", used=" + used()
                + "}";
    }

    
    public MemoryProbe deltaSinceCreated() {
        return substract(new MemoryProbe());
    }
    
    public static MemoryProbe create() {
        return new MemoryProbe();
    }

    public MemoryProbe substract(MemoryProbe operand) {
        return new MemoryProbe(free() - operand.free(), total() - operand.total(), max() - operand.max());
    }

}
