package net.michaeljackson23.mineademia.init;

import net.michaeljackson23.mineademia.Mineademia;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.UUID;

public class StateSaverAndLoader extends PersistentState {
    //This is creating a map for a player and it's using the data from the class I already created
    //Theoretically I could replace playerdata with different data then? like I could put an integer there?
    public HashMap<UUID, PlayerData> players = new HashMap<>();

    //Ill keep track of dirt broken to understand the documentation
    public Integer totalDirtBroken = 0;

    //Writes the nbt, but its not going to be saved
    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        //Not sure what this does, my guess is Im registering the data into the player here with
        //an initial value of the variable given, aka I can use /data to find it
        nbt.putInt("totalDirtBroken", totalDirtBroken);



        //Initializing Variable to use it's methods
        NbtCompound playersNbt = new NbtCompound();
        players.forEach((uuid, playerData) -> {
            //This variable is created without an "s" perhaps its for the speficic player targeted now
            NbtCompound playerNbt = new NbtCompound();

            //Grabbing the custom number and appending it to the player
            playerNbt.putInt("customNumber", playerData.customNumber);
            playerData.customIntArray.add(1);
            playerData.customIntArray.add(2);
            playerData.customIntArray.add(3);
            playerNbt.putIntArray("customIntArray", playerData.customIntArray);
            //Adding the hashmap is a bit more complicated, won't do here

            playerNbt.putString("quirk", playerData.playerQuirk);

            //Notice this playersNbt has the "s" in it, this isnt the same one I've been using
            playersNbt.put(uuid.toString(), playerNbt);
        });
        //The variable here has an s as well
        nbt.put("players", playersNbt);

        return nbt;
    }

    public static StateSaverAndLoader createFromNbt(NbtCompound tag) {
        StateSaverAndLoader state = new StateSaverAndLoader();
        //This is saving the variable totalDirtBroken to the custom nbt customNumber
        state.totalDirtBroken = tag.getInt("customNumber");

        //I think we are grabbing the information from line 42, using mapping and the key is "players"
        NbtCompound playersNbt = tag.getCompound("players");
        playersNbt.getKeys().forEach(key -> {
            PlayerData playerData = new PlayerData();

            //I don't really know whats going on here, we are setting the varaible we created to the nbt from the player
            playerData.customNumber = playersNbt.getCompound(key).getInt("customNumber");

            //enhanced for loop for nbt
            for(int customIntArray : playersNbt.getCompound(key).getIntArray("customIntArray")) {
                playerData.customIntArray.add(customIntArray);
            }

            //This finds the player to add the nbt to
            UUID uuid = UUID.fromString(key);
            state.players.put(uuid, playerData);
        });
        return state;
    }

    private static Type<StateSaverAndLoader> type = new Type<>(
            StateSaverAndLoader::new, // If there's no 'StateSaverAndLoader' yet create one
            StateSaverAndLoader::createFromNbt, // If there is a 'StateSaverAndLoader' NBT, parse it with 'createFromNbt'
            null // Supposed to be an 'DataFixTypes' enum, but we can just pass null
    );

    public static StateSaverAndLoader getServerState(MinecraftServer server) {
        PersistentStateManager persistentStateManager = server.getWorld(World.OVERWORLD).getPersistentStateManager();

        StateSaverAndLoader state = persistentStateManager.getOrCreate(type, Mineademia.Mod_id);

        state.markDirty();

        return state;
    }

    public static PlayerData getPlayerState(LivingEntity player) {
        StateSaverAndLoader serverState = getServerState(player.getWorld().getServer());

        // Either get the player by the uuid, or we don't have data for him yet, make a new player state
        PlayerData playerState = serverState.players.computeIfAbsent(player.getUuid(), uuid -> new PlayerData());

        return playerState;
    }
}

