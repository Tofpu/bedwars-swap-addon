package io.tofpu.bedwarsswapaddon.snapshot.helper;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import io.tofpu.bedwarsswapaddon.snapshot.LiveObject;
import io.tofpu.bedwarsswapaddon.wrapper.snapshot.TeamSnapshot;
import org.jetbrains.annotations.NotNull;

public class BedwarsHelper extends BukkitHelper {
    @NotNull
    protected TeamSnapshot snapshot(ITeam redTeam) {
        return new TeamSnapshot(redTeam);
    }

    protected LiveObject live(ITeam team) {
        return new LiveObject(team);
    }
}
