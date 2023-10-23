package io.tofpu.bedwarsswapaddon.snapshot.helper;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.arena.team.TeamColor;
import io.tofpu.bedwarsswapaddon.snapshot.util.BedwarsMocker;
import io.tofpu.bedwarsswapaddon.swap.snapshot.team.TeamSnapshot;
import io.tofpu.bedwarsswapaddon.swap.wrapper.LiveObject;
import io.tofpu.bedwarsswapaddon.swap.wrapper.TeamWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BedwarsHelper extends BukkitHelper {
    @NotNull
    protected TeamSnapshot snapshot(ITeam redTeam) {
        return new TeamSnapshot(redTeam);
    }

    protected TeamSnapshot snapshot(TeamColor color, boolean bedDestroyed) {
        return snapshot(BedwarsMocker.bedWarsTeam(color, bedDestroyed));
    }

    protected List<ITeam> teams(ITeam... snapshots) {
        return Arrays.stream(snapshots).collect(Collectors.toList());
    }

    protected List<TeamSnapshot> teams(TeamSnapshot... snapshots) {
        return Arrays.stream(snapshots).collect(Collectors.toList());
    }

    protected LiveObject<ITeam> live(TeamSnapshot team) {
        return TeamWrapper.of(team, team.getLive());
    }
}
