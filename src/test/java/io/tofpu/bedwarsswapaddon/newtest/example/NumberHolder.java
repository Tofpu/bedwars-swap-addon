package io.tofpu.bedwarsswapaddon.newtest.example;

import io.tofpu.bedwarsswapaddon.newtest.object.LiveObject;

public class NumberHolder extends LiveObject {
    private int number;

    public NumberHolder(int number) {
        this.number = number;
    }

    public void number(int number) {
        this.number = number;
    }

    public int number() {
        return number;
    }
}
