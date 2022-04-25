package io.tofpu.bedwarsswapaddon.model.swap.pool.task.sub;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import org.bukkit.entity.Player;

public interface SubTask {
    void run(final SubTaskContext context);

    public static class SubTaskContext {
        private final SubTask subTask;
        private final IArena arena;
        private final Player playerOne, playerTwo;
        private final ITeam playerOneTeam, playerTwoTeam;

        public SubTaskContext(final SubTask subTask, final IArena arena, final Player playerOne, final Player playerTwo, final ITeam playerOneTeam, final ITeam playerTwoTeam) {
            this.subTask = subTask;
            this.arena = arena;
            this.playerOne = playerOne;
            this.playerTwo = playerTwo;
            this.playerOneTeam = playerOneTeam;
            this.playerTwoTeam = playerTwoTeam;
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

        public ITeam getPlayerOneTeam() {
            return this.playerOneTeam;
        }

        public ITeam getPlayerTwoTeam() {
            return this.playerTwoTeam;
        }
    }
}
