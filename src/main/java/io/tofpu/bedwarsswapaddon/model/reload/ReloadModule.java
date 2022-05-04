package io.tofpu.bedwarsswapaddon.model.reload;

import java.util.concurrent.CompletableFuture;

public interface ReloadModule {
    CompletableFuture<Void> handleAsync();
}
