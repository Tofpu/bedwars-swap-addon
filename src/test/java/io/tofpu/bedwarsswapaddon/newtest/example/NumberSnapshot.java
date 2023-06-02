package io.tofpu.bedwarsswapaddon.newtest.example;

import io.tofpu.bedwarsswapaddon.newtest.object.LiveObject;

public class NumberSnapshot extends LiveObject.Snapshot<NumberHolder> {
    public NumberSnapshot(NumberHolder snapshot) {
        super(snapshot);
    }

    @Override
    public void apply(NumberHolder object) {
        object.number(snapshot.number());
    }
}
