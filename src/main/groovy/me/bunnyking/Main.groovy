package me.bunnyking
import me.bunnyking.Listeners.BlockBreakListener
import me.bunnyking.Listeners.PickupListener
import net.minestom.server.MinecraftServer
import net.minestom.server.entity.Player
import net.minestom.server.event.GlobalEventHandler
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent
import net.minestom.server.instance.InstanceContainer
import net.minestom.server.instance.InstanceManager
import net.minestom.server.instance.LightingChunk
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

        // 1. Set the chunk generator for this instance using seperate class for advanced generation
        new TerrainGenerator(instanceContainer)

        // 2. Use your custom LightingChunk implementation
        instanceContainer.setChunkSupplier((instance, chunkX, chunkZ) ->
                new LightingChunk(instance, chunkX, chunkZ)
        )


        //spawning rules
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler()
        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, (AsyncPlayerConfigurationEvent event) -> {
            Player player = event.getPlayer()
            event.setSpawningInstance(instanceContainer)
            player.setRespawnPoint(new Pos(0.0d, 50.0d, 0.0d))

        })

        // Initialize Listeners
        new BlockBreakListener(globalEventHandler)
        new PickupListener(globalEventHandler)


        //main tick loop for running tasks every tick
        scheduler.buildTask(() -> {

            //save chunks
            instanceContainer.saveChunksToStorage()


        }).repeat(TaskSchedule.tick(1)).schedule()
    }
}