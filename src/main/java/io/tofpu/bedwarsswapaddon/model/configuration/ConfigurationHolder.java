package io.tofpu.bedwarsswapaddon.model.configuration;

import io.tofpu.bedwarsswapaddon.model.configuration.section.GeneralSection;
import io.tofpu.bedwarsswapaddon.model.configuration.section.SwapAnnounceSection;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class ConfigurationHolder {
    @Setting("general-settings")
    private final GeneralSection general;

    @Setting("swap-announce-settings")
    private final SwapAnnounceSection announce;

    public ConfigurationHolder() {
        this(new GeneralSection(), new SwapAnnounceSection());
    }

    public ConfigurationHolder(final GeneralSection general, final SwapAnnounceSection announce) {
        this.general = general;
        this.announce = announce;
    }

    public ConfigurationHolder(final ConfigurationHolder copy) {
        this(copy.general, copy.announce);
    }

    public boolean isDebug() {
        return general.isDebug();
    }

    public int getMaximumInterval() {
        return announce.getMaximumInterval();
    }

    public int getMinimumInterval() {
        return announce.getMinimumInterval();
    }

    public ConfigurationHolder copy() {
        return new ConfigurationHolder(this);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ConfigurationHolder that = (ConfigurationHolder) o;

        return new EqualsBuilder().append(general, that.general)
                .append(announce, that.announce)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(general)
                .append(announce)
                .toHashCode();
    }
}
