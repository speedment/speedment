package com.speedment.internal.util.testing;

/**
 *
 * @author Per Minborg
 */
public class MemorySnapshot {

    private final long free, total, max;

    private MemorySnapshot() {
        System.gc();
        System.gc();
        final Runtime rt = Runtime.getRuntime();
        this.free = rt.freeMemory();
        this.total = rt.totalMemory();
        this.max = rt.maxMemory();
    }

    protected MemorySnapshot(long free, long total, long max) {
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

    
    public MemorySnapshot deltaSinceCreated() {
        return substract(new MemorySnapshot());
    }
    
    public static MemorySnapshot create() {
        return new MemorySnapshot();
    }

    public MemorySnapshot substract(MemorySnapshot operand) {
        return new MemorySnapshot(free() - operand.free(), total() - operand.total(), max() - operand.max());
    }

}
