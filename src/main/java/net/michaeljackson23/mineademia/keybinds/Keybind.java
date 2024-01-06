package net.michaeljackson23.mineademia.keybinds;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.michaeljackson23.mineademia.init.QuirkPicker;
import net.michaeljackson23.mineademia.util.PlayerAngleVector;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.Random;

public class Keybind {
    private static KeyBinding keyBinding;
    private static Random random = new Random();
    private static String message = "Nothing";

    public Keybind() {
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.mineademia.test",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                "key.mineademia.test"
        ));
    }

    public static void ClientWorkings() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.wasPressed()) {
                client.player.sendMessage(Text.literal(message), false);
                int randomNumber = random.nextInt(3) + 1;
                message = Integer.toString(randomNumber);
                PlayerAngleVector.getPlayerAngleVector(client.player, 2, 2, 2);
            }
        });
    }

}
