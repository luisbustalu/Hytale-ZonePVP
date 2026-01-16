package anonimo.ZonePVP;

import anonimo.ZonePVP.commands.PvPStatus;
import anonimo.ZonePVP.config.ZonePVPConfig;
import anonimo.ZonePVP.events.PvPStatusChange;
import anonimo.ZonePVP.events.CustomDamageEventSystem;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.util.Config;

import javax.annotation.Nonnull;

public class Main extends JavaPlugin {

    public static Config<ZonePVPConfig> CONFIG;

    public Main(@Nonnull JavaPluginInit init) {
        super(init);
        CONFIG = this.withConfig("ZonePVP", ZonePVPConfig.CODEC);
    }

    @Override
    protected void setup() {
        super.setup();
        CONFIG.save();

        // General Commands
        this.getCommandRegistry().registerCommand(new PvPStatus("pvpstatus", "Returns if you're in a PvP zone or not."));

        // PvP System
        this.getEntityStoreRegistry().registerSystem(new CustomDamageEventSystem());
        this.getEntityStoreRegistry().registerSystem(new PvPStatusChange());
    }
}