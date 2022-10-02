package me.derechtepilz.survivalhelper.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.CommandSyntaxException
import net.minecraft.command.CommandRegistryAccess
import net.minecraft.command.argument.ItemStackArgument
import net.minecraft.command.argument.ItemStackArgumentType
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.server.network.ServerPlayerEntity
import kotlin.math.min

class HelpCommands {

    fun register(dispatcher: CommandDispatcher<ServerCommandSource>, commandRegistryAccess: CommandRegistryAccess) {
        dispatcher.register(CommandManager.literal("shelp")
                .then(CommandManager.literal("building")
                        .then(CommandManager.literal("items")
                                .then(CommandManager.argument("item", ItemStackArgumentType.itemStack(commandRegistryAccess))
                                        .then(CommandManager.argument("amount", IntegerArgumentType.integer())
                                                .executes { context: CommandContext<ServerCommandSource> ->
                                                    giveItem(
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

    @kotlin.jvm.Throws(CommandSyntaxException::class)
    private fun giveItem(serverCommandSource: ServerCommandSource, itemStackArgument: ItemStackArgument, amount: Int): Int {
        val player: ServerPlayerEntity = serverCommandSource.playerOrThrow

        var totalAmount = amount
        val itemMaxStackSize = itemStackArgument.item.maxCount

        while (totalAmount != 0) {
            val itemAmount = min(totalAmount, itemMaxStackSize)
            totalAmount -= itemAmount
            val itemStack = itemStackArgument.createStack(itemAmount, false)
            player.inventory.insertStack(itemStack)
        }
        player.inventory.markDirty()
        return amount
    }

}