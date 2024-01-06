package net.michaeljackson23.mineademia.test;

import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.michaeljackson23.mineademia.util.PlayerAngleVector;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Vec3d;


public class KillOnUse {
    public Integer mana = 500;
    public KillOnUse() {
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            Vec3d lookVec = player.getRotationVec(1.0f);

            if(player.getActiveItem().equals(ItemStack.EMPTY)) {
//                player.addVelocity(player.getRotationVector());
                player.addVelocity(PlayerAngleVector.getPlayerAngleVector(player, -1, -1, -1));
                if(!world.isClient) {
                    EntityType.LIGHTNING_BOLT.spawn(((ServerWorld) player.getWorld()),
                            entity.getBlockPos(), SpawnReason.TRIGGERED);
                    entity.teleport(entity.getX(), entity.getY() + 1, entity.getZ());
                }
                world.addParticle(ParticleTypes.CRIT, entity.getX(), entity.getY(), entity.getZ(), 0, 0, 0);
                entity.kill();
                return ActionResult.SUCCESS;
            } else {
                return ActionResult.PASS;
            }
        });
    }
}
