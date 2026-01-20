package anonimo.ZonePVP.config;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;

public class ZonePVPConfig {

    public static final BuilderCodec<ZonePVPConfig> CODEC = BuilderCodec.builder(ZonePVPConfig.class, ZonePVPConfig::new)
            .append(new KeyedCodec<Integer>("MinZone", Codec.INTEGER),
                    (ZonePVPConfig, integer, extraInfo) -> ZonePVPConfig.MinPVPZone = integer,
                    (ZonePVPConfig, extraInfo) -> ZonePVPConfig.MinPVPZone).add()
            .append(new KeyedCodec<String>("PvPEnabledIcon", Codec.STRING),
                    (ZonePVPConfig, String, extraInfo) -> ZonePVPConfig.PvPEnabledIcon = String,
                    (ZonePVPConfig, extraInfo) -> ZonePVPConfig.PvPEnabledIcon).add()
            .append(new KeyedCodec<String>("PvPDisabledIcon", Codec.STRING),
                    (ZonePVPConfig, String, extraInfo) -> ZonePVPConfig.PvPDisabledIcon = String,
                    (ZonePVPConfig, extraInfo) -> ZonePVPConfig.PvPDisabledIcon).add()
            .build();

    private int MinPVPZone = 2;
    private String PvPEnabledIcon = "Weapon_Sword_Adamantite";
    private String PvPDisabledIcon = "Weapon_Shield_Orbis_Knight";

    public ZonePVPConfig() {

    }

    public int getMinPVPZone() {
        return MinPVPZone;
    }
    public String getPvPEnabledIcon() { return PvPEnabledIcon; }
    public String getPvPDisabledIcon() { return PvPDisabledIcon; }
}
