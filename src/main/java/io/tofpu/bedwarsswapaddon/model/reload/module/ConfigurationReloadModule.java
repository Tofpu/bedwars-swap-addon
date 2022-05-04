package io.tofpu.bedwarsswapaddon.model.reload.module;

import io.tofpu.bedwarsswapaddon.model.configuration.handler.ConfigurationHandler;
import io.tofpu.bedwarsswapaddon.model.reload.ReloadModule;

import java.util.concurrent.CompletableFuture;

public class ConfigurationReloadModule implements ReloadModule {
    @Override
    public CompletableFuture<Void> handleAsync() {
        return ConfigurationHandler.get().reload();
    }
}
