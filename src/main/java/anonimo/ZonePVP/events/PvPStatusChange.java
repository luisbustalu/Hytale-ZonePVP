package anonimo.ZonePVP.events;

import anonimo.ZonePVP.Main;
import anonimo.ZonePVP.utils.ZoneUtils;
import anonimo.ZonePVP.utils.PluginMessages;
import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.NotificationUtil;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.awt.*;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PvPStatusChange extends EntityTickingSystem<EntityStore> {
    Message PVP_ENABLED_TITLE = PluginMessages.PVP_ENABLED_TITLE;
    Message PVP_DISABLED_TITLE = PluginMessages.PVP_DISABLED_TITLE;
    Message PVP_ENABLED_DESC = PluginMessages.PVP_ENABLED_DESC;
    Message PVP_DISABLED_DESC = PluginMessages.PVP_DISABLED_DESC;
    private static final String PVP_ENABLED_ICON = Main.CONFIG.get().getPvPEnabledIcon();
    private static final String PVP_DISABLED_ICON = Main.CONFIG.get().getPvPDisabledIcon();

    private final Map<UUID, Message> playerLastNotification;

    public PvPStatusChange() {
        this.playerLastNotification = new ConcurrentHashMap<>();
    }

    @Override
    public void tick(float v, int index, @NonNullDecl ArchetypeChunk<EntityStore> archetypeChunk, @NonNullDecl Store<EntityStore> store, @NonNullDecl CommandBuffer<EntityStore> commandBuffer) {
        Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
        PlayerRef playerRef = store.getComponent(ref, PlayerRef.getComponentType());
        Player player = store.getComponent(ref, Player.getComponentType());

        if (playerRef == null|| player == null) {
            return;
        }

        if (player.isWaitingForClientReady()) {
            return;
        }

        boolean isPvPEnabled = ZoneUtils.isPvPEnabled(player);
        Message notificationText = isPvPEnabled ? PVP_ENABLED_TITLE : PVP_DISABLED_TITLE;
        Color notificationColor = isPvPEnabled ? Color.RED : Color.GREEN;
        Message notificationDesc = isPvPEnabled ? PVP_ENABLED_DESC : PVP_DISABLED_DESC;
        String notificationIcon = isPvPEnabled ? PVP_ENABLED_ICON : PVP_DISABLED_ICON;

        Message previousNotification = playerLastNotification.get(playerRef.getUuid());
        if (!(notificationText == previousNotification)) {
            var packetHandler = playerRef.getPacketHandler();

            Message primaryMessage = notificationText.color(notificationColor);
            Message secondaryMessage = notificationDesc.color(Color.WHITE);
            var icon = new ItemStack(notificationIcon, 1).toPacket();

            NotificationUtil.sendNotification(
                    packetHandler,
                    primaryMessage,
                    secondaryMessage,
                    icon);

            playerLastNotification.put(playerRef.getUuid(), notificationText);
        }
    }

    @NullableDecl
    @Override
    public Query<EntityStore> getQuery() {
        return PlayerRef.getComponentType();
    }
}
