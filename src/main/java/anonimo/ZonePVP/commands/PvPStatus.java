package anonimo.ZonePVP.commands;

import anonimo.ZonePVP.utils.ZoneUtils;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
import com.hypixel.hytale.server.core.entity.entities.Player;

import javax.annotation.Nonnull;

public class PvPStatus extends CommandBase {

    public PvPStatus(String name, String description) {
        super(name, description);
    }

    @Override
    protected void executeSync(@Nonnull CommandContext context) {
        Player sender = context.senderAs(Player.class);
        boolean PvPEnabled = ZoneUtils.isPvPEnabled(sender);

        context.sendMessage(Message.raw("PvP is " + (PvPEnabled ? "enabled" : "disabled") + "."));
    }
}
