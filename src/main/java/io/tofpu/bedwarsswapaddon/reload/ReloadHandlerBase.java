package io.tofpu.bedwarsswapaddon.reload;

import java.util.List;
import java.util.concurrent.*;

public abstract class ReloadHandlerBase {
    private final ExecutorService service;
    private boolean isReloading;

    protected ReloadHandlerBase() {
        this.service = Executors.newSingleThreadExecutor();
    }

    public CompletableFuture<Void> reload() {
        if (this.isReloading) {
            return CompletableFuture.completedFuture(null);
        }
        this.isReloading = true;

        final CompletableFuture<Void> future = new CompletableFuture<>();
        this.service.submit(() -> {
            for (final ReloadModule module : getReloadModules()) {
                try {
                    module.handleAsync().get(5, TimeUnit.SECONDS);
                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    isReloading = false;
                    throw new IllegalStateException("Failed to reload module " + module.getClass().getSimpleName(), e);
                }
            }
            isReloading = false;
            future.complete(null);
        });
        return future;
    }

    protected boolean isReloading() {
        return isReloading;
    }

    public abstract List<ReloadModule> getReloadModules();
}
