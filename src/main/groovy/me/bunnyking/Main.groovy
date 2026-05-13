package me.bunnyking

import net.minestom.server.MinecraftServer
import net.minestom.server.entity.Player
import net.minestom.server.event.GlobalEventHandler
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent
import net.minestom.server.instance.InstanceContainer
import net.minestom.server.instance.InstanceManager
import net.minestom.server.instance.block.Block
import net.minestom.server.coordinate.Pos

class Main {
    static void main(String[] args) {

        println "Server Starting Please Wait..."

        def minecraftServer = MinecraftServer.init()
        minecraftServer.start("0.0.0.0", 25565)

        InstanceManager instanceManager = MinecraftServer.getInstanceManager()
        InstanceContainer instanceContainer = instanceManager.createInstanceContainer()

        instanceContainer.setGenerator { unit ->
            unit.modifier().fillHeight(0, 40, Block.DIRT)
        }

        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler()
        globalEventHandler.addListener(AsyncPlayerConfigurationEvent) { event ->
            Player player = event.player
            event.setSpawningInstance(instanceContainer)
            player.setRespawnPoint(new Pos(0, 50, 0))
        }
    }
}
