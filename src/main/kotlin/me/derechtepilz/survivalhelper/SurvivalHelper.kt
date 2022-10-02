package me.derechtepilz.survivalhelper

import com.mojang.brigadier.CommandDispatcher
import me.derechtepilz.survivalhelper.commands.HelpCommands
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.command.CommandRegistryAccess

object SurvivalHelper : ClientModInitializer {

    const val MOD_ID = "survivalhelper"

    override fun onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register(ClientCommandRegistrationCallback { dispatcher: CommandDispatcher<FabricClientCommandSource>, registryAccess: CommandRegistryAccess ->
            registerCommands(dispatcher, registryAccess)
        })
    }

    private fun registerCommands(dispatcher: CommandDispatcher<FabricClientCommandSource>, registryAccess: CommandRegistryAccess) {
        HelpCommands().register(dispatcher, registryAccess)
    }
}