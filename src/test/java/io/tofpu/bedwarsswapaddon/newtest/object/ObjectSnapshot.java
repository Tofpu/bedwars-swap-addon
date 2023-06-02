package io.tofpu.bedwarsswapaddon.newtest.object;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ObjectSnapshot<O extends LiveObject> extends LiveObject.Snapshot<O> {
    public ObjectSnapshot(O snapshot) {
        super(snapshot);
    }

    @Override
    public void apply(O object) {
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
