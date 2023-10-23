package io.tofpu.bedwarsswapaddon.swap.snapshot;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import io.tofpu.bedwarsswapaddon.swap.snapshot.team.TeamSnapshot;

public interface Snapshot {
    void to(TeamSnapshot snapshot, ITeam team);
}
