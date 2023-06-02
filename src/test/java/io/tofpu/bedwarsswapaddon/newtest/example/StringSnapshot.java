package io.tofpu.bedwarsswapaddon.newtest.example;

import io.tofpu.bedwarsswapaddon.newtest.object.LiveObject;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class StringSnapshot extends LiveObject.Snapshot<StringHolder> {
    public StringSnapshot(StringHolder snapshot) {
        super(snapshot);
    }

    @Override
    public void apply(StringHolder object) {
        for (Field field : object.getClass().getDeclaredFields()) {
            if (Modifier.isTransient(field.getModifiers())) {
                continue;
            }

            access(field, () -> {
                try {
                    Object snapshotValue = field.get(snapshot());
                    field.set(object, snapshotValue);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    private static void access(Field field, Runnable action) {
        boolean accessible = field.isAccessible();
        if (!accessible) {
            field.setAccessible(true);
        }

        action.run();

        if (!accessible) {
            field.setAccessible(false);
        }
    }
}
