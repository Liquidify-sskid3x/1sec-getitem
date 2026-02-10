package com.example.randomitem;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomItemMod implements ModInitializer {

    private static final Random RANDOM = new Random();
    private static final List<Item> ALL_ITEMS = new ArrayList<>();
    private static int tickCounter = 0;

    @Override
    public void onInitialize() {

        // Load all items
        BuiltInRegistries.ITEM.stream().forEach(ALL_ITEMS::add);

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            tickCounter++;

            if (tickCounter >= 20) {
                tickCounter = 0;

                for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                    giveRandomItem(player);
                }
            }
        });
    }

    private void giveRandomItem(ServerPlayer player) {
        Item randomItem = ALL_ITEMS.get(RANDOM.nextInt(ALL_ITEMS.size()));
        player.getInventory().add(new ItemStack(randomItem));
    }
}