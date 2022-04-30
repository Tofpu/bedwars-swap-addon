package io.tofpu.bedwarsswapaddon.model.configuration;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class ConfigurationHolder {
    @Setting("enable-debug-mode")
    private boolean debug = false;

    @Setting("swap-interval")
    @Comment("The interval in seconds between swaps")
    private int swapInterval = 5;

    public boolean isDebug() {
        return debug;
    }

    public int getSwapInterval() {
        return swapInterval * 20;
    }
}
