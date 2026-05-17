package me.bunnyking.Listeners

import groovy.transform.CompileStatic
import net.minestom.server.entity.ItemEntity
import net.minestom.server.entity.Player
import net.minestom.server.event.EventNode
import net.minestom.server.event.Event
import net.minestom.server.event.player.PlayerBlockBreakEvent
import net.minestom.server.event.GlobalEventHandler
import net.minestom.server.item.ItemStack

import java.time.Duration


@CompileStatic
class BlockBreakListener {

    BlockBreakListener(GlobalEventHandler globalEventHandler) {
        EventNode<Event> node = EventNode.all("block-break-listener")
        node.addListener(PlayerBlockBreakEvent.class, { PlayerBlockBreakEvent event ->

            Player player = event.getPlayer()

            // Create ItemStack from the block
            ItemStack itemStack = ItemStack.of(event.getBlock().registry().material())
            ItemEntity itemEntity = new ItemEntity(itemStack)

            itemEntity.setInstance(player.getInstance(), player.getTargetBlockPosition(4))
            itemEntity.spawn()
            itemEntity.acquirable()
            itemEntity.setPickupDelay(Duration.ofMillis(250))



        })

        globalEventHandler.addChild(node)
    }

}
