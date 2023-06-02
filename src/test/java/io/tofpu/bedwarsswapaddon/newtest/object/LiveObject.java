package io.tofpu.bedwarsswapaddon.newtest.object;

public abstract class LiveObject implements Cloneable {
    @Override
    public LiveObject clone() {
        try {
            return (LiveObject) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public abstract static class Snapshot<O extends LiveObject> {
        protected final O snapshot;

        @SuppressWarnings("unchecked")
        public Snapshot(O snapshot) {
            this.snapshot = (O) snapshot.clone();
        }

        public abstract void apply(O object);

        public O snapshot() {
            return snapshot;
        }
    }
}
