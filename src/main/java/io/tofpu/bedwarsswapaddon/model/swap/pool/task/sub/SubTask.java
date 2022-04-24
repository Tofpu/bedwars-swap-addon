package io.tofpu.bedwarsswapaddon.model.swap.pool.task.sub;

import com.andrei1058.bedwars.api.arena.IArena;
import org.bukkit.entity.Player;

public interface SubTask {
    void run(final SubTaskContext subTaskContext);

    public static class SubTaskContext {
        private final SubTask subTask;
        private final IArena arena;
        private final Player playerOne, playerTwo;

        public SubTaskContext(final SubTask subTask, final IArena arena, final Player playerOne, final Player playerTwo) {
            this.subTask = subTask;
            this.arena = arena;
            this.playerOne = playerOne;
            this.playerTwo = playerTwo;
        }

        public SubTask getSubTask() {
            return this.subTask;
        }

        public IArena getArena() {
            return this.arena;
        }

        public Player getPlayerOne() {
            return this.playerOne;
        }

        public Player getPlayerTwo() {
            return this.playerTwo;
        }
    }
}
