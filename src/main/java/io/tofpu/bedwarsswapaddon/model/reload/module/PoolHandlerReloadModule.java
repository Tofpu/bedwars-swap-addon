package io.tofpu.bedwarsswapaddon.model.reload.module;

import io.tofpu.bedwarsswapaddon.model.reload.ReloadModule;
import io.tofpu.bedwarsswapaddon.model.swap.pool.SwapPoolHandlerBase;

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
