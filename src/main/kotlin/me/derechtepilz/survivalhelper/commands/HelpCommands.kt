package me.derechtepilz.survivalhelper.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.context.CommandContext
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.command.CommandRegistryAccess
import net.minecraft.command.argument.ItemStackArgument
import net.minecraft.command.argument.ItemStackArgumentType

class HelpCommands {

    fun register(dispatcher: CommandDispatcher<FabricClientCommandSource>, commandRegistryAccess: CommandRegistryAccess) {
        dispatcher.register(ClientCommandManager.literal("shelp")
                .then(ClientCommandManager.literal("building")
                        .then(ClientCommandManager.literal("items")
                                .then(ClientCommandManager.argument("item", ItemStackArgumentType.itemStack(commandRegistryAccess))
                                        .then(ClientCommandManager.argument("amount", IntegerArgumentType.integer())
                                                .executes { context: CommandContext<FabricClientCommandSource> ->
                                                    execute(
                                                        context.source,
                                                        ItemStackArgumentType.getItemStackArgument(context, "item"),
                                                        IntegerArgumentType.getInteger(context, "amount")
                                                    )
                                                }
                                        )
                                )
                        )
                )
        )
    }

    private fun execute(clientCommandSource: FabricClientCommandSource, itemStackArgument: ItemStackArgument, amount: Int): Int {
        val player: ClientPlayerEntity = clientCommandSource.player
        player.inventory.insertStack(itemStackArgument.createStack(amount, false))
        return 1
    }

}