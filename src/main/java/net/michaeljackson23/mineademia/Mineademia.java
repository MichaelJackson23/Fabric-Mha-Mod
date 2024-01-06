package net.michaeljackson23.mineademia;

import net.fabricmc.api.ModInitializer;

import net.michaeljackson23.mineademia.init.PlayerJoinsServerWorld;
import net.michaeljackson23.mineademia.init.QuirkPicker;
import net.michaeljackson23.mineademia.keybinds.Keybind;
import net.michaeljackson23.mineademia.test.DirtTracker;
import net.michaeljackson23.mineademia.test.KillOnUse;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mineademia implements ModInitializer {
	public static final String Mod_id = "mineademia";
    public static final Logger LOGGER = LoggerFactory.getLogger("mineademia");
	public static final Identifier INITIAL_SYNC = new Identifier(Mod_id, "initial_synce");

	@Override
	public void onInitialize() {
		new QuirkPicker();
		new Keybind();
		new DirtTracker();
		new KillOnUse();
		new PlayerJoinsServerWorld();
		Keybind.ClientWorkings();

	}
}