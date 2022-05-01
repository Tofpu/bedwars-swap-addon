package io.tofpu.bedwarsswapaddon.model.configuration;

import io.tofpu.bedwarsswapaddon.model.configuration.section.GeneralSection;
import io.tofpu.bedwarsswapaddon.model.configuration.section.SwapAnnounceSection;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class ConfigurationHolder {
    @Setting("general-settings")
    private final GeneralSection general = new GeneralSection();

    @Setting("swap-announce-settings")
    private final SwapAnnounceSection announce = new SwapAnnounceSection();

    public boolean isDebug() {
        return general.isDebug();
    }

    public int getMaximumInterval() {
        return announce.getMaximumInterval() * 20;
    }

    public int getMinimumInterval() {
        return announce.getMinimumInterval() * 20;
    }
}
