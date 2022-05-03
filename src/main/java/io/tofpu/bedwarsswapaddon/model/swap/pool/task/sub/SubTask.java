package io.tofpu.bedwarsswapaddon.model.swap.pool.task.sub;

import com.andrei1058.bedwars.api.arena.IArena;
import io.tofpu.bedwarsswapaddon.model.wrapper.TeamSnapshot;

public interface SubTask {
    void run(final SubTaskContext context);

    public static class SubTaskContext {
        private final SubTask subTask;
        private final IArena arena;
        private final TeamSnapshot currentTeam, toTeam;

        public SubTaskContext(final SubTask subTask, final IArena arena, final TeamSnapshot currentTeam, final TeamSnapshot to) {
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

        public TeamSnapshot getCurrentTeam() {
            return this.currentTeam;
        }

        public TeamSnapshot getToTeam() {
            return this.toTeam;
        }
    }
}
