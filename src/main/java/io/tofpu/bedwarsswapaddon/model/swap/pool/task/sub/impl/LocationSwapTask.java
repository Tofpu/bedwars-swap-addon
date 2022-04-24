package io.tofpu.bedwarsswapaddon.model.swap.pool.task.sub.impl;

import io.tofpu.bedwarsswapaddon.model.swap.pool.task.sub.SubTask;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class LocationSwapTask implements SubTask {
    @Override
    public void run(final SubTaskContext context) {
        final Player playerOne = context.getPlayerOne();
        final Player playerTwo = context.getPlayerTwo();

        final Location playerOneLocation = playerOne.getLocation().clone();

        playerOne.teleport(playerTwo.getLocation());
        playerTwo.teleport(playerOneLocation);
    }
}
