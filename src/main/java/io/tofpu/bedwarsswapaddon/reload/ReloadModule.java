package io.tofpu.bedwarsswapaddon.reload;

import java.util.concurrent.CompletableFuture;

public interface ReloadModule {
    CompletableFuture<Void> handleAsync();
}
