package anonimo.ZonePVP.events;

import anonimo.ZonePVP.utils.ZoneUtils;
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

    private static final String PVP_ENABLED_TITLE = "PvP is enabled";
    private static final String PVP_DISABLED_TITLE = "PvP is disabled";
    private static final String PVP_ENABLED_DESC = "This is a dangerous field. Watch out!";
    private static final String PVP_DISABLED_DESC = "Peaceful grounds again. Welcome to the safe zone.";
    private static final String PVP_ENABLED_ICON = "Weapon_Sword_Adamantite";
    private static final String PVP_DISABLED_ICON = "Weapon_Shield_Orbis_Knight";
    private final Map<UUID, String> playerLastNotification;

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
        String notificationText = isPvPEnabled ? PVP_ENABLED_TITLE : PVP_DISABLED_TITLE;
        Color notificationColor = isPvPEnabled ? Color.RED : Color.GREEN;
        String notificationDesc = isPvPEnabled ? PVP_ENABLED_DESC : PVP_DISABLED_DESC;
        String notificationIcon = isPvPEnabled ? PVP_ENABLED_ICON : PVP_DISABLED_ICON;

        String previousNotification = playerLastNotification.get(playerRef.getUuid());
        if (!notificationText.equals(previousNotification)) {
            var packetHandler = playerRef.getPacketHandler();

            Message primaryMessage = Message.raw(notificationText).color(notificationColor);
            Message secondaryMessage = Message.raw(notificationDesc).color(Color.WHITE);
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
