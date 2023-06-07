package io.tofpu.bedwarsswapaddon.wrapper;

import io.tofpu.bedwarsswapaddon.wrapper.snapshot.Snapshot;

public interface LiveObject<T> {
    void use(Snapshot snapshot);
    T object();
}
