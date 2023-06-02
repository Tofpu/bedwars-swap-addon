package io.tofpu.bedwarsswapaddon.newtest.example;

import io.tofpu.bedwarsswapaddon.newtest.object.LiveObject;

public class StringHolder extends LiveObject {
    private final int id;
    private final String data;

    public StringHolder(int id, String data) {
        this.id = id;
        this.data = data;
    }

    public int id() {
        return id;
    }

    public String data() {
        return data;
    }
}
