package anonimo.ZonePVP.config;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;

public class ZonePVPConfig {

    public static final BuilderCodec<ZonePVPConfig> CODEC = BuilderCodec.builder(ZonePVPConfig.class, ZonePVPConfig::new)
            .append(new KeyedCodec<Integer>("MinZone", Codec.INTEGER),
                    (ZonePVPConfig, integer, extraInfo) -> ZonePVPConfig.MinPVPZone = integer,
                    (ZonePVPConfig, extraInfo) -> ZonePVPConfig.MinPVPZone).add()
            .build();

    private int MinPVPZone = 2;

    public ZonePVPConfig() {

    }

    public int getMinPVPZone() {
        return MinPVPZone;
    }
}
