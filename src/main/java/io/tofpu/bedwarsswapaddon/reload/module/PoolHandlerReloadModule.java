package io.tofpu.bedwarsswapaddon.reload.module;

import io.tofpu.bedwarsswapaddon.reload.ReloadModule;
import io.tofpu.bedwarsswapaddon.swap.game.pool.SwapPoolHandlerBase;

import java.util.concurrent.CompletableFuture;

public class PoolHandlerReloadModule implements ReloadModule {
    private final SwapPoolHandlerBase<?> poolHandler;

    public PoolHandlerReloadModule(final SwapPoolHandlerBase<?> poolHandler) {
        this.poolHandler = poolHandler;
    }

    @Override
    public CompletableFuture<Void> handleAsync() {
        poolHandler.reload();
        return CompletableFuture.completedFuture(null);
    }
}
