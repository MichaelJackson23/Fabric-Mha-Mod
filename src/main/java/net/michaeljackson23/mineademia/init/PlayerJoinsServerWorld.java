package net.michaeljackson23.mineademia.init;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.server.ServerStartCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.michaeljackson23.mineademia.Mineademia;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayerJoinsServerWorld {
    String quirk;

    public PlayerJoinsServerWorld() {
//       ServerLifecycleEvents.SERVER_STARTED.register((server) -> {
//           this.quirk = QuirkPicker();
//           server.getPlayerManager().
//        });
//        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
//            handler.getPlayer().sendMessage(Text.literal("You have joined and this event is working!!"));
//            PlayerData playerState = StateSaverAndLoader.getPlayerState(handler.getPlayer());
//            PacketByteBuf data = PacketByteBufs.create();
//            data.writeInt(playerState.customNumber);
//            server.execute(() -> {
//                ServerPlayNetworking.send(handler.getPlayer(),INITIAL_SYNC, data);
//            });
//        });
    }

    private String QuirkPicker() {
        List<String> quirks = new ArrayList<>();
        quirks.add("ofa");
        quirks.add("hchh");
        quirks.add("explosion");

        int randomInt = 0;

        Random rand = new Random();
        randomInt = rand.nextInt(quirks.size());

        return quirks.get(randomInt);
    }
}
