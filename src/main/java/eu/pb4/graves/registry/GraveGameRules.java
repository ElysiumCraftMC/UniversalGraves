package eu.pb4.graves.registry;

import eu.pb4.graves.config.ConfigManager;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class GraveGameRules {
    public static final GameRules.Key<GameRules.IntRule> PROTECTION_TIME =
            GameRuleRegistry.register("universal_graves:protection_time", GameRules.Category.PLAYER, GameRuleFactory.createIntRule(-2, -2));
    public static final GameRules.Key<GameRules.IntRule> BREAKING_TIME =
            GameRuleRegistry.register("universal_graves:breaking_time", GameRules.Category.PLAYER, GameRuleFactory.createIntRule(-2, -2));
    public static final GameRules.Key<GameRules.IntRule> DROP_ITEM_STACK_CHANCE =
            GameRuleRegistry.register("universal_graves:drop_item_stack_chance", GameRules.Category.PLAYER, GameRuleFactory.createIntRule(-1, -1, 100));


    public static final int getProtectionTime(MinecraftServer server) {
        var rule = server.getOverworld().getGameRules().get(PROTECTION_TIME).get();

        if (rule == -2) {
            return ConfigManager.getConfig().configData.protectionTime;
        } else {
            return rule;
        }
    }

    public static final int getBreakingTime(MinecraftServer server) {
        var rule = server.getOverworld().getGameRules().get(BREAKING_TIME).get();

        if (rule == -2) {
            return ConfigManager.getConfig().configData.breakingTime;
        } else {
            return rule;
        }
    }

    public static final int getDropItemStackChance(World world) {
        var rule = world.getGameRules().get(DROP_ITEM_STACK_CHANCE).get();

        if (rule == -1) {
            return ConfigManager.getConfig().configData.dropItemStackChance;
        } else {
            return rule;
        }
    }
}
