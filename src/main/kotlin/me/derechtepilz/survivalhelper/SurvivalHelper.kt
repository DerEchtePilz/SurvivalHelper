package me.derechtepilz.survivalhelper

import com.mojang.brigadier.CommandDispatcher
import me.derechtepilz.survivalhelper.commands.HelpCommands
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.command.CommandRegistryAccess
import net.minecraft.server.command.CommandManager.RegistrationEnvironment
import net.minecraft.server.command.ServerCommandSource

object SurvivalHelper : ClientModInitializer {

    const val MOD_ID = "survivalhelper"

    override fun onInitializeClient() {
        CommandRegistrationCallback.EVENT.register(CommandRegistrationCallback { dispatcher: CommandDispatcher<ServerCommandSource>, registryAccess: CommandRegistryAccess, registrationEnvironment: RegistrationEnvironment ->
            registerCommands(dispatcher, registryAccess, registrationEnvironment)
        })
    }

    private fun registerCommands(dispatcher: CommandDispatcher<ServerCommandSource>, registryAccess: CommandRegistryAccess, registrationEnvironment: RegistrationEnvironment) {
        HelpCommands().register(dispatcher, registryAccess)
    }
}