package io.tofpu.bedwarsswapaddon.model.reload;

import io.tofpu.bedwarsswapaddon.model.reload.module.ConfigurationReloadModule;
import io.tofpu.bedwarsswapaddon.model.reload.module.PoolHandlerReloadModule;
import io.tofpu.bedwarsswapaddon.model.swap.pool.SwapPoolHandlerBase;
import revxrsal.commands.util.Collections;

import java.util.List;

public class MainReloadHandler extends ReloadHandlerBase {
    private final SwapPoolHandlerBase<?> poolHandler;

    public MainReloadHandler(final SwapPoolHandlerBase<?> poolHandler) {
        this.poolHandler = poolHandler;
    }

    @Override
    public List<ReloadModule> getReloadModules() {
        return Collections.listOf(new PoolHandlerReloadModule(poolHandler),
                new ConfigurationReloadModule());
    }
}
