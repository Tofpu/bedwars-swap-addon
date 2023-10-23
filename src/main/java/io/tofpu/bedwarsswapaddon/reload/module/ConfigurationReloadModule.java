package io.tofpu.bedwarsswapaddon.reload.module;

import io.tofpu.bedwarsswapaddon.configuration.handler.ConfigurationHandler;
import io.tofpu.bedwarsswapaddon.reload.ReloadModule;

import java.util.concurrent.CompletableFuture;

public class ConfigurationReloadModule implements ReloadModule {
    @Override
    public CompletableFuture<Void> handleAsync() {
        return ConfigurationHandler.get().reload();
    }
}
