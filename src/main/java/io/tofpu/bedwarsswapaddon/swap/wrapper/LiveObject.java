package io.tofpu.bedwarsswapaddon.swap.wrapper;

import io.tofpu.bedwarsswapaddon.swap.snapshot.Snapshot;

public interface LiveObject<T> {
    void use(Snapshot snapshot);
    T object();
}
