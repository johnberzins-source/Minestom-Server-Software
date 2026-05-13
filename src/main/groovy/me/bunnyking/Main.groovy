package me.bunnyking
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.instance.*;
import net.minestom.server.instance.block.Block;
import net.minestom.server.coordinate.Pos;


class Main{
static void main(String[] args) {
    println "Server Starting Please Wait..."

    //initialize network and server
    MinecraftServer MCServer = MinecraftServer.init()
    MCServer.start("0.0 .0 .0", 25565)

    //create world
    InstanceManager instanceManager = MinecraftServer.getInstanceManager()
    //this is your actual world
    InstanceContainer instanceContainer = instanceManager.createInstanceContainer()

    instanceContainer.setGenerator(unit -> unit.modifier().fillHeight(0, 40, Block.DIRT))

    //initialize the engine on what to do on an event
    GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler()
    globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, event -> {
        Player player = event.getPlayer()
        event.setSpawningInstance(instanceContainer)
        event.getPlayer().setRespawnPoint(new Pos(0, 50, 0))

    })

}





}