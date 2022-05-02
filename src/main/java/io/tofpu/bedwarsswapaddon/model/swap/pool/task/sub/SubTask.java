package io.tofpu.bedwarsswapaddon.model.swap.pool.task.sub;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import io.tofpu.bedwarsswapaddon.model.wrapper.TeamWrapper;
import org.bukkit.entity.Player;

public interface SubTask {
    void run(final SubTaskContext context);

    public static class SubTaskContext {
        private final SubTask subTask;
        private final IArena arena;
        private final TeamWrapper currentTeam, toTeam;

        public SubTaskContext(final SubTask subTask, final IArena arena, final TeamWrapper currentTeam, final TeamWrapper to) {
            this.subTask = subTask;
            this.arena = arena;
            this.currentTeam = currentTeam;
            this.toTeam = to;
        }

        public SubTask getSubTask() {
            return this.subTask;
        }

        public IArena getArena() {
            return this.arena;
        }

        public TeamWrapper getCurrentTeam() {
            return this.currentTeam;
        }

        public TeamWrapper getToTeam() {
            return this.toTeam;
        }
    }
}
