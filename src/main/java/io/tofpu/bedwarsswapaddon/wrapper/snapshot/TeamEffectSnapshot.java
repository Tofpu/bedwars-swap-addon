package io.tofpu.bedwarsswapaddon.wrapper.snapshot;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.arena.team.BedWarsTeam;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public class TeamEffectSnapshot implements Snapshot {
    private final List<PotionEffect> baseEffects;
    private final List<PotionEffect> teamEffects;

    public TeamEffectSnapshot(ITeam team) {
        this.baseEffects = new ArrayList<>(team.getBaseEffects());
        this.teamEffects = new ArrayList<>(((BedWarsTeam) team).getTeamEffects());
    }

    @Override
    public void apply(ITeam team) {
        team.getBaseEffects().clear();
        team.getBaseEffects().addAll(baseEffects);

        ((BedWarsTeam) team).getTeamEffects().clear();
        ((BedWarsTeam) team).getTeamEffects().addAll(teamEffects);
    }
}
