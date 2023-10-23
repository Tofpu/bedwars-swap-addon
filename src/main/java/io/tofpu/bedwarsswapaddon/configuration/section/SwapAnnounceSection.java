package io.tofpu.bedwarsswapaddon.configuration.section;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class SwapAnnounceSection {
    @Setting("minimum-swap-interval")
    @Comment("The minimum interval between two swaps in seconds.")
    private int minSwapInterval = 5;

    @Setting("maximum-swap-interval")
    @Comment("The maximum interval between two swaps in seconds.")
    private int maxSwapInterval = 10;

    public int getMaximumInterval() {
        return maxSwapInterval;
    }

    public int getMinimumInterval() {
        return minSwapInterval;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final SwapAnnounceSection that = (SwapAnnounceSection) o;

        if (minSwapInterval != that.minSwapInterval) {
            return false;
        }
        return maxSwapInterval == that.maxSwapInterval;
    }

    @Override
    public int hashCode() {
        int result = minSwapInterval;
        result = 31 * result + maxSwapInterval;
        return result;
    }
}
