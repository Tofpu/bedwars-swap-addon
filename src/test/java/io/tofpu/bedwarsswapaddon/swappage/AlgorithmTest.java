package io.tofpu.bedwarsswapaddon.swappage;

import com.andrei1058.bedwars.api.arena.team.TeamColor;
import io.tofpu.bedwarsswapaddon.snapshot.helper.BedwarsHelper;
import io.tofpu.bedwarsswapaddon.swap.game.pool.task.SwappageAlgorithm;
import io.tofpu.bedwarsswapaddon.swap.snapshot.team.TeamSnapshot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class AlgorithmTest extends BedwarsHelper {
    private final SwappageAlgorithm algorithm = new SwappageAlgorithm();

    @Test
    void two_teams_with_beds() {
        List<TeamSnapshot> list = teams(
                snapshot(TeamColor.RED, false),
                snapshot(TeamColor.BLUE, false)
        );

        List<Map.Entry<TeamSnapshot, TeamSnapshot>> result = algorithm.find(list);

        Assertions.assertEquals(2, result.size());

        Map.Entry<TeamSnapshot, TeamSnapshot> first = result.get(0);
        assertThat(TeamColor.RED, first.getKey());
        assertThat(TeamColor.BLUE, first.getValue());

        Map.Entry<TeamSnapshot, TeamSnapshot> second = result.get(1);
        assertThat(TeamColor.BLUE, second.getKey());
        assertThat(TeamColor.RED, second.getValue());
    }

    @Test
    void two_teams_without_beds() {
        List<TeamSnapshot> list = teams(
                snapshot(TeamColor.RED, true),
                snapshot(TeamColor.BLUE, true)
        );

        List<Map.Entry<TeamSnapshot, TeamSnapshot>> result = algorithm.find(list);

        Assertions.assertEquals(2, result.size());

        Map.Entry<TeamSnapshot, TeamSnapshot> first = result.get(0);
        assertThat(TeamColor.RED, first.getKey());
        assertThat(TeamColor.BLUE, first.getValue());

        Map.Entry<TeamSnapshot, TeamSnapshot> second = result.get(1);
        assertThat(TeamColor.BLUE, second.getKey());
        assertThat(TeamColor.RED, second.getValue());
    }

    @Test
    void two_teams_with_one_bed() {
        List<TeamSnapshot> list = teams(
                snapshot(TeamColor.RED, false),
                snapshot(TeamColor.BLUE, true)
        );

        List<Map.Entry<TeamSnapshot, TeamSnapshot>> result = algorithm.find(list);

        Assertions.assertEquals(0, result.size());
    }

    @Test
    void three_teams_with_beds() {
        List<TeamSnapshot> list = teams(
                snapshot(TeamColor.RED, false),
                snapshot(TeamColor.BLUE, false),
                snapshot(TeamColor.GREEN, false)
        );

        List<Map.Entry<TeamSnapshot, TeamSnapshot>> result = algorithm.find(list);

        Assertions.assertEquals(3, result.size());

        Map.Entry<TeamSnapshot, TeamSnapshot> first = result.get(0);
        assertThat(TeamColor.RED, first.getKey());
        assertThat(TeamColor.BLUE, first.getValue());

        Map.Entry<TeamSnapshot, TeamSnapshot> second = result.get(1);
        assertThat(TeamColor.BLUE, second.getKey());
        assertThat(TeamColor.GREEN, second.getValue());

        Map.Entry<TeamSnapshot, TeamSnapshot> third = result.get(2);
        assertThat(TeamColor.GREEN, third.getKey());
        assertThat(TeamColor.RED, third.getValue());
    }

    @Test
    void three_teams_with_first_two_beds() {
        List<TeamSnapshot> list = teams(
                snapshot(TeamColor.RED, false),
                snapshot(TeamColor.BLUE, false),
                snapshot(TeamColor.GREEN, true)
        );

        List<Map.Entry<TeamSnapshot, TeamSnapshot>> result = algorithm.find(list);

        Assertions.assertEquals(2, result.size());

        Map.Entry<TeamSnapshot, TeamSnapshot> first = result.get(0);
        assertThat(TeamColor.RED, first.getKey());
        assertThat(TeamColor.BLUE, first.getValue());

        Map.Entry<TeamSnapshot, TeamSnapshot> second = result.get(1);
        assertThat(TeamColor.BLUE, second.getKey());
        assertThat(TeamColor.RED, second.getValue());
    }

    @Test
    void three_teams_with_first_broken_bed_and_others_intact() {
        List<TeamSnapshot> list = teams(
                snapshot(TeamColor.RED, true),
                snapshot(TeamColor.BLUE, false),
                snapshot(TeamColor.GREEN, false)
        );

        List<Map.Entry<TeamSnapshot, TeamSnapshot>> result = algorithm.find(list);

        Assertions.assertEquals(2, result.size());

        Map.Entry<TeamSnapshot, TeamSnapshot> first = result.get(0);
        assertThat(TeamColor.BLUE, first.getKey());
        assertThat(TeamColor.GREEN, first.getValue());

        Map.Entry<TeamSnapshot, TeamSnapshot> second = result.get(1);
        assertThat(TeamColor.GREEN, second.getKey());
        assertThat(TeamColor.BLUE, second.getValue());
    }

    @Test
    void three_teams_with_second_broken_bed_and_others_intact() {
        List<TeamSnapshot> list = teams(
                snapshot(TeamColor.RED, false),
                snapshot(TeamColor.BLUE, true),
                snapshot(TeamColor.GREEN, false)
        );

        List<Map.Entry<TeamSnapshot, TeamSnapshot>> result = algorithm.find(list);

        Assertions.assertEquals(2, result.size());

        Map.Entry<TeamSnapshot, TeamSnapshot> first = result.get(0);
        assertThat(TeamColor.RED, first.getKey());
        assertThat(TeamColor.GREEN, first.getValue());

        Map.Entry<TeamSnapshot, TeamSnapshot> second = result.get(1);
        assertThat(TeamColor.GREEN, second.getKey());
        assertThat(TeamColor.RED, second.getValue());
    }

    @Test
    void three_teams_with_third_broken_bed_and_others_intact() {
        List<TeamSnapshot> list = teams(
                snapshot(TeamColor.RED, false),
                snapshot(TeamColor.BLUE, false),
                snapshot(TeamColor.GREEN, true)
        );

        List<Map.Entry<TeamSnapshot, TeamSnapshot>> result = algorithm.find(list);

        Assertions.assertEquals(2, result.size());

        Map.Entry<TeamSnapshot, TeamSnapshot> first = result.get(0);
        assertThat(TeamColor.RED, first.getKey());
        assertThat(TeamColor.BLUE, first.getValue());

        Map.Entry<TeamSnapshot, TeamSnapshot> second = result.get(1);
        assertThat(TeamColor.BLUE, second.getKey());
        assertThat(TeamColor.RED, second.getValue());
    }

    @Test
    void four_teams_with_two_broken_beds_and_two_intact_beds() {
        List<TeamSnapshot> list = teams(
                snapshot(TeamColor.RED, false),
                snapshot(TeamColor.BLUE, false),
                snapshot(TeamColor.GREEN, true),
                snapshot(TeamColor.YELLOW, true)
        );

        List<Map.Entry<TeamSnapshot, TeamSnapshot>> result = algorithm.find(list);

        Assertions.assertEquals(4, result.size());

        Map.Entry<TeamSnapshot, TeamSnapshot> first = result.get(0);
        assertThat(TeamColor.RED, first.getKey());
        assertThat(TeamColor.BLUE, first.getValue());

        Map.Entry<TeamSnapshot, TeamSnapshot> second = result.get(1);
        assertThat(TeamColor.BLUE, second.getKey());
        assertThat(TeamColor.RED, second.getValue());

        Map.Entry<TeamSnapshot, TeamSnapshot> third = result.get(2);
        assertThat(TeamColor.GREEN, third.getKey());
        assertThat(TeamColor.YELLOW, third.getValue());

        Map.Entry<TeamSnapshot, TeamSnapshot> fourth = result.get(3);
        assertThat(TeamColor.YELLOW, fourth.getKey());
        assertThat(TeamColor.GREEN, fourth.getValue());
    }

    @Test
    void four_teams_with_odd_broken_beds_and_even_intact_bed() {
        List<TeamSnapshot> list = teams(
                snapshot(TeamColor.RED, true),
                snapshot(TeamColor.BLUE, false),
                snapshot(TeamColor.GREEN, true),
                snapshot(TeamColor.YELLOW, false)
        );

        List<Map.Entry<TeamSnapshot, TeamSnapshot>> result = algorithm.find(list);

        Assertions.assertEquals(4, result.size());

        Map.Entry<TeamSnapshot, TeamSnapshot> first = result.get(0);
        assertThat(TeamColor.RED, first.getKey());
        assertThat(TeamColor.GREEN, first.getValue());

        Map.Entry<TeamSnapshot, TeamSnapshot> third = result.get(1);
        assertThat(TeamColor.BLUE, third.getKey());
        assertThat(TeamColor.YELLOW, third.getValue());

        Map.Entry<TeamSnapshot, TeamSnapshot> second = result.get(2);
        assertThat(TeamColor.GREEN, second.getKey());
        assertThat(TeamColor.RED, second.getValue());

        Map.Entry<TeamSnapshot, TeamSnapshot> fourth = result.get(3);
        assertThat(TeamColor.YELLOW, fourth.getKey());
        assertThat(TeamColor.BLUE, fourth.getValue());
    }

    private void assertThat(TeamColor teamColor, TeamSnapshot value) {
        Assertions.assertEquals(teamColor, value.getColor());
    }
}
