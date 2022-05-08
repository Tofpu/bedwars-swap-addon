package io.tofpu.bedwarsswapaddon.model.configuration.section;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class GeneralSection {
    @Setting("enable-debug-mode")
    @Comment("By enabling this, you will be able to see the debug messages in the console.")
    private boolean debug = false;

    public boolean isDebug() {
        return debug;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final GeneralSection that = (GeneralSection) o;

        return isDebug() == that.isDebug();
    }

    @Override
    public int hashCode() {
        return (isDebug() ? 1 : 0);
    }
}
