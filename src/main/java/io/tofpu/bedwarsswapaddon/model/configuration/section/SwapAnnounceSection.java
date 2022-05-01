package io.tofpu.bedwarsswapaddon.model.configuration.section;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class SwapAnnounceSection {
    @Setting("swap-interval")
    @Comment("The interval in seconds between swaps")
    private int swapInterval = 5;

    public int getSwapInterval() {
        return swapInterval;
    }
}
