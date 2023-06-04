package io.tofpu.bedwarsswapaddon.snapshot;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import io.tofpu.bedwarsswapaddon.wrapper.snapshot.Snapshot;

public class LiveObject {
    private final ITeam object;

    public LiveObject(ITeam object) {
        this.object = object;
    }

    public void use(Snapshot snapshot) {
        snapshot.apply(object);
    }

    public ITeam object() {
        return object;
    }
}
