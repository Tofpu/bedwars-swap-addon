package io.tofpu.bedwarsswapaddon.newtest;

import io.tofpu.bedwarsswapaddon.newtest.example.NumberHolder;
import io.tofpu.bedwarsswapaddon.newtest.example.NumberSnapshot;
import io.tofpu.bedwarsswapaddon.newtest.example.StringHolder;
import io.tofpu.bedwarsswapaddon.newtest.example.StringSnapshot;
import io.tofpu.bedwarsswapaddon.newtest.object.ObjectSnapshot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SnapshotTest {
    @Test
    void manual_snapshot_test() {
        final NumberHolder object = new NumberHolder(2);
        // clones object, meaning anything after this point should not affect the snapshot
        final NumberSnapshot snapshot = new NumberSnapshot(object);

        // snapshot should not be affected by this
        object.number(3);
        // verifies that object's number is indeed 3
        Assertions.assertEquals(3, object.number());

        // verifies that snapshot is still intact, despite changes to the object
        Assertions.assertEquals(2, snapshot.snapshot().number());

        // applies snapshot changes to object
        snapshot.apply(object);

        // verifies that the object number was reverted back to the snapshot
        Assertions.assertEquals(2, object.number());
    }

    @Test
    void reflection_snapshot_test() {
        StringHolder object = new StringHolder(2, "Hello World!");
        // clones object, meaning anything after this point should not affect the snapshot
        final StringSnapshot snapshot = new StringSnapshot(object);

        // snapshot should not be affected by this
        object = new StringHolder(2, "Hello Sun!");
        // verifies that object's number is indeed 3
        Assertions.assertEquals("Hello Sun!", object.data());

        // verifies that snapshot is still intact, despite changes to the object
        Assertions.assertEquals(2, snapshot.snapshot().id());
        Assertions.assertEquals("Hello World!", snapshot.snapshot().data());

        // applies snapshot changes to object
        snapshot.apply(object);

        // verifies that the object number was reverted back to the snapshot
        Assertions.assertEquals(2, object.id());
        Assertions.assertEquals("Hello World!", object.data());
    }

    @Test
    void generalized_snapshot_test() {
        StringHolder object = new StringHolder(2, "Hello World!");
        // clones object, meaning anything after this point should not affect the snapshot
        final ObjectSnapshot<StringHolder> snapshot = new ObjectSnapshot<>(object);

        // snapshot should not be affected by this
        object = new StringHolder(2, "Hello Sun!");
        // verifies that object's number is indeed 3
        Assertions.assertEquals("Hello Sun!", object.data());

        // verifies that snapshot is still intact, despite changes to the object
        Assertions.assertEquals(2, snapshot.snapshot().id());
        Assertions.assertEquals("Hello World!", snapshot.snapshot().data());

        // applies snapshot changes to object
        snapshot.apply(object);

        // verifies that the object data was reverted back to the snapshot
        Assertions.assertEquals(2, object.id());
        Assertions.assertEquals("Hello World!", object.data());
    }
}
