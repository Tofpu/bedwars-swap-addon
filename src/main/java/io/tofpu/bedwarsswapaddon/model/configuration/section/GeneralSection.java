package io.tofpu.bedwarsswapaddon.model.configuration.section;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class GeneralSection {
    @Setting("enable-debug-mode")
    private boolean debug = false;

    public boolean isDebug() {
        return debug;
    }
}
