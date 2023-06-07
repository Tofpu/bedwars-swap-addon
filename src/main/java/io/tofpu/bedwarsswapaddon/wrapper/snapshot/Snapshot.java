package io.tofpu.bedwarsswapaddon.wrapper.snapshot;

import com.andrei1058.bedwars.api.arena.team.ITeam;

public interface Snapshot {
    void to(TeamSnapshot snapshot, ITeam team);
}
