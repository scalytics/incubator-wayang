package org.apache.wayang.spark.plugin;

import org.apache.wayang.core.api.Configuration;
import org.apache.wayang.core.mapping.Mapping;
import org.apache.wayang.core.optimizer.channels.ChannelConversion;
import org.apache.wayang.core.platform.Platform;
import org.apache.wayang.core.plugin.Plugin;
import org.apache.wayang.spark.mapping.Mappings;
import org.apache.wayang.spark.platform.SparkPlatform;

import java.util.Collection;
import java.util.Collections;

public class SparkMLPlugin implements Plugin {

    @Override
    public Collection<Mapping> getMappings() {
        return Mappings.ML_MAPPINGS;
    }

    @Override
    public Collection<ChannelConversion> getChannelConversions() {
        return Collections.emptyList();
    }

    @Override
    public Collection<Platform> getRequiredPlatforms() {
        return Collections.singletonList(SparkPlatform.getInstance());
    }

    @Override
    public void setProperties(Configuration configuration) {
        // Nothing to do, because we already configured the properties in #configureDefaults(...).
    }
}
