package io.tofpu.bedwarsswapaddon.wrapper;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import io.tofpu.bedwarsswapaddon.wrapper.snapshot.Snapshot;
import io.tofpu.bedwarsswapaddon.wrapper.snapshot.TeamSnapshot;

public class TeamWrapper implements LiveObject<ITeam> {
    private final TeamSnapshot snapshot;
    private final ITeam team;

    private TeamWrapper(TeamSnapshot snapshot, ITeam team) {
        this.snapshot = snapshot;
        this.team = team;
    }

    public static TeamWrapper of(final TeamSnapshot snapshot, final ITeam team) {
        return new TeamWrapper(snapshot, team);
    }

    @Override
    public void use(Snapshot snapshot) {
        snapshot.to(this.snapshot, object());
    }

    @Override
    public ITeam object() {
        return team;
    }
}
