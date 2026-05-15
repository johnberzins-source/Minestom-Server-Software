package me.bunnyking

import net.minestom.server.MinecraftServer
import net.minestom.server.entity.Player
import net.minestom.server.event.GlobalEventHandler
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent
import net.minestom.server.instance.InstanceContainer
import net.minestom.server.instance.InstanceManager
import net.minestom.server.instance.LightingChunk
import net.minestom.server.instance.anvil.AnvilLoader
import net.minestom.server.instance.block.Block
import net.minestom.server.coordinate.Pos
import net.minestom.server.timer.Scheduler
import net.minestom.server.timer.TaskSchedule


class Main {
    static void main(String[] args) {

        //say the server has started the main script
        println("Server Starting Please Wait...")

        //define the server and create a network port/ip
        MinecraftServer minecraftServer = MinecraftServer.init()
        minecraftServer.start("0.0.0.0", 25565)

        //create world container
        InstanceManager instanceManager = MinecraftServer.getInstanceManager()
        InstanceContainer instanceContainer = instanceManager.createInstanceContainer()
        Scheduler scheduler = MinecraftServer.getSchedulerManager()

        instanceContainer.setChunkSupplier {instance, chunkX, chunkZ ->new LightingChunk(instance, chunkX, chunkZ)}
        //load terrain
        instanceContainer.setChunkLoader(new AnvilLoader("worlds/world"))

        //spawning rules
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler()
        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, (AsyncPlayerConfigurationEvent event) -> {
            Player player = event.getPlayer()
            event.setSpawningInstance(instanceContainer)
            player.setRespawnPoint(new Pos(0.0d, 50.0d, 0.0d))

        })




        //main tick loop for running tasks every tick
        scheduler.buildTask(() -> {

            //generate terrain
            try {
                instanceContainer.setChunkLoader(new AnvilLoader("worlds/world"))
            } catch (Exception ignored) {
                println("ERROR, no world file")
                println("Attempting to create a world")
                instanceContainer.setGenerator { unit ->
                    unit.modifier().fillHeight(0, 40, Block.DIRT)
                }
            }

            //save chunks
            instanceContainer.saveChunksToStorage()




        }).repeat(TaskSchedule.tick(1)).schedule()
    }
}