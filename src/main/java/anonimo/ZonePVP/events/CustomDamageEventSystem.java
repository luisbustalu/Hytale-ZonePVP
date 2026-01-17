package anonimo.ZonePVP.events;

import anonimo.ZonePVP.utils.ZoneUtils;
import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.dependency.Dependency;
import com.hypixel.hytale.component.dependency.RootDependency;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
import com.hypixel.hytale.server.core.modules.entity.damage.DamageEventSystem;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Set;

public class CustomDamageEventSystem extends DamageEventSystem {

    @Override
    public void handle(int index, @NonNullDecl ArchetypeChunk<EntityStore> archetypeChunk, @NonNullDecl Store<EntityStore> store, @NonNullDecl CommandBuffer<EntityStore> commandBuffer, @NonNullDecl Damage damage) {
        if(damage.isCancelled()) {
            return;
        }

        // We get the reference id of the victim and try to get the Player component for it
        Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
        Player victim = store.getComponent(ref, Player.getComponentType());

        // We detect if it's not a player
        if(victim == null) {
            return;
        }

        // We get the reference id of the attacker and try to get the Player component for it
        if (damage.getSource() instanceof Damage.EntitySource damageEntitySource) {
            Ref<EntityStore> attackerRef = damageEntitySource.getRef();

            if (!attackerRef.isValid()) {
                return;
            }

            Player attackerPlayerComponent = commandBuffer.getComponent(attackerRef, Player.getComponentType());

            // We detect if it's not a player
            if(attackerPlayerComponent == null) {
                return;
            }

            // If code reaches this point and PvP is disabled based in the region, we cancel the damage.
            if(!ZoneUtils.isPvPEnabled(victim)) {
                damage.setCancelled(true);
            }
        }
    }

    @Nullable
    @Override
    public Query<EntityStore> getQuery() {
        return PlayerRef.getComponentType();
    }

    @NonNullDecl
    @Override
    public Set<Dependency<EntityStore>> getDependencies() {
        return Collections.singleton(RootDependency.first());
    }
}