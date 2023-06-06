package io.tofpu.bedwarsswapaddon.model.swap.pool.task;

import io.tofpu.bedwarsswapaddon.wrapper.snapshot.Snapshot;
import io.tofpu.bedwarsswapaddon.wrapper.snapshot.TeamSnapshot;

import java.util.*;

// todo: 1 broken test; and it's not as simple as this; will need to recursively loop it or smth
public class SwappageAlgorithm {
    public List<Map.Entry<TeamSnapshot, TeamSnapshot>> find(final List<TeamSnapshot> list) {
        return this.find(list, new ArrayList<>());
    }

    private List<Map.Entry<TeamSnapshot, TeamSnapshot>> find(final List<TeamSnapshot> list, final List<Integer> skipNextList) {
        final List<Map.Entry<TeamSnapshot, TeamSnapshot>> entries = new ArrayList<>();

        if (list.size() == 1) {
            return entries;
        }

        for (int i = 0; i < list.size(); i++) {
            TeamSnapshot current = list.get(i);
            TeamSnapshot next;

            int offset = i + 1;
            next = next(list, skipNextList, offset, current);

            if (next == null) {
                int beginningOffset = 0;
                next = next(list, skipNextList, beginningOffset, current);
            }

            if (next == null) {
                System.out.println("Unable to find compatible team for " + current.getName() + "; skipping!");
                continue;
            }

            entries.add(new AbstractMap.SimpleEntry<>(current, next));
        }
        return entries;
    }

    private TeamSnapshot next(List<TeamSnapshot> list, List<Integer> skipNextList, int offset, TeamSnapshot current) {
        for (int j = offset; j < list.size(); j++) {
            if (skipNextList.contains(j)) continue;
            TeamSnapshot attempt = list.get(j);
            if (isCompatible(current, attempt)) {
                skipNextList.add(j);
                return attempt;
            }
        }
        return null;
    }

    private boolean isCompatible(TeamSnapshot current, TeamSnapshot attempt) {
        return !current.getColor().equals(attempt.getColor()) && current.isBedDestroyed() == attempt.isBedDestroyed();
    }
}
