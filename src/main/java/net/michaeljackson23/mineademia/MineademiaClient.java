package net.michaeljackson23.mineademia;

import net.fabricmc.api.ClientModInitializer;
import net.michaeljackson23.mineademia.test.DirtTracker;

public class MineademiaClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        DirtTracker.DirtTrackerClient();
    }
}
