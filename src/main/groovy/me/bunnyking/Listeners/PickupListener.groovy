package me.bunnyking.Listeners

import groovy.transform.CompileStatic
import net.minestom.server.entity.Entity
import net.minestom.server.entity.Player
import net.minestom.server.event.Event
import net.minestom.server.event.EventNode
import net.minestom.server.event.GlobalEventHandler
import net.minestom.server.event.item.PickupItemEvent
import net.minestom.server.entity.ItemEntity
import net.minestom.server.event.player.PlayerBlockBreakEvent

import net.minestom.server.item.ItemStack


@CompileStatic
class PickupListener {

        PickupListener(GlobalEventHandler globalEventHandler) {
            EventNode<Event> node = EventNode.all("pickup-listener")
            node.addListener(PickupItemEvent.class, { PickupItemEvent event ->

                Entity entity = event.getLivingEntity()
                if (entity instanceof Player) {
                    Player player = (Player) entity
                    ItemEntity itemEntity = event.getItemEntity()
                    ItemStack itemStack = itemEntity.getItemStack()
                    player.getInventory().addItemStack(itemStack);

                }

            })
            globalEventHandler.addChild(node)

        }
}