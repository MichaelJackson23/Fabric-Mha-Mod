package net.michaeljackson23.mineademia.test;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.michaeljackson23.mineademia.Mineademia;
import net.michaeljackson23.mineademia.init.PlayerData;
import net.michaeljackson23.mineademia.init.StateSaverAndLoader;
import net.minecraft.block.Blocks;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import static net.michaeljackson23.mineademia.Mineademia.INITIAL_SYNC;

public class DirtTracker {
    public static final Identifier DIRT_BROKEN = new Identifier(Mineademia.Mod_id, "dirt_broken");
    public Integer totalDirtBlocksBroken = 0;
    public static PlayerData playerData = new PlayerData();

    public DirtTracker() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            handler.getPlayer().sendMessage(Text.literal("You have joined and this event is working!!"));
            PlayerData playerState = StateSaverAndLoader.getPlayerState(handler.getPlayer());
            PacketByteBuf data = PacketByteBufs.create();
            data.writeInt(playerState.customNumber);
            server.execute(() -> {
                ServerPlayNetworking.send(handler.getPlayer(),INITIAL_SYNC, data);
            });
        });

        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, entity) -> {
            if (state.getBlock() == Blocks.GRASS_BLOCK || state.getBlock() == Blocks.DIRT) {
                StateSaverAndLoader serverState = StateSaverAndLoader.getServerState(world.getServer());
                serverState.totalDirtBroken++;
                //This gets the players nbt and adds to it
                PlayerData playerState = StateSaverAndLoader.getPlayerState(player);
                playerState.customNumber++;

                MinecraftServer server = world.getServer();

                PacketByteBuf data = PacketByteBufs.create();
                data.writeInt(serverState.totalDirtBroken);
                data.writeInt(playerState.customNumber);

                ServerPlayerEntity playerEntity = server.getPlayerManager().getPlayer(player.getUuid());
                server.execute(() -> {
                    ServerPlayNetworking.send(playerEntity, DIRT_BROKEN, data);
                });
            } else {
                world.createExplosion(player, pos.getX(), pos.getY(), pos.getZ(), 2.0f, World.ExplosionSourceType.TNT);
            }
        });
    }

    public static void DirtTrackerClient() {
        ClientPlayNetworking.registerGlobalReceiver(DIRT_BROKEN, (client, handler, buf, responseSender) -> {
            int totalDirtBlocksBroken = buf.readInt();
            playerData.customNumber = buf.readInt();

            client.execute(() -> {
                client.player.sendMessage(Text.literal("Total dirt blocks broken: "+totalDirtBlocksBroken));
                client.player.sendMessage(Text.literal("Total dirt blocks you have broken: "+ playerData.customNumber));
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(INITIAL_SYNC, (client, handler, buf, responseSender) -> {
            playerData.customNumber = buf.readInt();

            client.execute(() -> {
                client.player.sendMessage(Text.literal("Initial specific dirt blocks broken: " + playerData.customNumber));
            });
        });

    }
}
